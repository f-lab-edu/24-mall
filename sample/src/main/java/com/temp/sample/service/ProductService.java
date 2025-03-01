package com.temp.sample.service;

import com.temp.sample.service.response.ProductResponse;

public interface ProductService {
    ProductResponse read(Long id);
    ProductResponse readAll();
}
