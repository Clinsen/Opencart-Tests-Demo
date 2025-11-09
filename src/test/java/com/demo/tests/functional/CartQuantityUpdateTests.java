package com.demo.tests.functional;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartQuantityUpdateTests extends BaseTest {

    @Test(groups = "functional")
    public void updatingQuantityRecalculatesRowTotal() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        var product = results.openFirstProduct();
        product.addToCart();

        var cart = home.header().openCartPage();
        Assert.assertTrue(cart.itemsCount() > 0, "Cart is empty");

        double unit = cart.unitPrice();

        cart.setQuantity(2);
        cart.applyQuantityUpdate();
        Assert.assertEquals(cart.currentQuantity(), 2, "Quantity not updated");

        double expected = unit * 2;
        double actual   = cart.rowTotal();

        Assert.assertEquals(actual, expected, 0.01, "Row total mismatch after quantity update");
    }
}
