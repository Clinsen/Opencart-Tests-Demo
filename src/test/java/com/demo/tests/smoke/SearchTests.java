package com.demo.tests.smoke;

import com.demo.tests.base.BaseTest;
import com.demo.framework.pages.HomePage;
import com.demo.tests.data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    @Test
    public void searchExistingShowsResults() {
        var home = new HomePage(driver());
        Assert.assertTrue(home.isLoaded(), "Home not loaded");

        var results = home.header().search(TestData.SEARCH_EXISTING);
        Assert.assertTrue(results.resultsCount() > 0, "Expected results > 0");
        Assert.assertTrue(results.headingText().toLowerCase().contains("macbook"));
    }

    @Test
    public void searchAbsentShowsZero() {
        var home = new HomePage(driver());
        var results = home.header().search(TestData.SEARCH_ABSENT);
        Assert.assertEquals(results.resultsCount(), 0, "Expected 0 results");
    }
}
