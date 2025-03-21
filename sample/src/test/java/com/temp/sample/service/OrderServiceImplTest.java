package com.temp.sample.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.temp.sample.dao.MerchantRepository;
import com.temp.sample.dao.OrderInfoRepository;
import com.temp.sample.dao.ProductRepository;
import com.temp.sample.dao.UserRepository;
import com.temp.sample.entity.Merchant;
import com.temp.sample.entity.OrderInfo;
import com.temp.sample.entity.Product;
import com.temp.sample.entity.User;
import com.temp.sample.exception.OutOfStockException;
import com.temp.sample.external.PaymentClient;
import com.temp.sample.external.PaymentResponse;
import com.temp.sample.service.request.OrderItemRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @InjectMocks
  OrderServiceImpl orderService;

  @Mock
  OrderInfoRepository orderInfoRepository;

  @Mock
  ProductRepository productRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  MerchantRepository merchantRepository;

  @Mock
  PaymentClient paymentClient;


  private User mockUser;
  private Merchant mockMerchant;
  private List<OrderItemRequest> orderItemRequest;
  private List<Product> mockProducts = new ArrayList<>();

  @BeforeEach
  void setUp() {
    // 테스트용 객체 생성
    mockUser = User.createMockUser(1L, "myng4291@naver.com", "1234", "01055555555");
    mockMerchant = Merchant.createMock(1L, "휴대폰");
    mockProducts.add(Product.createMock(1L, "아이폰", BigDecimal.valueOf(10000), 10, 1L));
    mockProducts.add(Product.createMock(2L, "갤럭시", BigDecimal.valueOf(10000), 10, 1L));

    orderItemRequest = mockProducts.stream().map(product -> {
      return new OrderItemRequest(product.getId(), product.getStock());
    }).toList();

  }

  @Test
  @DisplayName("정상 주문 처리 테스트")
  void processOrder_Success() {

    given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));
    given(merchantRepository.findById(anyLong())).willReturn(Optional.of(mockMerchant));
    given(productRepository.findByIdIn(List.of(1L, 2L))).willReturn(mockProducts);
    given(paymentClient.callPayment(any()))
        .willReturn(new PaymentResponse(200, "결제 완료"));

    assertDoesNotThrow(() -> orderService.processOrder(
        orderItemRequest,
        mockMerchant.getId(),
        mockUser.getId(),
        LocalDateTime.now()
    ));

  }

  @Test
  @DisplayName("상품이 부족할 때 예외 발생 테스트")
  void processOrder_OutOfStockException() {
    Product outOfStockProduct = Product.createMock(1L, "아이폰", BigDecimal.valueOf(10000),
        0, 1L);
    List<Product> insufficientStockProducts = List.of(outOfStockProduct);

    given(userRepository.findById(anyLong())).willReturn(Optional.of(mockUser));
    given(merchantRepository.findById(anyLong())).willReturn(Optional.of(mockMerchant));
    given(productRepository.findByIdIn(anyList())).willReturn(insufficientStockProducts);

    // 상품부족 예외 확인
    OutOfStockException exception = assertThrows(OutOfStockException.class, () -> {
      orderService.processOrder(
          orderItemRequest,
          mockMerchant.getId(),
          mockUser.getId(),
          LocalDateTime.now()
      );
    });

    // 예외 메시지 확인
    assertTrue(exception.getOutOfStockItems().stream()
        .anyMatch(item -> item.getProductId().equals(outOfStockProduct.getId())));

    // save()가 호출되지 않았는지 검증
    then(orderInfoRepository).should(never()).save(any(OrderInfo.class));
  }

}