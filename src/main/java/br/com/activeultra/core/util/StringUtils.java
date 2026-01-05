package br.com.activeultra.core.util;

public class StringUtils {

    public static boolean isBlankOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlankOrNull(String value) {
        return !isBlankOrNull(value);
    }

}
