package com.ivlevks.hw3.password_validator;

import com.ivlevks.hw3.password_validator.exceptions.WrongLoginException;
import com.ivlevks.hw3.password_validator.exceptions.WrongPasswordException;

public class PasswordValidator {

    public static boolean isValidData(String login, String password, String confirmPassword) {

        boolean result = true;
        String regex = "\\w+";

        try {
            if (!login.matches(regex)) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }

            if (login.length() >= 20) {
                throw new WrongLoginException("Логин слишком длинный");
            }

            if (!password.matches(regex)) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }

            if (password.length() >= 20) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }

            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException e) {
            result = false;
            System.out.println(e.getMessage());
        }

        return result;
    }
}
