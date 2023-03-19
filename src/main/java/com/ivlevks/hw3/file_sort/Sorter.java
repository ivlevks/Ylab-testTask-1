package com.ivlevks.hw3.file_sort;

import java.io.*;
import java.util.*;

public class Sorter {
    int counterForJoin = 1;

    public File sortFile(File dataFile) throws IOException {
//        long sizeOfChunk = Runtime.getRuntime().maxMemory() / 2;
        long sizeOfChunk = 200_000_000L;

        List<File> listFile = divideFileOnChunk(dataFile, sizeOfChunk);
        System.out.println("Complete divide");
        sortEachChunck(listFile);
        File result = mergeAllChunck(listFile);
        return result;
    }

    private File mergeAllChunck(List<File> listFile) throws IOException {
        Deque<File> deque = new ArrayDeque<>();
        for (File file : listFile) {
            deque.offer(file);
        }

        while (deque.size() != 1) {
            File first = deque.poll();
            File second = deque.poll();
            File mergedFile = mergeTwoFiles(first, second);
            System.out.println("merge " + first.getName() + "   " + second.getName());
            deque.offer(mergedFile);
        }
        System.out.println("Merge completed");

        File result = deque.getFirst();
        return result;
    }


    private File mergeTwoFiles(File firstFile, File secondFile) throws IOException {
        File result = new File(firstFile.getParentFile(), "joinData_" + counterForJoin++);

        Scanner scanFirstFile = new Scanner(new FileInputStream(firstFile));
        Scanner scanSecondFile = new Scanner(new FileInputStream(secondFile));
        PrintWriter writer = new PrintWriter(result);

        long value1 = 0;
        long value2 = 0;
        boolean writeFirstValue = true;
        boolean writeSecondValue = true;
        while (scanFirstFile.hasNextLong() && scanSecondFile.hasNextLong()) {
            if (writeFirstValue) {
                value1 = scanFirstFile.nextLong();
            }
            if (writeSecondValue) {
                value2 = scanSecondFile.nextLong();
            }

            if (value1 <= value2) {
                writer.println(value1);
                writeFirstValue = true;
                writeSecondValue = false;
            } else {
                writer.println(value2);
                writeFirstValue = false;
                writeSecondValue = true;
            }
        }

        if (!writeFirstValue) {
            writer.println(value2);
        }
        if (!writeSecondValue) {
            writer.println(value1);
        }

        while (scanFirstFile.hasNextLong()) {
            value1 = scanFirstFile.nextLong();
            writer.println(value1);
        }
        while (scanSecondFile.hasNextLong()) {
            value2 = scanSecondFile.nextLong();
            writer.println(value2);
        }

        firstFile.delete();
        secondFile.delete();
        scanFirstFile.close();
        scanSecondFile.close();
        writer.close();
        return result;
    }

    private void sortEachChunck(List<File> listFile) throws IOException {
        System.out.println("Start sort divided file");
        List<Long> list = new ArrayList<>();

        for (File file : listFile) {
            list.clear();
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 Scanner scanner = new Scanner(fileInputStream)) {
                while (scanner.hasNextLong()) {
                    long number = scanner.nextLong();
                    list.add(number);
                }
            }

            Collections.sort(list);

            try (PrintWriter printWriter = new PrintWriter(file)) {
                for (Long num : list) {
                    printWriter.println(num);
                    printWriter.flush();
                }
            }
            System.out.println("sorted " + file.getName() + " count lines writes  - " + list.size());
        }
    }

    private List<File> divideFileOnChunk(File dataFile, long sizeOfChunk) throws IOException {
        List<File> result = new ArrayList<>();
        int counter = 1;

        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {

            while (scanner.hasNextLong()) {
                File newFile = new File(dataFile.getParentFile(), "tempData_" + counter++);
                int counterLines = 0;
                try (PrintWriter printWriter = new PrintWriter(newFile)) {

                    while (scanner.hasNextLong()) {
                        if (newFile.length() + 8 > sizeOfChunk) break;
                        long number = scanner.nextLong();
                        printWriter.println(number);
                        counterLines++;
                        printWriter.flush();
                    }
                    result.add(newFile);
                    System.out.println("created " + newFile.getName() + " count lines writes  - " + counterLines);
                }
            }
        }
        return result;
    }
}
