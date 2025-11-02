package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private final By title = By.cssSelector("#content h1");
    private final By addToCart = By.id("button-cart");
    private final By successAlert = By.cssSelector(".alert-success");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return waitVisible(title).isDisplayed();
    }

    public String getTitle() {
        return text(title);
    }

    public void addToCart() {
        click(addToCart);
        waitVisible(successAlert);
    }

    public boolean isAddToCartSuccess() {
        return waitVisible(successAlert).isDisplayed();
    }
}
