package com.ucakturk.ecommerce.entity.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryCostRuleConfig {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

}
