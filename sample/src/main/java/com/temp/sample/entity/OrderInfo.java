package com.temp.sample.entity;

import com.temp.sample.consts.OrderStatus;
import com.temp.sample.entity.com.TimestampEntity;
import com.temp.sample.entity.com.converter.OrderStatusConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "order")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  @ManyToOne
  @JoinColumn(name = "merchant_id")
  private Merchant merchant;

  @ManyToOne
  private User user;

  private BigDecimal amount = BigDecimal.ZERO;

  private OrderStatus status;

  @Embedded
  private TimestampEntity timestamp;

  public static OrderInfo createOrder(List<OrderItem> orderItems, Merchant merchant, User user,
      BigDecimal amount) {

    OrderInfo orderInfo = new OrderInfo();

    orderInfo.orderItems = orderItems;
    orderInfo.merchant = merchant;
    orderInfo.user = user;
    orderInfo.amount = amount;
    return orderInfo;

  }

}
