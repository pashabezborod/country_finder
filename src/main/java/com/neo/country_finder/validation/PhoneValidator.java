package com.neo.country_finder.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    Set<Character> allowedChars = new HashSet<>(Arrays.asList('_', '.', '-', '(', ')', '+'));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        int digitCount = value.replaceAll("\\D", "").length();
        if (digitCount < 5 || digitCount > 15) return false;
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i)) && !allowedChars.contains(value.charAt(i)))
                return false;
        }
        return true;
    }
}
