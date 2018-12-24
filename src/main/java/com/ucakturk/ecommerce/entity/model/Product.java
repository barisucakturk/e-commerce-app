package com.ucakturk.ecommerce.entity.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "title")
public class Product {

    private String title;
    private BigDecimal price;
    private Category category;

    public Product(String title, BigDecimal price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.category.addProduct(this);
    }
}
