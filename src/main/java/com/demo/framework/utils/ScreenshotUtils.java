package com.demo.framework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {
    private static final Path DIR = Path.of("target", "screenshots");

    public static String take(WebDriver driver, String name) throws IOException {
        Files.createDirectories(DIR);
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        Path dest = DIR.resolve(ts + "_" + safe(name) + ".png");
        Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        return dest.toString();
    }

    private static String safe(String s) { return s.replaceAll("[^a-zA-Z0-9._-]", "_"); }
    private ScreenshotUtils() {}
}
