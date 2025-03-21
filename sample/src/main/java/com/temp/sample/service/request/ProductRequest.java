package com.temp.sample.service.request;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequest {

  private Long productId;
  private String name;
  private Integer stock;
  private BigDecimal price;
  private BigDecimal discount;
  private String description;
  private List<Option> options;
  private List<Image> image;
  private String categoryId;
  private Long merchantId;


  private Long userId;

  // 상품 옵션
  @Getter
  public static class Option {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
  }

  // 상품 이미지
  @Getter
  public static class Image{
    private Long id;
    private String name;
    private MultipartFile file;
  }
}
