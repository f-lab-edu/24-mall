package com.temp.sample.entity;

import com.temp.sample.entity.com.TimestampEntity;
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

    private String orderNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private User user;

    private BigDecimal amount = BigDecimal.ZERO;

    @Embedded
    private TimestampEntity timestamp;

    public static OrderInfo createOrder(String orderNumber, Merchant merchant, User user, BigDecimal amount) {

        OrderInfo orderInfo = new OrderInfo();

        orderInfo.orderNumber = orderNumber;
        orderInfo.merchant = merchant;
        orderInfo.user = user;
        orderInfo.amount = amount;
        return orderInfo;

    }

}
