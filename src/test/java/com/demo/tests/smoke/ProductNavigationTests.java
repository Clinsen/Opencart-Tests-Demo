package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductNavigationTests extends BaseTest {

    @Test
    public void productPageOpensFromSearchResults() {
        var results = home.header().search(TestData.SEARCH_EXISTING);

        Assert.assertTrue(results.resultsCount() > 0, "No results for search query");

        var productPage = results.openFirstProduct();
        Assert.assertTrue(productPage.isLoaded(), "Product page not loaded");
        Assert.assertTrue(
                productPage.getTitle().toLowerCase().contains(TestData.SEARCH_EXISTING.toLowerCase()),
                "Product title does not match search query"
        );
    }
}