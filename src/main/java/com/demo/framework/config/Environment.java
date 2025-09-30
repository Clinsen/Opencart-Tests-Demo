package com.demo.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Environment {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Environment.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) props.load(in); // дефолти з resources
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    private static String get(String key, String def) {
        return System.getProperty(key, props.getProperty(key, def)); // System props мають пріоритет
    }

    public static String baseUrl()   { return get("baseUrl", "https://demo.opencart.ua/"); }
    public static String browser()   { return get("browser", "chrome"); }
    public static boolean headless() { return Boolean.parseBoolean(get("headless", "false")); }
    public static int timeoutSec()   { return Integer.parseInt(get("timeoutSec", "10")); }
    private Environment() {}
}
