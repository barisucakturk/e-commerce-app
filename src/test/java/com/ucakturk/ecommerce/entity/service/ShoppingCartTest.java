package com.ucakturk.ecommerce.entity.service;

import com.ucakturk.ecommerce.entity.model.Category;
import com.ucakturk.ecommerce.entity.model.Product;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.mock;
//import static org.powermock.api.mockito.PowerMockito.when;
//import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ShoppingCart.class)
public class ShoppingCartTest {

    private ShoppingCart cart;

    private DiscountEngine discountEngineMock;

    private CouponEngine couponEngineMock;

    private Map<Product, Integer> products;

    private Product product;

    private Product product2;

    private Category electronics;

    private BigDecimal cost;

    private BigDecimal result;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        discountEngineMock = mock(DiscountEngine.class);
        couponEngineMock = mock(CouponEngine.class);
        cart = new ShoppingCart();
        products = new HashMap<>();
    }

    @Test
    public void addItem() {
        //given
        cost = BigDecimal.TEN;
        electronics = new Category("electronics");
        product = new Product("phone",cost, electronics);
        int item = 10;
        //when
        cart.addItem(product,item);
        //then
        assertEquals(BigDecimal.valueOf(100),cart.getTotalPrice());
    }
@Ignore
    @Test
    public void applyDiscounts() throws Exception {
        //given
        cost = BigDecimal.TEN;
        electronics = new Category("electronics");
        product = new Product("phone",cost, electronics);
        cost = BigDecimal.valueOf(20);
        product2 = new Product("computer",cost, electronics);
        //when
        cart.addItem(product,5);
        cart.addItem(product2,3);
     //   whenNew(DiscountEngine.class).withNoArguments().thenReturn(discountEngineMock);
       // when(discountEngineMock.calculateDiscount(any(),any())).thenReturn(BigDecimal.TEN);
        //then
        cart.applyDiscounts();
        result = cart.getTotalAmountAfterDiscounts();
        assertEquals(BigDecimal.valueOf(140),result);

    }

    @Test
    public void applyCoupon() {
    }

    @Test
    public void calculateDeliveryCost() {
    }

    @Test
    public void getTotalAmountAfterDiscounts() {
    }

    @Test
    public void getCampaignDiscount() {
    }

    @Test
    public void getCouponDiscount() {
    }

    @Test
    public void getDeliveryCost() {
    }
}