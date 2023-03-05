package com.ivlevks;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            if (n < 0 || m < 0) System.out.println("Input data is not valid");

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (j == m - 1) {
                        System.out.println(template);
                    } else System.out.print(template + " ");
                }
            }
        }
    }
}