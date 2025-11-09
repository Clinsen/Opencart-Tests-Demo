package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import com.demo.framework.config.Environment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
    private final By couponToggle = By.cssSelector("a[href='#collapse-coupon']");
    private final By couponInput = By.id("input-coupon");
    private final By couponApplyBtn = By.id("button-coupon");
    private final By alertDanger = By.cssSelector(".alert.alert-danger");
    private final By firstRow      = By.cssSelector("form[action*='checkout/cart/edit'] tbody tr:first-child");
    private final By qtyInput      = By.cssSelector("form[action*='checkout/cart/edit'] tbody tr:first-child input[name^='quantity']");
    private final By updateBtn     = By.cssSelector("form[action*='checkout/cart/edit'] tbody tr:first-child .btn.btn-primary");
    private final By unitPriceCell = By.cssSelector("form[action*='checkout/cart/edit'] tbody tr:first-child td:nth-child(5)");
    private final By rowTotalCell  = By.cssSelector("form[action*='checkout/cart/edit'] tbody tr:first-child td:nth-child(6)");

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
    private double parsePrice(String s) {
        String digits = s.replaceAll("[^0-9.,]", "");
        if (digits.isEmpty()) return 0.0;

        int lastDot = digits.lastIndexOf('.');
        int lastComma = digits.lastIndexOf(',');
        int sep = Math.max(lastDot, lastComma);

        if (sep != -1 && (digits.length() - sep - 1) >= 1 && (digits.length() - sep - 1) <= 2) {
            String intPart = digits.substring(0, sep).replaceAll("[^0-9]", "");
            String fracPart= digits.substring(sep + 1).replaceAll("[^0-9]", "");
            if (intPart.isEmpty()) intPart = "0";
            if (fracPart.isEmpty()) fracPart = "0";
            return Double.parseDouble(intPart + "." + fracPart);
        } else {
            String whole = digits.replaceAll("[^0-9]", "");
            return whole.isEmpty() ? 0.0 : Double.parseDouble(whole);
        }
    }

    public double unitPrice() {
        waitVisible(firstRow);
        return parsePrice(text(unitPriceCell));
    }

    public double rowTotal() {
        waitVisible(firstRow);
        return parsePrice(text(rowTotalCell));
    }

    public void setQuantity(int qty) {
        var el = waitVisible(qtyInput);
        el.clear();
        el.sendKeys(Integer.toString(qty));
    }

    public void applyQuantityUpdate() {
        WebElement oldRow = driver.findElement(firstRow);
        String before = text(rowTotalCell);

        click(updateBtn);

        new WebDriverWait(driver, Duration.ofSeconds(Environment.timeoutSec()))
                .until(ExpectedConditions.stalenessOf(oldRow));

        waitVisible(firstRow);
        new WebDriverWait(driver, Duration.ofSeconds(Environment.timeoutSec()))
                .until(d -> !text(rowTotalCell).equals(before));
    }

    public int currentQuantity() {
        return Integer.parseInt(driver.findElement(qtyInput).getAttribute("value"));
    }
}
