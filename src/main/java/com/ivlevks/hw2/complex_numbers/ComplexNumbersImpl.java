package com.ivlevks.hw2.complex_numbers;

import java.util.Objects;

public class ComplexNumbersImpl implements ComplexNumbers {
    private double Re;
    private double Im;

    public ComplexNumbersImpl() {
    }

    public ComplexNumbersImpl(double re) {
        Re = re;
        Im = 0;
    }

    public ComplexNumbersImpl(double re, double im) {
        Re = re;
        Im = im;
    }

    @Override
    public ComplexNumbersImpl add(ComplexNumbersImpl first, ComplexNumbersImpl second) {

        checkArgumentsForNull(first, second);

        double resultRe = first.Re + second.Re;
        double resultIm = first.Im + second.Im;

        checkDoubleOverflow(resultRe, resultIm);

        return new ComplexNumbersImpl(resultRe, resultIm);
    }

    @Override
    public ComplexNumbersImpl subtract(ComplexNumbersImpl first, ComplexNumbersImpl second) {

        checkArgumentsForNull(first, second);

        double resultRe = first.Re - second.Re;
        double resultIm = first.Im - second.Im;

        checkDoubleOverflow(resultRe, resultIm);

        return new ComplexNumbersImpl(resultRe, resultIm);
    }

    @Override
    public ComplexNumbersImpl multiply(ComplexNumbersImpl first, ComplexNumbersImpl second) {

        checkArgumentsForNull(first, second);

        double resultRe = first.Re * second.Re - first.Im * second.Im;
        double resultIm = first.Re * second.Im + second.Re * first.Im;

        checkDoubleOverflow(resultRe, resultIm);

        return new ComplexNumbersImpl(resultRe, resultIm);
    }

    @Override
    public double abs(ComplexNumbersImpl number) {
        double result = Math.sqrt(number.Re * number.Re + number.Im * number.Im);

        // 3 sign after points
        double scale = 1000;
        return Math.round(result * scale) / scale;
    }

    private void checkArgumentsForNull(ComplexNumbersImpl first, ComplexNumbersImpl second) {
        if (first == null || second == null) throw new NullPointerException("arguments is null");
    }

    private void checkDoubleOverflow(double resultRe, double resultIm) {
        if (resultRe == Double.POSITIVE_INFINITY || resultRe == Double.NEGATIVE_INFINITY ||
                Double.isNaN(resultRe)) throw new ArithmeticException("double overflow");

        if (resultIm == Double.POSITIVE_INFINITY || resultIm == Double.NEGATIVE_INFINITY ||
                Double.isNaN(resultIm)) throw new ArithmeticException("double overflow");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumbersImpl number = (ComplexNumbersImpl) o;
        return Double.compare(number.Re, Re) == 0 && Double.compare(number.Im, Im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Re, Im);
    }

    @Override
    public String toString() {
        if (Im == 0) return String.valueOf(Re);
        if (Im < 0) return Re + "" + Im + "i";
        else return Re + "+" + Im + "i";
    }
}
