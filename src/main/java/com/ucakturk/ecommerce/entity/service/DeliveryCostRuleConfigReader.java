package com.ucakturk.ecommerce.entity.service;

import java.math.BigDecimal;

import com.ucakturk.ecommerce.entity.model.DeliveryCostRuleConfig;

public class DeliveryCostRuleConfigReader {

    private DeliveryCostRuleConfigReader() {

    }

    public static DeliveryCostRuleConfig defaultRule() {
        return new DeliveryCostRuleConfig(BigDecimal.ONE, BigDecimal.ONE, new BigDecimal("2.99"));
    }

}
