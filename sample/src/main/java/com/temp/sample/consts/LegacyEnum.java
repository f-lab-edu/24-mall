package com.temp.sample.consts;

import org.apache.commons.lang3.StringUtils;

public interface LegacyEnum {

    String getCode();

    static <E extends Enum<E> & LegacyEnum> E of(E[] values, String code) {
        for (E e : values) {
            if (StringUtils.equals(code,e.getCode())) {
                return e;
            }
        }
        return null;
    }
}
