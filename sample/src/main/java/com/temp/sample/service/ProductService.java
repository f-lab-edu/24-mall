package com.temp.sample.service;

import com.temp.sample.service.request.ProductRequest;
import com.temp.sample.service.response.ProductResponse;

public interface ProductService {

  ProductResponse read(Long id);

  ProductResponse readAll();

  ProductResponse create(ProductRequest req);

  ProductResponse update(ProductRequest req);

  ProductResponse delete(ProductRequest req);
}
