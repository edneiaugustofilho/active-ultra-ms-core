package br.com.activeultra.core.util;

import java.util.List;

public class StringUtils {

    public static boolean isBlankOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlankOrNull(String value) {
        return !isBlankOrNull(value);
    }

    public static String trimOrNull(String value) {
        return value == null ? null : value.trim();
    }

    public static String concatenate(List<String> stringList, String separator) {
        final String sep = separator == null ? " " : separator;

        final StringBuilder result = new StringBuilder();
        stringList.forEach(s -> {
            if (s != null && !s.isEmpty()) {
                if (!result.isEmpty()) {
                    result.append(sep);
                }
                result.append(s);
            }
        });

        return result.toString();
    }
}
