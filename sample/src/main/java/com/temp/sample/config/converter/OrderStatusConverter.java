package com.temp.sample.config.converter;

import com.temp.sample.consts.OrderStatus;
import org.springframework.core.convert.converter.Converter;

public class OrderStatusConverter implements Converter<String, OrderStatus> {

    @Override
    public OrderStatus convert(String s) {
        return OrderStatus.of(s);
    }
}