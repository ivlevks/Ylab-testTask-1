package com.ivlevks.hw2.complex_numbers;

public interface ComplexNumbers {

    ComplexNumbersImpl add(ComplexNumbersImpl first, ComplexNumbersImpl second);

    ComplexNumbersImpl subtract(ComplexNumbersImpl first, ComplexNumbersImpl second);

    ComplexNumbersImpl multiply(ComplexNumbersImpl first, ComplexNumbersImpl second);

    double abs(ComplexNumbersImpl number);

}
