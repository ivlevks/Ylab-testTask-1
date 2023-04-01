package com.ivlevks.lesson02.complex_numbers;

public class ComplexNumbersTest {
    public static void main(String[] args) {
        ComplexNumbersImpl num1 = new ComplexNumbersImpl(-19, 10);
        ComplexNumbersImpl num2 = new ComplexNumbersImpl(5, -8);

        ComplexNumbers complexNumbers = new ComplexNumbersImpl();

        System.out.println(complexNumbers.add(num1, num2));
        System.out.println(complexNumbers.subtract(num1, num2));
        System.out.println(complexNumbers.multiply(num1, num2));
        System.out.println(complexNumbers.abs(num1));
    }
}
