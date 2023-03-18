package com.ivlevks.hw3.file_sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {
    public File generate(String name, int count) throws FileNotFoundException {
        Random random = new Random();
        File file = new File(name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < count; i++) {
                pw.println(random.nextLong());
            }
            pw.flush();
        }
        return file;
    }
}
