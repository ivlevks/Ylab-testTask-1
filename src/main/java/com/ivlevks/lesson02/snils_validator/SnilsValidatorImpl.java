package com.ivlevks.lesson02.snils_validator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        if (checkIsNotValidData(snils)) return false;

        char[] array = snils.toCharArray();
        int checkSum = Character.getNumericValue(array[9]) * 10 +
                Character.getNumericValue(array[10]);

        int sum = 0;
        int multiplier = 9;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(array[i]) * multiplier--;
        }

        if (sum == 100) sum = 0;
        if (sum > 100) sum = sum % 101;
        return sum == checkSum;
    }

    private boolean checkIsNotValidData(String snils) {
        return snils == null || snils.length() != 11 || snils.contains("\\D");
    }
}
