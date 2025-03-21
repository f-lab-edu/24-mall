package com.temp.sample.controller.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse {
    public static final ApiResponse OK = new ApiResponse(CommonResult.OK);
    private final String resultCode;
    private final String resultMsg;

    public ApiResponse(CommonResult commonResult) {
        this.resultCode = commonResult.getCode();
        this.resultMsg = commonResult.getDesc();
    }
}
