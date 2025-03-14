package com.temp.sample.service;

import com.temp.sample.dao.InventoryRepository;
import com.temp.sample.dao.OrderInfoRepository;
import com.temp.sample.entity.Inventory;
import com.temp.sample.entity.Merchant;
import com.temp.sample.entity.OrderInfo;
import com.temp.sample.entity.User;
import com.temp.sample.external.PaymentClient;
import com.temp.sample.external.PaymentResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderInfoRepository orderInfoRepository;
    private final InventoryRepository inventoryRepository;
    private final PaymentClient paymentClient;

    @Transactional
    @Override
    public void processOrder() {

        Merchant merchant = Merchant.createMerchant("고기집");
        User user = User.createUser("gogi@gogi.com", "1234", "01055557777");
        OrderInfo order = OrderInfo.createOrder("o-1234", merchant, user, BigDecimal.valueOf(1234L));

        Inventory inventory = inventoryRepository.findByProductIdForUpdate(1L);

        int quantity = 11;

        inventory.decreaseStock(quantity);

        PaymentResponse paymentResponse = paymentClient.callPayment(order);

        orderInfoRepository.save(order);

        orderInfoRepository.flush();


    }
}
