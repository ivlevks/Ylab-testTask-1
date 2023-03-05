package com.ivlevks;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        int n = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextInt();
        }

        if (n < 0) {
            System.out.println("Input data is not valid");
            System.exit(0);
        }

        long[] arrayNumbersPell = new long[n + 1];
        arrayNumbersPell[0] = 0;
        arrayNumbersPell[1] = 1;

        for (int i = 2; i <= n; i++) {
            arrayNumbersPell[i] = arrayNumbersPell[i - 1] * 2 + arrayNumbersPell[i - 2];
        }

        System.out.println(arrayNumbersPell[n]);
    }
}
