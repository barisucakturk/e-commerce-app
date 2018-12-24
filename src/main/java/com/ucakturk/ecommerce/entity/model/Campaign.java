package com.ucakturk.ecommerce.entity.model;

import lombok.Data;

@Data
public class Campaign {

    private Category category;
    private DiscountCampaign discountCampaign;

    public Campaign(Category category, DiscountCampaign discountCampaign) {
        this.category = category;
        this.discountCampaign = discountCampaign;
        this.category.addCampaign(this);
    }
}
