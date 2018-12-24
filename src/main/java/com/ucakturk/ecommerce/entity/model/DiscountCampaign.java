package com.ucakturk.ecommerce.entity.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiscountCampaign extends Discount {

    private BigDecimal amount;
}
