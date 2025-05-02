package com.api.pharma.valueobjects;

/**
 * Represents an email address in the system.
 *
 * This Value Object is immutable and encapsulates the email address as a String.
 * It validates the email format upon creation.
 */
public class Email {

    private final String value;

    public Email(String value) {
        if (value == null || !isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.value = value;
    }

    private boolean isValidEmail(String value) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return value.matches(emailRegex);
    }

    public String getValue() {
        return value;
    }
}
