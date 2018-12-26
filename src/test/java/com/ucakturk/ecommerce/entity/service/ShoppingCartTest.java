package com.ucakturk.ecommerce.entity.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ucakturk.ecommerce.entity.model.Category;
import com.ucakturk.ecommerce.entity.model.Coupon;
import com.ucakturk.ecommerce.entity.model.Product;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ShoppingCart.class)
public class ShoppingCartTest {

    private ShoppingCart cart;

    private DiscountEngine discountEngineMock;

    private CouponEngine couponEngineMock;

    private DeliveryCostCalculator deliveryCostCalculatorMock;

    private Product product;

    private Product product2;

    private Category electronics;

    private BigDecimal cost;

    private BigDecimal result;

    private Coupon coupon;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        discountEngineMock = mock(DiscountEngine.class);
        couponEngineMock = mock(CouponEngine.class);
        deliveryCostCalculatorMock = mock(DeliveryCostCalculator.class);
    }

    @Test
    public void addItem_ShouldReturnPrice_WhenNewItemsAdded() {
        //given
        cost = BigDecimal.TEN;
        electronics = new Category("electronics");
        product = new Product("phone", cost, electronics);
        int item = 10;
        //when
        cart = new ShoppingCart();
        cart.addItem(product, item);
        //then
        assertEquals(BigDecimal.valueOf(100), cart.getTotalPrice());
    }

    @Test
    public void applyDiscount_ShouldReturnDiscountedPrice_WhenDiscountCalculated() throws Exception {
        //given
        cost = BigDecimal.TEN;
        electronics = new Category("electronics");
        product = new Product("phone", cost, electronics);
        cost = BigDecimal.valueOf(20);
        product2 = new Product("computer", cost, electronics);
        //when
        whenNew(DiscountEngine.class).withNoArguments().thenReturn(discountEngineMock);
        when(discountEngineMock.calculateDiscount(any(), any())).thenReturn(BigDecimal.TEN);
        cart = new ShoppingCart();
        cart.addItem(product, 5);
        cart.addItem(product2, 3);
        //then
        cart.applyDiscounts();
        result = cart.getTotalAmountAfterDiscounts();
        assertEquals(BigDecimal.valueOf(20),cart.getCampaignDiscount());
        assertEquals(BigDecimal.valueOf(90), result);
    }

    @Test
    public void applyCoupon_ShouldReturnDiscountedPrice_WhenCouponApplied() throws Exception {
        //given
        coupon = new Coupon();
        cost = BigDecimal.valueOf(1000);
        electronics = new Category("electronics");
        product = new Product("LCD", cost, electronics);
        //when
        whenNew(CouponEngine.class).withNoArguments().thenReturn(couponEngineMock);
        when(couponEngineMock.calculateCoupon(any(), any(), any())).thenReturn(BigDecimal.valueOf(20));
        cart = new ShoppingCart();
        cart.addItem(product, 2);
        //then
        cart.applyCoupon(coupon);
        result = cart.getTotalAmountAfterDiscounts();
        assertEquals(BigDecimal.valueOf(20),cart.getCouponDiscount());
        assertEquals(BigDecimal.valueOf(1980), result);
    }

    @Test
    public void calculateDeliveryCost() throws Exception {
        //given
        //when
        whenNew(DeliveryCostCalculator.class).withArguments(any()).thenReturn(deliveryCostCalculatorMock);
        when(deliveryCostCalculatorMock.calculateDeliveryCost(any())).thenReturn(BigDecimal.valueOf(102));
        //then
        cart = new ShoppingCart();
        result = cart.calculateDeliveryCost();
        assertEquals(BigDecimal.valueOf(102),cart.getDeliveryCost());
        assertEquals(BigDecimal.valueOf(102), result);
    }
}