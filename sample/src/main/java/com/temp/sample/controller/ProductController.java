package com.temp.sample.controller;

import com.temp.sample.service.ProductService;
import com.temp.sample.service.request.ProductRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/products")
  ApiResponse getProducts(@RequestBody ProductRequest request, HttpServletRequest httpRequest) {

    // 필터에서 설정된 속성 가져오기
    Long userId = (Long) httpRequest.getAttribute("userId");

    productService.read(request.getId());

    return ApiResponse.OK;
  }

}
