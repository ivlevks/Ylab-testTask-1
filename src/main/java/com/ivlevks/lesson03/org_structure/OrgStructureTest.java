package com.ivlevks.lesson03.org_structure;

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

    private static void printStructure(Employee employee, String offset) {
        System.out.printf("%sId - %d; bossId - %d; name - %s; position - %s%n",
                offset, employee.getId(), employee.getBossId(), employee.getName(), employee.getPosition());
        if (!employee.getSubordinate().isEmpty()) {
            offset += "      ";
            for (Employee currentEmployee : employee.getSubordinate()) {
                printStructure(currentEmployee, offset);
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
