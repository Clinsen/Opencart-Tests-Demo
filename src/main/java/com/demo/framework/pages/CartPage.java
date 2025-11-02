package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartRows = By.cssSelector(
            "form[action*='checkout/cart/edit'] .table-responsive > table.table-bordered tbody tr");
    private final By cartTableBody = By.cssSelector(
            "form[action*='checkout/cart/edit'] .table-responsive > table.table-bordered tbody");
    private final By firstProductLink = By.cssSelector(
            "form[action*='checkout/cart/edit'] .table-responsive > table.table-bordered tbody tr td:nth-child(2) a");
    private final By heading = By.cssSelector("#content h1");
    private final By proceedToCheckout = By.cssSelector(
            ".buttons .pull-right a.btn-primary, a[href*='route=checkout/checkout']");
    private final By emptyHeading = By.cssSelector("#content h1");

    public CartPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return waitVisible(heading).isDisplayed();
    }

    public int itemsCount() {
        waitVisible(cartTableBody);
        return driver.findElements(cartRows).size();
    }

    public String firstItemName() {
        return driver.findElement(firstProductLink).getText();
    }

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckout);
        return new CheckoutPage(driver);
    }
}
