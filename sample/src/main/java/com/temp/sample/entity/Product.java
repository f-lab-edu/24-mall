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
import lombok.ToString;

@Table(name = "product")
@Getter
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

    @Embedded
    private TimestampEntity timestamp;

    public static Product createProduct(String name, BigDecimal price, Integer stock) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.stock = stock;
        return product;
    }

    public static Product createMockProduct(Long id, String name, BigDecimal price, Integer stock) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.price = price;
        product.stock = stock;
        return product;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new RuntimeException("재고 부족");
        }
        this.stock -= quantity;
    }
}
