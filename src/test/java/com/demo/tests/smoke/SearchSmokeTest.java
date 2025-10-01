package com.demo.tests.smoke;

import com.demo.framework.pages.HomePage;
import com.demo.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchSmokeTest extends BaseTest {

    @Test
    public void searchMacBookShowsResults() {
        HomePage home = new HomePage(driver());
        Assert.assertTrue(home.isLoaded(), "Home page is not loaded (search input not visible).");

        var results = home.header().search("MacBook");
        Assert.assertTrue(results.resultsCount() > 0, "Expected at least 1 search result.");
        Assert.assertTrue(results.headingText().toLowerCase().contains("macbook"),
                "Heading should mention 'MacBook'.");
    }
}
