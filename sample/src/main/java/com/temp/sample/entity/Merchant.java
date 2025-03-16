package com.temp.sample.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "merchant")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;

    private String name;

    public static Merchant createMerchant(String name) {
        Merchant merchant = new Merchant();
        merchant.name = name;
        return merchant;
    }

    public static Merchant createMockMerchant(Long id, String name) {
        Merchant merchant = new Merchant();
        merchant.merchantId = id;
        merchant.name = name;
        return merchant;
    }
}
