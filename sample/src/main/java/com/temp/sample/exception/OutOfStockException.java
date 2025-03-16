package com.temp.sample.exception;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OutOfStockException extends RuntimeException {

  private final List<OutOfStockItem> outOfStockItems; // 재고 없는 상품 정보 리스트

  public OutOfStockException(List<OutOfStockItem> outOfStockItems) {
    super("상품의 수량이 부족합니다.");
    this.outOfStockItems = outOfStockItems;
  }

  @Builder
  @Getter
  public static class OutOfStockItem {
    private final Long productId;
    private final String productName;
  }
}
