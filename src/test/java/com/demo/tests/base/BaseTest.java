package com.demo.tests.base;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import com.demo.framework.base.DriverFactory;
import com.demo.framework.config.Environment;
import com.demo.framework.driver.DriverManager;
import com.demo.framework.pages.HomePage;
import com.demo.framework.utils.Waits;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    protected HomePage home;

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    @BeforeSuite(alwaysRun = true)
    public void writeAllureEnvironment() throws Exception {
        Path dir = Path.of("target", "allure-results");
        Files.createDirectories(dir);
        try (var w = new FileWriter(dir.resolve("environment.properties").toFile())) {
            w.write("BaseURL=" + Environment.baseUrl() + "\n");
            w.write("Browser=" + Environment.browser() + "\n");
            w.write("Headless=" + Environment.headless() + "\n");
            w.write("TimeoutSec=" + Environment.timeoutSec() + "\n");
            w.write("OS=" + System.getProperty("os.name") + "\n");
            w.write("Java=" + System.getProperty("java.version") + "\n");
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriver drv = DriverFactory.createDriver(Environment.browser());
        DriverManager.setDriver(drv);
        drv.get(Environment.baseUrl());
        Waits.waitForDocumentReady(drv, java.time.Duration.ofSeconds(Environment.timeoutSec()));
        home = new HomePage(driver()); // we start each and every test from home page
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        DriverManager.quitDriver();
    }
}