package com.demo.framework.utils;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public final class Waits {

    /** Чекає поки document.readyState стане 'complete' */
    public static void waitForDocumentReady(WebDriver driver, Duration timeout) {
        new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(Exception.class)
                .until(d -> "complete".equals(
                        ((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    private Waits() {}
}
