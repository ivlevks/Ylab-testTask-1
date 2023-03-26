package com.ivlevks.hw3.org_structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, Employee> employeeMap = new HashMap<>();

        saveEmployeeInMap(csvFile, employeeMap);
        createRelation(employeeMap);

        return employeeMap.get(1L);
    }

    private void createRelation(Map<Long, Employee> employeeMap) {
        for (Employee employee : employeeMap.values()) {
            if (employee.getBossId() != null) {
                Employee boss = employeeMap.get(employee.getBossId());
                employee.setBoss(boss);
                boss.getSubordinate().add(employee);
            }
        }
    }

    private void saveEmployeeInMap(File csvFile, Map<Long, Employee> employeeMap) {
        try (FileInputStream fileInputStream = new FileInputStream(csvFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNextLine()) {
                String stringFromFile = scanner.nextLine();
                String[] split = stringFromFile.split(";");

                if (split[0].equals("id")) continue;
                Long id = Long.valueOf(split[0]);
                Long bossId = split[1].equals("") ? null : Long.valueOf(split[1]);
                String name = split[2];
                String position = split[3];

                Employee employee = new Employee();
                employee.setId(id);
                employee.setBossId(bossId);
                employee.setName(name);
                employee.setPosition(position);
                employeeMap.put(id, employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
