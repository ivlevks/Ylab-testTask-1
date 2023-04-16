package com.ivlevks.lesson02.snils_validator;

public class SnilsValidatorTest {

    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();

        // snils with task
        System.out.println(snilsValidator.validate("01468870570"));
        System.out.println(snilsValidator.validate("90114404441"));
        System.out.println();

        // 5 valid snils
        System.out.println(snilsValidator.validate("43377217890"));
        System.out.println(snilsValidator.validate("20166507125"));
        System.out.println(snilsValidator.validate("63016757474"));
        System.out.println(snilsValidator.validate("70699471243"));
        System.out.println(snilsValidator.validate("82571638320"));
        System.out.println();

        // 5 not valid snils
        System.out.println(snilsValidator.validate("43123217890"));
        System.out.println(snilsValidator.validate("20543554357"));
        System.out.println(snilsValidator.validate("63767757474"));
        System.out.println(snilsValidator.validate("70678471243"));
        System.out.println(snilsValidator.validate("82571639320"));
        System.out.println();

        // again 5 not valid snils
        System.out.println(snilsValidator.validate(null));
        System.out.println(snilsValidator.validate("2054f907569"));
        System.out.println(snilsValidator.validate("637677574746546456"));
        System.out.println(snilsValidator.validate("706784"));
        System.out.println(snilsValidator.validate("dfhhj"));
    }
}
