package com.temp.sample.controller;

import com.temp.sample.service.ProductService;
import com.temp.sample.service.request.ProductRequest;
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
    ApiResponse getProducts(@RequestBody ProductRequest request){

        productService.read(request.getId());

        return ApiResponse.OK;
    }

}
