package com.temp.sample.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

  private Integer resultCode;
  private String resultMessage;
}
