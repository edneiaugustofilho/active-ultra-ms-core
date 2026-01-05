package br.com.activeultra.core.util;

public class NameValidator {

    public static boolean isValidFullName(String fullName) {
        if (isValidName(fullName)) {
            String[] parts = fullName.split(" ");
            return parts.length >= 2 && isValidName(parts[0]) &&
                    atLeastOneValidSirName(parts);
        }

        return false;
    }

    private static boolean atLeastOneValidSirName(String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            if (isValidName(parts[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidName(String name) {
        return name != null && name.length() >= 2;
    }

}
