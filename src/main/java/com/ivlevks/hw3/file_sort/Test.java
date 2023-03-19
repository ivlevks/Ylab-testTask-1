package com.ivlevks.hw3.file_sort;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(LocalDateTime.now());

        File dataFile = new Generator().generate("data.txt", 30_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true

        System.out.println(LocalDateTime.now());
    }
}
