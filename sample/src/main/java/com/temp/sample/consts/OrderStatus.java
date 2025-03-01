package com.temp.sample.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements LegacyEnum{

    APPLY("0", "주문접수"),
    APPROVAL("1", "주문승인"),
    READY("2", "준비중"),
    DELIVERY("3", "배송중"),
    DONE("4", "배송완료"),
    CANCEL("5", "취소")
    ;


    private final String code;
    private final String desc;

    public static OrderStatus of(String code) {
        return LegacyEnum.of(values(), code);
    }



}
