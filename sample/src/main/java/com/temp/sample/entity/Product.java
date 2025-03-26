package com.temp.sample.entity;

import com.temp.sample.entity.com.TimestampEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "product")
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stock;

    private LocalDateTime timeDealStartTime;

    private LocalDateTime timeDealEndTime;

    private Long merchantId;

    @Embedded
    private TimestampEntity timestamp;

    public static Product create(String name, BigDecimal price, Integer stock, Long merchantId) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.stock = stock;
        product.merchantId = merchantId;
        return product;
    }

    public static Product createMock(Long id, String name, BigDecimal price, Integer stock, Long merchantId) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.price = price;
        product.stock = stock;
        product.merchantId = merchantId;
        return product;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new RuntimeException("재고 부족");
        }
        this.stock -= quantity;
    }
}
