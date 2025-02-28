package com.temp.sample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {



    @PostMapping("/products")
    ApiResponse getProducts(){



        return ApiResponse.OK;
    }

}
