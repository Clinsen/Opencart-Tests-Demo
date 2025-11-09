package com.demo.framework.utils;

public final class PriceUtils {
    private PriceUtils() {}

    public static double parse(String s) {
        String digits = s.replaceAll("[^0-9.,]", "");
        if (digits.isEmpty()) return 0.0;
        int dot = digits.lastIndexOf('.');
        int comma = digits.lastIndexOf(',');
        int sep = Math.max(dot, comma);
        if (sep != -1 && (digits.length() - sep - 1) >= 1 && (digits.length() - sep - 1) <= 2) {
            String ip = digits.substring(0, sep).replaceAll("\\D", "");
            String fp = digits.substring(sep + 1).replaceAll("\\D", "");
            if (ip.isEmpty()) ip = "0";
            if (fp.isEmpty()) fp = "0";
            return Double.parseDouble(ip + "." + fp);
        }

        String whole = digits.replaceAll("\\D", "");
        return whole.isEmpty() ? 0.0 : Double.parseDouble(whole);
    }
}
