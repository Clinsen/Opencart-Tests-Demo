package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By container = By.cssSelector("body.checkout-checkout #content, #accordion");

    public CheckoutPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return waitVisible(container).isDisplayed();
    }
}