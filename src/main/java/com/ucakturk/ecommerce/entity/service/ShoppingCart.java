package com.ucakturk.ecommerce.entity.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ucakturk.ecommerce.entity.model.Coupon;
import com.ucakturk.ecommerce.entity.model.Product;

import lombok.Data;

@Data
public class ShoppingCart {

    private Map<Product, Integer> products;
    private int item;
    private DiscountEngine discountEngine;
    private CouponEngine couponEngine;
    private DeliveryCostCalculator deliveryCostCalculator;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceAppliedDiscount;
    private BigDecimal totalCampaignDiscounts;
    private BigDecimal totalCouponDiscounts;
    private BigDecimal deliveryCosts;

    public ShoppingCart() {
        products = new HashMap<>();
        discountEngine = new DiscountEngine();
        couponEngine = new CouponEngine();
        deliveryCostCalculator = new DeliveryCostCalculator(DeliveryCostRuleConfigReader.defaultRule());
        totalPrice = BigDecimal.ZERO;
        totalPriceAppliedDiscount = BigDecimal.ZERO;
        totalCampaignDiscounts = BigDecimal.ZERO;
        totalCouponDiscounts = BigDecimal.ZERO;
    }

    public void addItem(Product product, int item) {
        products.putIfAbsent(product, item);
        products.computeIfPresent(product, (p, i) -> products.put(p, i + item));
        totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(item)));
    }

    public void applyDiscounts() {

        totalCampaignDiscounts = products.entrySet()
            .stream()
            .map(e -> discountEngine.calculateDiscount(e.getKey(), BigDecimal.valueOf(e.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalPriceAppliedDiscount = totalPrice.subtract(totalCampaignDiscounts);

    }

    public void applyCoupon(Coupon coupon) {
        totalCouponDiscounts =
            couponEngine.calculateCoupon(totalPriceAppliedDiscount, BigDecimal.valueOf(products.size()), coupon);

        totalPriceAppliedDiscount = totalPrice.subtract(totalCouponDiscounts);

    }

    public BigDecimal calculateDeliveryCost() {
        deliveryCosts = deliveryCostCalculator.calculateDeliveryCost(this);
        return deliveryCosts;
    }

    public BigDecimal getTotalAmountAfterDiscounts() {
        return totalPriceAppliedDiscount;
    }

    public BigDecimal getCampaignDiscount() {
        return totalCampaignDiscounts;
    }

    public BigDecimal getCouponDiscount() {
        return totalCouponDiscounts;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCosts;
    }

}
