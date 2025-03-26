package com.temp.sample.controller;

import com.temp.sample.controller.response.ApiResponse;
import com.temp.sample.service.ProductService;
import com.temp.sample.service.request.ProductCreateReq;
import com.temp.sample.service.request.ProductDeleteReq;
import com.temp.sample.service.request.ProductUpdateReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    ApiResponse getProducts(@PathVariable Long productId) {

        productService.read(productId);

        return ApiResponse.OK;
    }

    @PostMapping("/products")
    ApiResponse createProducts(@RequestBody ProductCreateReq req,
                               HttpServletRequest httpRequest) {

        // 필터에서 설정된 속성 가져오기
        Long userId = (Long) httpRequest.getAttribute("userId");
        req.setProductId(userId);
        productService.create(req);

        return ApiResponse.OK;
    }

    @PatchMapping("/product")
    ApiResponse updateProduct(@RequestBody ProductUpdateReq req,
                              HttpServletRequest httpRequest) {

        // 필터에서 설정된 속성 가져오기
        Long userId = (Long) httpRequest.getAttribute("userId");
        req.setProductId(userId);
        productService.update(req);

        return ApiResponse.OK;
    }

    @DeleteMapping("/product")
    ApiResponse deleteProduct(@RequestBody ProductDeleteReq req,
                              HttpServletRequest httpRequest) {

        // 필터에서 설정된 속성 가져오기
        Long userId = (Long) httpRequest.getAttribute("userId");
        req.setProductId(userId);
        productService.delete(req);

        return ApiResponse.OK;
    }


}
