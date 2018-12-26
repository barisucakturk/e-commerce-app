package com.ucakturk.ecommerce.service;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiscountEngine {

    private BigDecimal hundred = BigDecimal.valueOf(100);

    public BigDecimal calculateDiscount(Product product, BigDecimal amount) {
        Category category = product.getCategory();
        List<DiscountCampaign> discountCampaigns = new ArrayList<>();
        while (category != null) {
            if (!category.getCampaignList().isEmpty()) {
                discountCampaigns =
                    category.getCampaignList().stream().map(Campaign::getDiscountCampaign).collect(Collectors.toList());
                break;
            } else {
                log.info(
                    "There is no category for campaigns in related product. Searching parent category for campaign.");
                category = category.getParentCategory();
            }
        }
        if (discountCampaigns.isEmpty()) {
            log.info("No campaign found.");
            return BigDecimal.ZERO;
        }

        TreeSet<BigDecimal> discountValues = new TreeSet<>();

        for (DiscountCampaign discountCampaign : discountCampaigns) {
            if (amount.compareTo(discountCampaign.getAmount()) >= 0) {
                log.info("Amount {} is greater than minimum amount {}. Discount will be applied", amount,
                    discountCampaign.getAmount());
                BigDecimal discountValue;
                if (discountCampaign.getDiscountType() == DiscountType.RATE) {
                    log.info("Discount Type for Campaign: {}", discountCampaign.getDiscountType().toString());
                    discountValue = product.getPrice()
                        .multiply(amount)
                        .multiply(discountCampaign.getDiscountAmount())
                        .divide(hundred, MathContext.DECIMAL32);
                    discountValues.add(discountValue);
                } else if (discountCampaign.getDiscountType() == DiscountType.AMOUNT) {
                    log.info("Discount Type for Campaign: {}", discountCampaign.getDiscountType().toString());
                    discountValue = amount.multiply(discountCampaign.getDiscountAmount());
                    discountValues.add(discountValue);
                }
            }
        }
        log.info("Getting higher discount for Product {}", product.getTitle());
        return discountValues.last();
    }
}
