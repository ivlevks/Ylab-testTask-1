package com.ivlevks.lesson03.file_sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Validator {
    private File file;
    private int counter = 0;
    public Validator(File file) {
        this.file = file;
    }
    public boolean isSorted() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                counter++;
                if (current < prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            System.out.println("Total count lines - " + counter);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
