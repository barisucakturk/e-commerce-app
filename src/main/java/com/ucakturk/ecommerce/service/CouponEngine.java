package com.ucakturk.ecommerce.service;

import java.math.BigDecimal;
import java.math.MathContext;

import com.ucakturk.ecommerce.entity.enums.DiscountType;
import com.ucakturk.ecommerce.entity.model.Coupon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CouponEngine {

    private BigDecimal hundred = BigDecimal.valueOf(100);

    public BigDecimal calculateCoupon(BigDecimal totalAmount, BigDecimal amount, Coupon coupon) {
        BigDecimal discountValue = BigDecimal.ZERO;
        if (totalAmount.compareTo(coupon.getPrice()) > 0) {
            log.info("Amount {} is greater than minimum amount {}. Coupon will be applied", totalAmount,
                coupon.getPrice());
            if (coupon.getDiscount().getDiscountType() == DiscountType.RATE) {
                log.info("Discount Type for Coupon: {}",coupon.getDiscount().getDiscountType().toString());
                discountValue =
                    amount.multiply(coupon.getDiscount().getDiscountAmount()).divide(hundred, MathContext.DECIMAL32);
            } else if (coupon.getDiscount().getDiscountType() == DiscountType.AMOUNT) {
                log.info("Discount Type for Coupon: {}",coupon.getDiscount().getDiscountType().toString());
                discountValue = amount.multiply(coupon.getDiscount().getDiscountAmount());
            }
        }

        return discountValue;
    }
}
