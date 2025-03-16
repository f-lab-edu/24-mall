package com.temp.sample.service;

import com.temp.sample.service.request.OrderItemRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    void processOrder(List<OrderItemRequest> orderItems, Long merchantId, Long userId, LocalDateTime reqDateTime);
}
