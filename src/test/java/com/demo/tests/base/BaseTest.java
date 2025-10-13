package com.demo.tests.base;

import com.demo.framework.base.DriverFactory;
import com.demo.framework.config.Environment;
import com.demo.framework.driver.DriverManager;
import com.demo.framework.utils.ScreenshotUtils;
import com.demo.framework.utils.Waits;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriver drv = DriverFactory.createDriver(Environment.browser());
        DriverManager.setDriver(drv);
        drv.get(Environment.baseUrl());
        Waits.waitForDocumentReady(drv, java.time.Duration.ofSeconds(Environment.timeoutSec()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        DriverManager.quitDriver();
    }
}