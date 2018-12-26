package com.ucakturk.ecommerce.service;

import com.ucakturk.ecommerce.entity.model.Category;
import com.ucakturk.ecommerce.entity.model.DeliveryCostRuleConfig;
import com.ucakturk.ecommerce.entity.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class DeliveryCostCalculatorTest {

    private DeliveryCostCalculator deliveryCostCalculator;

    @Mock
    private DeliveryCostRuleConfig deliveryCostRuleConfig;

    private ShoppingCart shoppingCart;

    private Map<Product, Integer> products;

    private Product apple;

    private Category food;

    private Category electronics;

    private BigDecimal result;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shoppingCart = new ShoppingCart();
        products = new HashMap<>();
        food = new Category("food");
        electronics = new Category("electronics");
        apple = new Product("Apple", BigDecimal.TEN, food);
    }

    @Test
    public void calculateDeliveryCost_ShouldReturnDeliveryCost_WhenThereIsOneCategory() {
        //given
        products.put(apple, 2);
        shoppingCart.setProducts(products);
        //when
        when(deliveryCostRuleConfig.getCostPerDelivery()).thenReturn(BigDecimal.ONE);
        when(deliveryCostRuleConfig.getCostPerProduct()).thenReturn(BigDecimal.TEN);
        when(deliveryCostRuleConfig.getFixedCost()).thenReturn(BigDecimal.valueOf(3));
        deliveryCostCalculator = new DeliveryCostCalculator(deliveryCostRuleConfig);
        //then
        result = deliveryCostCalculator.calculateDeliveryCost(shoppingCart);
        assertEquals(BigDecimal.valueOf(14), result);

        InOrder inOrder = Mockito.inOrder(deliveryCostRuleConfig);
        inOrder.verify(deliveryCostRuleConfig, times(1)).getCostPerDelivery();

    }

    @Test
    public void calculateDeliveryCost_ShouldReturnDeliveryCost_WhenThereAreMoreCategory() {
        //given
        Product computer = new Product("computer", BigDecimal.valueOf(27), electronics);
        products.put(computer, 12);
        products.put(apple, 2);
        shoppingCart.setProducts(products);
        //when
        when(deliveryCostRuleConfig.getCostPerDelivery()).thenReturn(BigDecimal.valueOf(7));
        when(deliveryCostRuleConfig.getCostPerProduct()).thenReturn(BigDecimal.TEN);
        when(deliveryCostRuleConfig.getFixedCost()).thenReturn(BigDecimal.valueOf(12));
        deliveryCostCalculator = new DeliveryCostCalculator(deliveryCostRuleConfig);
        //then
        result = deliveryCostCalculator.calculateDeliveryCost(shoppingCart);
        assertEquals(BigDecimal.valueOf(46), result);

        InOrder inOrder = Mockito.inOrder(deliveryCostRuleConfig);
        inOrder.verify(deliveryCostRuleConfig, times(1)).getCostPerDelivery();
        inOrder.verify(deliveryCostRuleConfig, times(1)).getCostPerProduct();
        inOrder.verify(deliveryCostRuleConfig, times(1)).getFixedCost();
        inOrder.verifyNoMoreInteractions();

    }
}