package com.yash.api_error_lab.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(
                "User already exists with email : " + email
        );
    }
}
