package com.ivlevks.hw3.file_sort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
//        File dataFile = new Generator().generate("data.txt", 3_000_000);
        File dataFile = new File("data.txt");
//        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
//        System.out.println(new Validator(sortedFile).isSorted()); // true


//        // Get current size of heap in bytes
//        long heapSize = Runtime.getRuntime().totalMemory() / 1000 / 1000;
//
//        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
//        long heapMaxSize = Runtime.getRuntime().maxMemory() / 1000 / 1000;
//
//        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
//        long heapFreeSize = Runtime.getRuntime().freeMemory() / 1000 / 1000;
//
//        System.out.println(heapSize);
//        System.out.println(heapMaxSize);
//        System.out.println(heapFreeSize);
    }
}
