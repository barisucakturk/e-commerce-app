package com.ucakturk.ecommerce.entity.service;

import java.math.BigDecimal;

import com.ucakturk.ecommerce.entity.model.DeliveryCostRuleConfig;
import com.ucakturk.ecommerce.entity.model.Product;

public class DeliveryCostCalculator {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    public DeliveryCostCalculator(DeliveryCostRuleConfig deliveryCostRuleConfig) {
        this.costPerDelivery = deliveryCostRuleConfig.getCostPerDelivery();
        this.costPerProduct = deliveryCostRuleConfig.getCostPerProduct();
        this.fixedCost = deliveryCostRuleConfig.getFixedCost();
    }

    public BigDecimal calculateDeliveryCost(ShoppingCart cart) {
        long numberOfDeliveries = cart.getProducts().keySet().stream().map(Product::getCategory).distinct().count();
        return costPerDelivery.multiply(BigDecimal.valueOf(numberOfDeliveries))
            .add(costPerProduct.multiply(BigDecimal.valueOf(cart.getProducts().size()))).add(fixedCost);

    }
}
