package com.ivlevks.lesson03.file_sort;

import java.io.*;
import java.util.*;

public class Sorter {
    int counterForJoin = 1;

    public File sortFile(File dataFile) throws IOException {
        long sizeOfChunk = 3_000_000;

        List<File> listFile = divideFileOnChunk(dataFile, sizeOfChunk);
        System.out.println("Complete divide");

        return mergeAllChunk(listFile);
    }

    private File mergeAllChunk(List<File> listFile) throws IOException {
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

        return deque.getFirst();
    }

    private File mergeTwoFiles(File firstFile, File secondFile) throws IOException {
        File result = new File(firstFile.getParentFile(), "joinData_" + counterForJoin++);

        Scanner scanFirstFile = new Scanner(new FileInputStream(firstFile));
        Scanner scanSecondFile = new Scanner(new FileInputStream(secondFile));
        PrintWriter writer = new PrintWriter(result);

        long value1 = scanFirstFile.nextLong();
        long value2 = scanSecondFile.nextLong();
        while (true) {
            if (value1 <= value2) {
                writer.println(value1);
                if (!scanFirstFile.hasNextLong()) {
                    if (!scanSecondFile.hasNextLong()){
                        writer.println(value2);
                    }
                    break;
                }
                value1 = scanFirstFile.nextLong();
            } else {
                writer.println(value2);
                if (!scanSecondFile.hasNextLong()) {
                    if (!scanFirstFile.hasNextLong()){
                        writer.println(value1);
                    }
                    break;
                }
                value2 = scanSecondFile.nextLong();
            }
        }

        if (scanFirstFile.hasNextLong()) {
            while (true) {
                writer.println(value1);
                if (!scanFirstFile.hasNextLong()) {
                    break;
                }
                value1 = scanFirstFile.nextLong();
            }
        }
        if (scanSecondFile.hasNextLong()) {
            while (true) {
                writer.println(value2);
                if (!scanSecondFile.hasNextLong()) {
                    break;
                }
                value2 = scanSecondFile.nextLong();
            }
        }

        firstFile.delete();
        secondFile.delete();
        scanFirstFile.close();
        scanSecondFile.close();
        writer.close();
        return result;
    }

    private List<File> divideFileOnChunk(File dataFile, long sizeOfChunk) throws IOException {
        List<File> result = new ArrayList<>();
        List<Long> listNumbers = new ArrayList<>();
        File parentFile = dataFile.getParentFile();
        int counter = 1;

        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            int counterLines = 0;

            while (scanner.hasNextLong()) {
                listNumbers.add(scanner.nextLong());
                counterLines++;

                if (counterLines == sizeOfChunk) {
                    result.add(sortAndWriteDataInChunk(listNumbers, parentFile, counter++));
                    counterLines = 0;
                    listNumbers.clear();
                }
            }

            if (!listNumbers.isEmpty()) {
                result.add(sortAndWriteDataInChunk(listNumbers, parentFile, counter++));
            }
        }
        return result;
    }

    private File sortAndWriteDataInChunk(List<Long> listNumbers, File parentFile, int counter) {
        File newFile = new File(parentFile, "tempData_" + counter);
        int counterLines = 0;

        Collections.sort(listNumbers);

        try (PrintWriter printWriter = new PrintWriter(newFile)) {

            for (Long number : listNumbers) {
                printWriter.println(number);
                counterLines++;
            }
            printWriter.flush();
            System.out.println("created " + newFile.getName() + " count lines writes  - " + counterLines);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return newFile;
    }
}
