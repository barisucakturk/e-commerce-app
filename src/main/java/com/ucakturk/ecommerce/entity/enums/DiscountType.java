package com.ucakturk.ecommerce.entity.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum DiscountType {


    RATE("RATE"),
    AMOUNT("AMOUNT");

    private final String key;

    DiscountType(String key) {
        this.key = key;
    }

    public static DiscountType getByKey(String key) {
        return Arrays.stream(DiscountType.values())
            .filter(labelTypeEnum -> labelTypeEnum.key.equals(key))
            .findFirst()
            .orElse(null);
    }

}
