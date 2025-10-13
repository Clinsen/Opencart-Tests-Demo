package com.demo.tests.base;

import com.demo.framework.driver.DriverManager;
import com.demo.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.ITestResult.FAILURE;
import static org.testng.ITestResult.SUCCESS_PERCENTAGE_FAILURE;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[START] " + result.getMethod().getMethodName() +
                " | thread=" + Thread.currentThread().getId());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[PASS]  " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        captureArtifacts(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[SKIP]  " + result.getMethod().getMethodName());
    }

    private void captureArtifacts(ITestResult result) {
        WebDriver drv = DriverManager.getDriver();
        if (drv == null) return;

        try {
            String shot = ScreenshotUtils.take(drv, result.getMethod().getMethodName());
            System.out.println("[Screenshot] " + shot + " | URL: " + drv.getCurrentUrl());

           /* String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path htmlDir = Path.of("target", "pagesource");
            Files.createDirectories(htmlDir);
            Path htmlPath = htmlDir.resolve(result.getMethod().getMethodName() + "_" + ts + ".html");
            Files.writeString(htmlPath, drv.getPageSource());
            System.out.println("[HTML] " + htmlPath);*/

        } catch (Exception e) {
            System.out.println("Failed to capture artifacts: " + e.getMessage());
        }
    }
}
