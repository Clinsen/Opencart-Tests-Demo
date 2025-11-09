package com.demo.tests.functional;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;

public class CouponValidationTests extends BaseTest {

    @Test(groups = "functional")
    public void invalidCouponShowsErrorOnCart() {
        var results = home.header().search(TestData.SEARCH_EXISTING);
        var product = results.openFirstProduct();
        product.addToCart();

        var cart = home.header().openCartPage();
        Assert.assertTrue(cart.itemsCount() > 0, "Cart should not be empty for coupon test");

        cart.openCouponSection();
        cart.applyCoupon("INVALID-COUPON-STRING");

        Assert.assertTrue(cart.isCouponErrorShown(), "Error alert not shown");

        String msg = cart.couponErrorText().toLowerCase();
        String[] ua = { "помил", "неправиль", "просроч", "перевищ" };
        String[] en = { "err", "invalid", "expired", "exceed" };

        boolean matchesUa = Arrays.stream(ua).anyMatch(msg::contains);
        boolean matchesEn = Arrays.stream(en).anyMatch(msg::contains);

        Assert.assertTrue(matchesUa || matchesEn, "Unexpected alert text: " + msg);
    }
}