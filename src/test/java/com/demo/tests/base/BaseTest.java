package com.demo.tests.base;

import com.demo.framework.base.DriverFactory;
import com.demo.framework.config.Environment;
import com.demo.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver(Environment.browser());
        driver.get(Environment.baseUrl());
        com.demo.framework.utils.Waits.waitForDocumentReady(driver, java.time.Duration.ofSeconds(10));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess() && driver != null) {
            try {
                String path = ScreenshotUtils.take(driver, result.getName());
                System.out.println("Saved screenshot: " + path);
            } catch (Exception e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        }
        if (driver != null) driver.quit();
    }
}
