package com.ivlevks.hw3.password_validator.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
