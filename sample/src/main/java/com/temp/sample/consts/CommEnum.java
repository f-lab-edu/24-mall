package com.temp.sample.consts;

import org.apache.commons.lang3.StringUtils;

public interface CommEnum {

    String getCode();

    static <E extends Enum<E> & CommEnum> E of(E[] values, String code) {
        for (E e : values) {
            if (StringUtils.equals(code,e.getCode())) {
                return e;
            }
        }
        return null;
    }
}
