package com.temp.sample.entity.com.converter;

import com.temp.sample.consts.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

  @Override
  public String convertToDatabaseColumn(OrderStatus orderStatus) {
    if (orderStatus == null) {
      return null;
    }
    return orderStatus.getCode();
  }

  @Override
  public OrderStatus convertToEntityAttribute(String code) {
    return OrderStatus.of(code);
  }
}
