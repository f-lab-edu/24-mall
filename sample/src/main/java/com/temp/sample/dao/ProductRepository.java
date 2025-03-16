package com.temp.sample.dao;

import com.temp.sample.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE p.stock > 0 AND p.id IN :productIds")
  List<Product> findInStocks(@Param("productIds") List<Long> productIds);

  List<Product> findByIdIn(List<Long> productIds);
}
