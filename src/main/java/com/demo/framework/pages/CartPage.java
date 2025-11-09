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
    private final By couponToggle   = By.cssSelector("a[href='#collapse-coupon']");
    private final By couponInput    = By.id("input-coupon");
    private final By couponApplyBtn = By.id("button-coupon");
    private final By alertDanger    = By.cssSelector(".alert.alert-danger");

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

    public void openCouponSection() {
        click(couponToggle);
    }

    public void applyCoupon(String code) {
        type(couponInput, code);
        click(couponApplyBtn);
    }

    public boolean isCouponErrorShown() {
        return waitVisible(alertDanger).isDisplayed();
    }

    public String couponErrorText() {
        return text(alertDanger).trim().toLowerCase();
    }
}
