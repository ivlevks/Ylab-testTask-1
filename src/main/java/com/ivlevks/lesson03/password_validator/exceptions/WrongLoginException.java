package com.ivlevks.lesson03.password_validator.exceptions;

public class WrongLoginException extends Exception {

    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }
}
