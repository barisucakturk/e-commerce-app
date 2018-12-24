package com.ucakturk.ecommerce.entity.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.ucakturk.ecommerce.entity.enums.DiscountType;
import com.ucakturk.ecommerce.entity.model.Campaign;
import com.ucakturk.ecommerce.entity.model.Category;
import com.ucakturk.ecommerce.entity.model.DiscountCampaign;
import com.ucakturk.ecommerce.entity.model.Product;

public class DiscountEngine {

    private BigDecimal hundred = BigDecimal.valueOf(100);

    public BigDecimal calculateDiscount(Product product, BigDecimal amount) {
        Category category = product.getCategory();
        List<DiscountCampaign> discountCampaigns = new ArrayList<>();
        while (category != null) {
            if (category.getCampaignList().size() > 0) {
                discountCampaigns = category.getCampaignList().stream().map(Campaign::getDiscountCampaign).collect(Collectors.toList());
            } else {
                category = category.getParentCategory();
            }
        }
        if (discountCampaigns.isEmpty()) {
            return BigDecimal.ZERO;
        }

        TreeSet<BigDecimal> discountValues = new TreeSet<>();

        for (DiscountCampaign discountCampaign : discountCampaigns) {
            if (amount.compareTo(discountCampaign.getAmount()) >= 0) {
                BigDecimal discountValue;
                if (discountCampaign.getDiscountType() == DiscountType.RATE) {
                    discountValue = product.getPrice()
                        .multiply(amount)
                        .multiply(discountCampaign.getDiscountAmount())
                        .divide(hundred, MathContext.DECIMAL32);
                    discountValues.add(discountValue);
                } else if (discountCampaign.getDiscountType() == DiscountType.AMOUNT) {
                    discountValue = discountCampaign.getAmount().multiply(discountCampaign.getDiscountAmount());
                    discountValues.add(discountValue);
                }
            }
        }
        return discountValues.last();
    }
}
