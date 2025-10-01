package com.demo.framework.pages;

import com.demo.framework.base.BasePage;
import com.demo.framework.pages.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By searchInput = By.name("search");

    public HomePage(WebDriver driver) { super(driver); }

    /** The page is considered to be loaded when the search input is visible */
    public boolean isLoaded() {
        try {
            waitVisible(searchInput);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HeaderComponent header() { return new HeaderComponent(driver); }
}
