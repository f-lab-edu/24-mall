package com.temp.sample.service;

import com.temp.sample.dao.MerchantRepository;
import com.temp.sample.dao.OrderInfoRepository;
import com.temp.sample.dao.ProductRepository;
import com.temp.sample.dao.UserRepository;
import com.temp.sample.entity.Merchant;
import com.temp.sample.entity.OrderInfo;
import com.temp.sample.entity.OrderItem;
import com.temp.sample.entity.Product;
import com.temp.sample.entity.User;
import com.temp.sample.exception.OutOfStockException;
import com.temp.sample.exception.OutOfStockException.OutOfStockItem;
import com.temp.sample.external.PaymentClient;
import com.temp.sample.external.PaymentRequest;
import com.temp.sample.external.PaymentResponse;
import com.temp.sample.service.request.OrderItemRequest;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderInfoRepository orderInfoRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final MerchantRepository merchantRepository;
  private final PaymentClient paymentClient;

  @Transactional
  @Override
  public void processOrder(List<OrderItemRequest> reqOrderItems, Long merchantId, Long userId,
      LocalDateTime reqDateTime) {

    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("잘못된 회원"));

    Merchant merchant = merchantRepository.findById(merchantId)
        .orElseThrow(() -> new RuntimeException("잘못된 가맹점"));

    // 상품 찾기
    List<Product> products = productRepository.findByIdIn(
        reqOrderItems.stream().map(OrderItemRequest::getProductId).toList());

    // 재고 검사및 재고 감소처리
    List<OutOfStockItem> outOfStockItems = new ArrayList<>();
    for (int i = 0; i < products.size(); i++) {
      Product product = products.get(i);

      for (int j = 0; j < reqOrderItems.size(); j++) {
        OrderItemRequest orderItemRequest = reqOrderItems.get(j);

        if (product.getId().equals(orderItemRequest.getProductId())) {
          if (product.getStock() >= orderItemRequest.getQuantity()) {
            product.decreaseStock(orderItemRequest.getQuantity());
          } else {
            outOfStockItems.add(
                OutOfStockItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .build());
          }
        }
      }
    }

    if (!outOfStockItems.isEmpty()) {
      throw new OutOfStockException(outOfStockItems);
    }

    // 주문상품 저장
    BigDecimal amount = BigDecimal.ZERO;
    List<OrderItem> orderItems = products.stream().map(product -> {

      OrderItemRequest orderItemRequest = reqOrderItems.stream()
          .filter(orderItem -> orderItem.getProductId().equals(product.getId()))
          .findFirst().orElseThrow(() -> new RuntimeException("상품이 없습니다."));

      BigDecimal totalPrice = product.getPrice()
          .multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()));

      OrderItem orderItem = OrderItem.createOrderItem(product, orderItemRequest.getQuantity(),
          totalPrice);
      BigDecimal add = amount.add(orderItem.getTotalPrice());
      return orderItem;
    }).toList();

    OrderInfo order = OrderInfo.createOrder(orderItems, merchant, user, amount);

    // 결제서비스 호출
    PaymentResponse paymentResponse = paymentClient.callPayment(new PaymentRequest(order.getAmount()));

    orderInfoRepository.save(order);

  }
}
