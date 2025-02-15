package com.api.pharma.utilities.constants;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class MessageConstants {

    public static class UserMessages {
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String USER_NOT_FOUND_BY_EMAIL = "User not found to email: ";
        public static final String ACCOUNT_DISABLED = "User account is disabled";
        public static final String ACCOUNT_LOCKED = "User account is blocked";
        public static final String INVALID_CREDENTIALS = "Bad credentials, Incorrect email or password.";
        public static final String AUTHENTICATION_FAILED = "Authentication failed";
    }

    public static class HttpStatusCodes {

        private static final Map<HttpStatus, String> ERROR_CODES = Map.ofEntries(
                // 2xx Success
                Map.entry(HttpStatus.OK, "200"),
                Map.entry(HttpStatus.CREATED, "201"),
                Map.entry(HttpStatus.ACCEPTED, "202"),
                Map.entry(HttpStatus.NO_CONTENT, "204"),

                // 3xx Redirection
                Map.entry(HttpStatus.MOVED_PERMANENTLY, "301"),
                Map.entry(HttpStatus.FOUND, "302"),
                Map.entry(HttpStatus.SEE_OTHER, "303"),
                Map.entry(HttpStatus.NOT_MODIFIED, "304"),
                Map.entry(HttpStatus.TEMPORARY_REDIRECT, "307"),
                Map.entry(HttpStatus.PERMANENT_REDIRECT, "308"),

                // 4xx Client Errors
                Map.entry(HttpStatus.BAD_REQUEST, "400"),
                Map.entry(HttpStatus.UNAUTHORIZED, "401"),
                Map.entry(HttpStatus.FORBIDDEN, "403"),
                Map.entry(HttpStatus.NOT_FOUND, "404"),
                Map.entry(HttpStatus.METHOD_NOT_ALLOWED, "405"),
                Map.entry(HttpStatus.NOT_ACCEPTABLE, "406"),
                Map.entry(HttpStatus.CONFLICT, "409"),
                Map.entry(HttpStatus.GONE, "410"),
                Map.entry(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "415"),
                Map.entry(HttpStatus.TOO_MANY_REQUESTS, "429"),

                // 5xx Server Errors
                Map.entry(HttpStatus.INTERNAL_SERVER_ERROR, "500"),
                Map.entry(HttpStatus.NOT_IMPLEMENTED, "501"),
                Map.entry(HttpStatus.BAD_GATEWAY, "502"),
                Map.entry(HttpStatus.SERVICE_UNAVAILABLE, "503"),
                Map.entry(HttpStatus.GATEWAY_TIMEOUT, "504")
        );

        public static String getErrorCode(HttpStatus httpStatus){
            return ERROR_CODES.getOrDefault(httpStatus, "500");
        }
    }
}
