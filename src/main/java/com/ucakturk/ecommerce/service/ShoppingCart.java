package com.ucakturk.ecommerce.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ucakturk.ecommerce.entity.model.Coupon;
import com.ucakturk.ecommerce.entity.model.Product;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("{} Item(s) {} added ", item, product.getTitle());
    }

    public void applyDiscounts() {

        totalCampaignDiscounts = products.entrySet()
            .stream()
            .map(e -> discountEngine.calculateDiscount(e.getKey(), BigDecimal.valueOf(e.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalPriceAppliedDiscount = totalPrice.subtract(totalCampaignDiscounts);

        log.info("Total price after applying necessary discounts is {}", totalPriceAppliedDiscount);

    }

    public void applyCoupon(Coupon coupon) {
        totalCouponDiscounts =
            couponEngine.calculateCoupon(totalPriceAppliedDiscount, BigDecimal.valueOf(products.size()), coupon);

        totalPriceAppliedDiscount = totalPrice.subtract(totalCouponDiscounts);

        log.info("Total price after applying necessary coupons is {}", totalPriceAppliedDiscount);

    }

    public BigDecimal calculateDeliveryCost() {
        deliveryCosts = deliveryCostCalculator.calculateDeliveryCost(this);
        log.info("Delivery Cost is {}", deliveryCosts);
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
