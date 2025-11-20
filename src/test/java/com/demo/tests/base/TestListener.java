package com.demo.tests.base;

import com.demo.framework.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Instant;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        var driver = DriverManager.getDriver();
        if (driver instanceof TakesScreenshot ts) {
            byte[] png = ts.getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(
                    "Screenshot - " + Instant.now(),
                    "image/png",
                    "png",
                    png
            );
        }

        try {
            Allure.addAttachment("Current URL", "text/plain", driver.getCurrentUrl());
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Allure.addAttachment("Test start", "text/plain", "Starting: " + name);
    }

    @Override public void onTestSuccess(ITestResult result) { }
    @Override public void onTestSkipped(ITestResult result) { }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override public void onTestFailedWithTimeout(ITestResult result) { }
    @Override public void onStart(ITestContext context) { }
    @Override public void onFinish(ITestContext context) { }
}
