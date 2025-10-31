package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @Test
    public void homePageLoadsSuccessfully() {
        Assert.assertTrue(home.isLoaded(), "Home page not loaded");
        Assert.assertTrue(home.header().isVisible(), "Header not visible");
    }
}