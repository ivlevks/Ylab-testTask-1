package com.ivlevks.hw3.org_structure;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class OrgStructureTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        OrgStructureParserImpl parser = new OrgStructureParserImpl();
        Employee boss = parser.parseStructure(getFileFromResources());

        String offset = "";
        printStructure(boss, offset);
    }

    private static void printStructure(Employee boss, String offset) {
        System.out.printf("%sId - %d; bossId - %d; name - %s; position - %s%n",
                offset, boss.getId(), boss.getBossId(), boss.getName(), boss.getPosition());
        if (!boss.getSubordinate().isEmpty()) {
            offset += "      ";
            for (Employee employee : boss.getSubordinate()) {
                printStructure(employee, offset);
            }
        }
    }

    public static File getFileFromResources() throws URISyntaxException {
//        URL url = OrgStructureTest.class.getClassLoader().getResource("file.csv");
//        URL url = OrgStructureTest.class.getClassLoader().getResource("file2.csv");
        URL url = OrgStructureTest.class.getClassLoader().getResource("file3.csv");
        return new File(url.toURI().getPath());
    }
}
