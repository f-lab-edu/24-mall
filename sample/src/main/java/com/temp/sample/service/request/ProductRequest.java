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
  private BigDecimal price;
  private BigDecimal discount;
  private String description;
  private List<Option> options;
  private List<MultipartFile> image;
  private String categoryId;


  private Long userId;

  // 상품 옵션
  public static class Option {

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
  }
}
