package com.ucakturk.ecommerce.service;

import com.ucakturk.ecommerce.entity.enums.DiscountType;
import com.ucakturk.ecommerce.entity.model.Coupon;
import com.ucakturk.ecommerce.entity.model.Discount;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CouponEngineTest {

    private CouponEngine couponEngine;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private Coupon coupon;
    private Discount discount;
    private BigDecimal result;

    @org.junit.Before
    public void setUp() {
        couponEngine = new CouponEngine();
        coupon = new Coupon();
        discount = new Discount();
    }

    @org.junit.Test
    public void calculateCoupon_ShouldReturnDiscountValueIsZero_WhenAmountLessThanConstraint() {
        //given
        discount.setDiscountAmount(BigDecimal.TEN);
        coupon.setDiscount(discount);
        coupon.setPrice(BigDecimal.TEN);
        amount = BigDecimal.ONE;
        totalAmount = BigDecimal.valueOf(9);
        //when
        //then
        result = couponEngine.calculateCoupon(totalAmount, amount, coupon);

        assertEquals(BigDecimal.ZERO, result);
    }

    @org.junit.Test
    public void calculateCoupon_ShouldReturnDiscountValue_WhenAmountGreaterThanConstraintAndDiscountTypeIsRate() {
        //given
        discount.setDiscountAmount(BigDecimal.ONE);
        discount.setDiscountType(DiscountType.RATE);
        coupon.setPrice(BigDecimal.TEN);
        coupon.setDiscount(discount);
        amount = BigDecimal.valueOf(100);
        totalAmount = BigDecimal.valueOf(80);
        //when
        //then
        result = couponEngine.calculateCoupon(totalAmount, amount, coupon);

        assertEquals(BigDecimal.ONE, result);
    }

    @org.junit.Test
    public void calculateCoupon_ShouldReturnDiscountValue_WhenAmountGreaterThanConstraintAndDiscountTypeIsAmount() {
        //given
        discount.setDiscountAmount(BigDecimal.TEN);
        discount.setDiscountType(DiscountType.AMOUNT);
        coupon.setPrice(BigDecimal.valueOf(40));
        coupon.setDiscount(discount);
        amount = BigDecimal.valueOf(20);
        totalAmount = BigDecimal.valueOf(50);
        //when
        //then
        result = couponEngine.calculateCoupon(totalAmount, amount, coupon);

        assertEquals(BigDecimal.valueOf(200), result);
    }
}