package br.com.activeultra.core.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator {

    public static boolean isMajorOf18(LocalDate birthDate) {
        return isMajorOf(birthDate, 18);
    }

    public static boolean isMajorOf(LocalDate birthDate, int majorAge) {
        if (birthDate == null) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= majorAge;
    }

}
