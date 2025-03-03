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

    }


    @Test
    void test2() {
        String product = createProduct();
        System.out.println("product = " + product);
    }

    String createProduct() {
        return restClient.post()
            .uri("/sample")
            .retrieve()
            .body(String.class);
    }

    String createProduct2(ProductRequest request) {
        return restClient.post()
            .uri("/sample")
            .body(request)
            .retrieve()
            .body(String.class);
    }
}