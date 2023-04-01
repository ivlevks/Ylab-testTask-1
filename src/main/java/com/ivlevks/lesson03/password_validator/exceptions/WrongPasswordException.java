package com.ivlevks.lesson03.password_validator.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
