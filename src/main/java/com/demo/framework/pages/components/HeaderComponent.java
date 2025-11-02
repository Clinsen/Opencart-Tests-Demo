package com.demo.framework.pages.components;

import com.demo.framework.base.BasePage;
import com.demo.framework.pages.CartPage;
import com.demo.framework.pages.SearchResultsPage;
import org.openqa.selenium.*;

public class HeaderComponent extends BasePage {
    private final By searchInput = By.name("search");
    private final By cartToggle = By.cssSelector("#cart .dropdown-toggle");
    private final By viewCart   = By.cssSelector("#cart .dropdown-menu a[href*='route=checkout/cart']");

    public boolean isVisible() {
        return waitVisible(By.id("logo")).isDisplayed();
    }

    public HeaderComponent(WebDriver driver) { super(driver); }

    public SearchResultsPage search(String query) {
        type(searchInput, query);
        driver.findElement(searchInput).sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public CartPage openCartPage() {
        click(cartToggle);
        click(viewCart);
        return new CartPage(driver);
    }
}
