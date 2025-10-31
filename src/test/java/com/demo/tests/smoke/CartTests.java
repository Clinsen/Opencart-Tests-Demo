package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @Test
    public void canAddProductToCartFromProductPage() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        var product = results.openFirstProduct();

        Assert.assertTrue(product.isLoaded(), "Product page not loaded");

        product.addToCart();
        Assert.assertTrue(product.isAddToCartSuccess(), "No success alert after adding to the cart");
    }
}