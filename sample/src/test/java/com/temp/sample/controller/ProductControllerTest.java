package com.temp.sample.controller;

import com.temp.sample.service.request.ProductRequest;
import com.temp.sample.service.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void test(){
        ProductResponse 배송 = createProduct(new ProductRequest(1L, "배송"));

        System.out.println("배송 = " + 배송);
    }

    ProductResponse createProduct(ProductRequest request) {
        return restClient.post()
                .uri("/products")
                .body(request)
                .retrieve()
                .body(ProductResponse.class);
    }



}