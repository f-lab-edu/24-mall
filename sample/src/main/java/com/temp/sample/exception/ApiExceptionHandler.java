package com.temp.sample.exception;

import com.temp.sample.controller.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<?> handleUnexpectedException(Exception ex) {
    if (ex instanceof ApiException) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
    }

    // 그 외 모든 예외는 공통 정제해서 처리
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.FAIL);
  }
}
