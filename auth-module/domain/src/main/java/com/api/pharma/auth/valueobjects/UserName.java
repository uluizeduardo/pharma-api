package com.api.pharma.auth.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object that represents a valid and sanitized username.
 * Must follow system rules for allowed usernames.
 */
public class UserName {

    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{2,29}$");
    private final String value;

    public UserName(String value) {
        Objects.requireNonNull(value, "UserName cannot be null");

        String trimedValue = value.trim();

        if(!VALID_PATTERN.matcher(trimedValue).matches()) {
            throw new IllegalArgumentException("Invalid username: must start with a letter, 3-30 characters, only letters, digits and underscore.");
        }

        this.value = trimedValue.toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserName)) return false;
        UserName userName = (UserName) o;
        return value.equals(userName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
