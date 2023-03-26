package com.ivlevks.hw3.password_validator;

public class PasswordValidatorTest {
    public static void main(String[] args) {

        System.out.println(PasswordValidator.isValidData("admin_31", "123", "123"));
        System.out.println(PasswordValidator.isValidData("ad_min", "w12_12", "w12_12"));
        System.out.println();

        System.out.println(PasswordValidator.isValidData("admin", "12", "1234"));
        System.out.println(PasswordValidator.isValidData("admin???", "1234", "1234"));
        System.out.println(PasswordValidator.isValidData("admin", "!!1234", "!!1234"));

        System.out.println();
        System.out.println(PasswordValidator.isValidData("adminadminadminadminadm",
                "12", "1234"));
        System.out.println(PasswordValidator.isValidData("admin",
                "12345123451234512345", "12345123451234512345"));
        System.out.println(PasswordValidator.isValidData("admin",
                "12345123451234512345", "12"));
    }
}
