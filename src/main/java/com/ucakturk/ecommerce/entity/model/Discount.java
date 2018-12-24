package com.ucakturk.ecommerce.entity.model;

import java.math.BigDecimal;

import com.ucakturk.ecommerce.entity.enums.DiscountType;

import lombok.Data;

@Data
public class Discount {
    private DiscountType discountType;
    private BigDecimal discountAmount;
}
