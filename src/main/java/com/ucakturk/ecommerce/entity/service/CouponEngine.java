package com.ucakturk.ecommerce.entity.service;

import java.math.BigDecimal;
import java.math.MathContext;

import com.ucakturk.ecommerce.entity.enums.DiscountType;
import com.ucakturk.ecommerce.entity.model.Coupon;

public class CouponEngine {

    private BigDecimal hundred = BigDecimal.valueOf(100);

    public BigDecimal calculateCoupon(BigDecimal totalAmount, BigDecimal amount, Coupon coupon) {
        BigDecimal discountValue = BigDecimal.ZERO;
        if (totalAmount.compareTo(coupon.getPrice()) > 0) {
            if (coupon.getDiscount().getDiscountType() == DiscountType.RATE) {
                discountValue = amount.multiply(coupon.getDiscount().getDiscountAmount()).divide(hundred, MathContext.DECIMAL32);
            } else if (coupon.getDiscount().getDiscountType() == DiscountType.AMOUNT) {
                discountValue = amount.multiply(coupon.getDiscount().getDiscountAmount());
            }
        }

        return discountValue;
    }
}
