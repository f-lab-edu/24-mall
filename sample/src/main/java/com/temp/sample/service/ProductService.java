package com.temp.sample.service;

import com.temp.sample.service.request.ProductCreateReq;
import com.temp.sample.service.request.ProductDeleteReq;
import com.temp.sample.service.request.ProductUpdateReq;
import com.temp.sample.service.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponse read(Long id);

  ProductResponse readAll(Pageable pageable);

  ProductResponse create(ProductCreateReq req);

  ProductResponse update(ProductUpdateReq req);

  ProductResponse delete(ProductDeleteReq req);
}
