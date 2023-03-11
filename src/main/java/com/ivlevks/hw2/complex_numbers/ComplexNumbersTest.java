package com.ivlevks.hw2.complex_numbers;

public class ComplexNumbersTest {
    public static void main(String[] args) {
        ComplexNumber num1 = new ComplexNumber(3, -6);
        ComplexNumber num2 = new ComplexNumber(5, -8);

        System.out.println(ComplexNumber.add(num1, num2));
        System.out.println(ComplexNumber.subtract(num1, num2));
        System.out.println(ComplexNumber.multiply(num1, num2));
        System.out.println(ComplexNumber.abs(num1));
    }
}
