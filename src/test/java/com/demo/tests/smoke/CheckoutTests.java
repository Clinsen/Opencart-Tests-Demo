package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @Test
    public void checkoutPageReachableFromCart() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        var product = results.openFirstProduct();
        product.addToCart();

        var cart = home.header().openCartPage();
        Assert.assertTrue(cart.itemsCount() > 0, "Cart is empty");

        var checkout = cart.proceedToCheckout();
        Assert.assertTrue(checkout.isLoaded(), "Checkout page not loaded");
    }
}