package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import java.util.List;
import org.openqa.selenium.*;

public class SearchResultsPage extends BasePage {
    private final By productCards = By.cssSelector(".product-thumb");
    private final By heading = By.cssSelector("#content h1");
    private final By firstProduct = By.cssSelector(".product-thumb h4 a");

    public SearchResultsPage(WebDriver driver) { super(driver); }

    public int resultsCount() {
        try { waitVisible(productCards); } catch (TimeoutException ignored) {}
        List<WebElement> items = driver.findElements(productCards);
        return items.size();
    }

    public ProductPage openFirstProduct() {
        click(firstProduct);
        return new ProductPage(driver);
    }

    public String headingText() { return text(heading); }
}
