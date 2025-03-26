package com.temp.sample.exception;

import com.temp.sample.controller.response.CommonResult;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private final String resultCode;
  private final String resultMsg;

  public ApiException(CommonResult commonResult) {
    this.resultCode = commonResult.getCode();
    this.resultMsg = commonResult.getDesc();
  }
}
