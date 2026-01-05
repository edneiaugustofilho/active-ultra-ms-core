package br.com.activeultra.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressValidator {

    public static boolean isValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // Simplified email regex
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}
