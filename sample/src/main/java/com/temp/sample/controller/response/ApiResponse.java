package com.temp.sample.controller.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse {
    public static final ApiResponse OK = new ApiResponse(CommonResult.OK);
    private final String resultCode;
    private final String resultMsg;

    public ApiResponse(){
        this.resultCode = CommonResult.OK.getCode();
        this.resultMsg = CommonResult.OK.getDesc();
    }

    public ApiResponse(CommonResult code) {
        this.resultCode = code.getCode();
        this.resultMsg = code.getDesc();
    }
}
