package com.temp.sample.controller;

import com.temp.sample.controller.response.ApiResponse;
import com.temp.sample.service.ProductService;
import com.temp.sample.service.request.ProductRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/products/{productId}")
  ApiResponse getProducts(@PathVariable Long productId, HttpServletRequest httpRequest) {

    // 필터에서 설정된 속성 가져오기
    Long userId = (Long) httpRequest.getAttribute("userId");

    productService.read(productId);

    return ApiResponse.OK;
  }


}
