package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartContentsTests extends BaseTest {

    @Test
    public void cartShowsAddedItem() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        var product = results.openFirstProduct();
        Assert.assertTrue(product.isLoaded(), "Product page not loaded");

        product.addToCart();
        var cart = home.header().openCartPage();

        Assert.assertTrue(cart.isLoaded(), "Cart page not loaded");
        Assert.assertTrue(cart.itemsCount() > 0, "Cart should contain at least one item");
        Assert.assertTrue(
                cart.firstItemName().toLowerCase().contains(TestData.SEARCH_EXISTING.toLowerCase()),
                "First cart item does not match search query"
        );
    }
}
