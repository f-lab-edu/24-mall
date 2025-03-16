package com.temp.sample.dao;

import com.temp.sample.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)  // 비관적 락 적용
    @Query("SELECT i FROM Inventory i WHERE i.productId = :productId")
    Inventory findByProductIdForUpdate(@Param("productId") Long productId);
}
