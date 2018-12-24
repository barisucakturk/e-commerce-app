package com.ucakturk.ecommerce.entity.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Coupon {

    private BigDecimal price;
    private Discount discount;
}
