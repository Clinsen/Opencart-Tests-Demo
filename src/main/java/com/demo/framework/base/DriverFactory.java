package com.demo.framework.base;

import com.demo.framework.config.Environment;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver(String browserName) {
        WebDriver driver;

        switch (browserName.toLowerCase()) {

            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ff = new FirefoxOptions();

                if (Environment.headless()) {
                    ff.addArguments("-headless");
                }

                ff.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new FirefoxDriver(ff);
                break;
            }

            case "chrome":
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions ch = new ChromeOptions();

                // Headless mode for CI
                if (Environment.headless()) {
                    ch.addArguments("--headless=new");
                }

                // For Linux + CI environments
                ch.addArguments(
                        "--disable-gpu",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--remote-allow-origins=*",
                        "--window-size=1440,900"
                );

                ch.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driver = new ChromeDriver(ch);
                break;
            }
        }

        // Global
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        // For non-headless
        if (!Environment.headless()) {
            driver.manage().window().setSize(new Dimension(1440, 900));
        }

        return driver;
    }
}