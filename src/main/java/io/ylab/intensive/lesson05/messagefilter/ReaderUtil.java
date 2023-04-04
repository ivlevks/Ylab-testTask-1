package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ReaderUtil {

    public static List<String> getDataFromFile(String file) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(file));
        while (scanner.hasNext()) {
            result.add(scanner.nextLine());
        }
        return result;
    }
}
