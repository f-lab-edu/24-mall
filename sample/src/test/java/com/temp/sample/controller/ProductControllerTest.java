package com.temp.sample.controller;

import com.temp.sample.service.request.ProductCreateReq;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

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

    String createProduct2(ProductCreateReq request) {
        return restClient.post()
            .uri("/sample")
            .body(request)
            .retrieve()
            .body(String.class);
    }
}