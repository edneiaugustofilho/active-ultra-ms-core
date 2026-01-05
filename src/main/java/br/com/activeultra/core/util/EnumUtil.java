package br.com.activeultra.core.util;

import java.util.ArrayList;
import java.util.List;

public class EnumUtil {

    public static <E extends Enum<E>> E parseEnum(Class<E> enumType, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Enum.valueOf(enumType, value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid value '" + value + "' for enum " + enumType.getSimpleName(), ex
            );
        }
    }

    public static String toString(Enum<?> enumValue) {
        if (enumValue == null) {
            return null;
        }

        return enumValue.name();
    }

    public static <E extends Enum<E>> void validateEnum(
            String fieldName,
            Class<E> enumType,
            String value,
            List<String> errors
    ) {
        if (isBlank(value)) {
            return; // nullable enums are allowed
        }
        try {
            // if your API sends exact enum names, remove toUpperCase()
            Enum.valueOf(enumType, value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            errors.add(String.format(
                    "Invalid value '%s' for %s. Allowed values: %s",
                    value,
                    fieldName,
                    String.join(", ", enumNames(enumType))
            ));
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static <E extends Enum<E>> List<String> enumNames(Class<E> enumType) {
        List<String> names = new ArrayList<>();
        for (E e : enumType.getEnumConstants()) {
            names.add(e.name());
        }
        return names;
    }

}
