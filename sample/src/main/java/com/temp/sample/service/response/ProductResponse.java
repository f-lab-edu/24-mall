package com.temp.sample.service.response;

import com.temp.sample.controller.response.ApiResponse;
import com.temp.sample.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class ProductResponse {

    @Builder.Default
    private final ApiResponse apiResponse = ApiResponse.OK;
    private final List<Product> product;
    private final Integer productCount;

    public static ProductResponse from(Product product) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        return ProductResponse.builder()
                .product(products)
                .productCount(products.size())
                .build();
    }

}
