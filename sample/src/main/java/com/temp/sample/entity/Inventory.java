package com.temp.sample.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "inventory")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int stock;

    @Version  // 낙관적 락을 위한 버전 필드
    private Integer version;

    public static Inventory createInventory(Long productId, int stock) {
        Inventory inventory = new Inventory();
        inventory.productId = productId;
        inventory.stock = stock;
        return inventory;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고 부족");
        }
        this.stock -= quantity;
    }

}
