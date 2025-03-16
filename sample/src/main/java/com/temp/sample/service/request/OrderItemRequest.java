package com.temp.sample.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemRequest {

  private Long productId;

  private Integer quantity;

}
