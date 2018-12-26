package com.ucakturk.ecommerce.service;

import com.ucakturk.ecommerce.entity.enums.DiscountType;
import com.ucakturk.ecommerce.entity.model.Campaign;
import com.ucakturk.ecommerce.entity.model.Category;
import com.ucakturk.ecommerce.entity.model.DiscountCampaign;
import com.ucakturk.ecommerce.entity.model.Product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DiscountEngineTest {

    private DiscountEngine discountEngine;

    private Product product;

    private Category food;

    private Category foodParent;

    private BigDecimal cost;

    private BigDecimal amount;

    private Campaign campaign1;

    private DiscountCampaign discountCampaign1;

    private DiscountCampaign discountCampaign2;

    private BigDecimal discountResult;

    @Before
    public void setUp() {
        discountEngine = new DiscountEngine();

    }

    @Test
    public void calculateDiscount_ShouldReturnDiscountAmount_WhenDiscountIsValidAndDiscountTypeRateButJustOne() {
        //given
        cost = BigDecimal.TEN;
        food = new Category("food");
        product = new Product("orange", cost, food);
        amount = BigDecimal.valueOf(12);
        discountCampaign1 = new DiscountCampaign();
        discountCampaign1.setAmount(BigDecimal.TEN);
        discountCampaign1.setDiscountType(DiscountType.RATE);
        discountCampaign1.setDiscountAmount(BigDecimal.valueOf(20));
        campaign1 = new Campaign(food, discountCampaign1);
        //when
        discountResult = discountEngine.calculateDiscount(product, amount);
        //then
        assertEquals(BigDecimal.valueOf(24), discountResult);
    }

    @Test
    public void calculateDiscount_ShouldReturnDiscountAmount_WhenDiscountIsValidButMoreThanOne() {
        //given
        cost = BigDecimal.TEN;
        food = new Category("food");
        product = new Product("orange", cost, food);
        amount = BigDecimal.valueOf(12);
        discountCampaign1 = new DiscountCampaign();
        discountCampaign1.setAmount(BigDecimal.TEN);
        discountCampaign1.setDiscountType(DiscountType.RATE);
        discountCampaign1.setDiscountAmount(BigDecimal.valueOf(20));
        discountCampaign2 = new DiscountCampaign();
        discountCampaign2.setAmount(BigDecimal.TEN);
        discountCampaign2.setDiscountType(DiscountType.AMOUNT);
        discountCampaign2.setDiscountAmount(BigDecimal.valueOf(3));
        campaign1 = new Campaign(food, discountCampaign1);
        campaign1 = new Campaign(food, discountCampaign2);
        //when
        discountResult = discountEngine.calculateDiscount(product, amount);
        //then
        assertEquals(BigDecimal.valueOf(36), discountResult);
    }

    @Test
    public void calculateDiscount_ShouldReturnZeroDiscountAmount_WhenThereIsNoDiscount() {
        //given
        cost = BigDecimal.TEN;
        food = new Category("food");
        product = new Product("orange", cost, food);
        amount = BigDecimal.valueOf(12);
        //when
        discountResult = discountEngine.calculateDiscount(product, amount);
        //then
        assertEquals(BigDecimal.ZERO, discountResult);
    }

    @Test
    public void calculateDiscount_ShouldReturnDiscountAmount_WhenThereIsDiscountInParentCategory() {
        //given
        cost = BigDecimal.TEN;
        food = new Category("food");
        foodParent = new Category("foodParent");
        product = new Product("orange", cost, food);
        food.setParentCategory(foodParent);
        discountCampaign1 = new DiscountCampaign();
        discountCampaign1.setAmount(BigDecimal.TEN);
        discountCampaign1.setDiscountType(DiscountType.AMOUNT);
        discountCampaign1.setDiscountAmount(BigDecimal.valueOf(5));
        campaign1 = new Campaign(foodParent, discountCampaign1);
        amount = BigDecimal.valueOf(30);
        //when
        discountResult = discountEngine.calculateDiscount(product, amount);
        //then
        assertEquals(BigDecimal.valueOf(150), discountResult);
    }
}