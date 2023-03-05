package com.ivlevks;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 0; i <= maxAttempts; i++) {
                if (i == maxAttempts) {
                    System.out.print("Ты не угадал");
                    break;
                }

                int n = scanner.nextInt();
                if (n == number) {
                    System.out.printf("Ты угадал с %d попытки!%n", i + 1);
                    break;
                }

                String attemptExpression = "попыток";
                if (i == 8) attemptExpression = "попытка";
                if (i < 8 && i > 4) attemptExpression = "попытки";
                if (n < number) {
                    System.out.printf("Мое число больше! Осталось %d %s%n", maxAttempts - i - 1, attemptExpression);
                } else System.out.printf("Мое число меньше! Осталось %d %s%n", maxAttempts - i - 1, attemptExpression);
            }
        }
    }
}
