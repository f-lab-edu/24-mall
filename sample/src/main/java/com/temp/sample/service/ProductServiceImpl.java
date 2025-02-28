package com.temp.sample.service;

import com.temp.sample.dao.ProductRepository;
import com.temp.sample.entity.Product;
import com.temp.sample.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse read(Long id) {
        return ProductResponse.from(productRepository.findById(id).orElseThrow());
    }

    public ProductResponse readAll(){
        List<Product> all = productRepository.findAll();
        return ProductResponse.builder()
                .product(all)
                .productCount(all.size())
                .build();
    }
}
