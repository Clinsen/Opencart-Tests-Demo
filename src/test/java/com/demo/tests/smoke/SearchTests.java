package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    @DataProvider
    public Object[][] searchData() {
        return new Object[][]{
                { TestData.SEARCH_EXISTING, true },
                { TestData.SEARCH_ABSENT, false }
        };
    }

    @Test(dataProvider = "searchData")
    public void searchBehavesAsExpected(String query, boolean expectResults) {
        Assert.assertTrue(home.isLoaded(), "Home not loaded");

        var results = home.header().search(query);
        int count = results.resultsCount();

        if (expectResults) {
            Assert.assertTrue(count > 0, "Expected results > 0");
            Assert.assertTrue(
                    results.headingText().toLowerCase().contains(query.toLowerCase()),
                    "Heading should contain search query"
            );
        } else {
            Assert.assertEquals(count, 0, "Expected 0 results");
        }
    }
}