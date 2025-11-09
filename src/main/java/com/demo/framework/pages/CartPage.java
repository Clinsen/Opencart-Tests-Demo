package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import com.demo.framework.config.Environment;
import com.demo.framework.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.demo.framework.utils.Waits.waitForDocumentReady;

public class CartPage extends BasePage {
    private static final String SCOPE = "form[action*='checkout/cart/edit'] ";
    private final By cartRows = By.cssSelector(SCOPE +
            " .table-responsive > table.table-bordered tbody tr");
    private final By cartTableBody = By.cssSelector(SCOPE +
            " .table-responsive > table.table-bordered tbody");
    private final By firstProductLink = By.cssSelector(SCOPE +
            " .table-responsive > table.table-bordered tbody tr td:nth-child(2) a");
    private final By firstRow = By.cssSelector(SCOPE +
            " tbody tr:first-child");
    private final By qtyInput = By.cssSelector(SCOPE +
            " tbody tr:first-child input[name^='quantity']");
    private final By updateBtn= By.cssSelector(SCOPE +
            " tbody tr:first-child .btn.btn-primary");
    private final By unitPriceCell = By.cssSelector(SCOPE +
            " tbody tr:first-child td:nth-child(5)");
    private final By rowTotalCell  = By.cssSelector(SCOPE +
            " tbody tr:first-child td:nth-child(6)");
    private final By heading = By.cssSelector("#content h1");
    private final By proceedToCheckout = By.cssSelector(
            ".buttons .pull-right a.btn-primary, a[href*='route=checkout/checkout']");
    private final By couponToggle = By.cssSelector("a[href='#collapse-coupon']");
    private final By couponInput = By.id("input-coupon");
    private final By couponApplyBtn = By.id("button-coupon");
    private final By alertDanger = By.cssSelector(".alert.alert-danger");

    public CartPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return waitVisible(heading).isDisplayed();
    }

    public int itemsCount() {
        waitVisible(cartTableBody);
        return driver.findElements(cartRows).size();
    }

    public String firstItemName() {
        return text(firstProductLink);
    }

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckout);
        waitForDocumentReady(driver, Duration.ofSeconds(Environment.timeoutSec()));
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

    public double unitPrice() {
        waitVisible(firstRow);
        return PriceUtils.parse(text(unitPriceCell));
    }
    public double rowTotal() {
        waitVisible(firstRow);
        return PriceUtils.parse(text(rowTotalCell));
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

        waitUntil(d -> {
            try { return ExpectedConditions.stalenessOf(oldRow).apply(d); }
            catch (Exception e) { return true; }
        });
        waitVisible(firstRow);
        waitUntil(d -> !text(rowTotalCell).equals(before));
    }

    public int currentQuantity() {
        return Integer.parseInt(driver.findElement(qtyInput).getAttribute("value"));
    }
}
