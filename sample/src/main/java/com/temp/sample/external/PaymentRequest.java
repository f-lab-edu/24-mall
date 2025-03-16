package com.temp.sample.external;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequest {
  private BigDecimal amount;

}
