package com.demo.tests.regression;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndRegressionTest extends BaseTest {

    @Test(groups = "regression")
    public void fullPurchaseFlowReachability() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        Assert.assertTrue(results.resultsCount() > 0, "No results found");

        var product = results.openFirstProduct();
        Assert.assertTrue(product.isLoaded(), "Product page not loaded");
        Assert.assertTrue(product.getTitle().toLowerCase().contains(TestData.SEARCH_EXISTING.toLowerCase()));

        product.addToCart();

        var cart = home.header().openCartPage();
        Assert.assertTrue(cart.itemsCount() > 0, "Cart is empty");

        var checkout = cart.proceedToCheckout();
        Assert.assertTrue(checkout.isLoaded(), "Checkout page not loaded");
    }
}
