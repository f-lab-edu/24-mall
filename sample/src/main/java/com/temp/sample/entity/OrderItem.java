package com.temp.sample.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Table(name = "order_product")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderInfo order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private Integer quantity;

  private BigDecimal totalPrice;


  public static OrderItem createOrderItem( Product product, int quantity,
      BigDecimal totalPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.product = product;
    orderItem.quantity = quantity;
    orderItem.totalPrice = totalPrice;

    return orderItem;
  }
}
