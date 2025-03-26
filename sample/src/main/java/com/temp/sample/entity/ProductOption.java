package com.temp.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "product_option")
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private BigDecimal price;

  private BigDecimal discountPrice;

  private Long productId;


  public static ProductOption create(String name, BigDecimal price, BigDecimal discountPrice,
      Long productId) {
    ProductOption productOption = new ProductOption();
    productOption.name = name;
    productOption.price = price;
    productOption.discountPrice = discountPrice;
    productOption.productId = productId;

    return productOption;
  }

  public static ProductOption createMock(Long id, String name, BigDecimal price,
      BigDecimal discountPrice, Long productId) {
    ProductOption productOption = new ProductOption();
    productOption.id = id;
    productOption.name = name;
    productOption.price = price;
    productOption.discountPrice = discountPrice;
    productOption.productId = productId;
    return productOption;
  }

}
