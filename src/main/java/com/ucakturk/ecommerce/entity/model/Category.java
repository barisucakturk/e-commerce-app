package com.ucakturk.ecommerce.entity.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString(exclude = "campaignList")
@AllArgsConstructor
@Data
public class Category {

    private String title;
    private Category parentCategory;
    private List<Campaign> campaignList;
    private List<Product> productList;

    public Category(String title) {
        this.title = title;
        campaignList = new ArrayList<>();
        productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void addCampaign(Campaign campaign) {
        campaignList.add(campaign);
    }
}
