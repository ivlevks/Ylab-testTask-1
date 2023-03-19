package com.ivlevks.hw3.org_structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        /*
          Storage Employee in format: Key - id, Value - Employee
         */
        Map<Long, Employee> employeeMap = new HashMap<>();

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

                //get or create employee
                Employee employee;
                if (employeeMap.containsKey(id)) {
                    employee = employeeMap.get(id);
                } else employee = new Employee();

                //set parameters employee
                employee.setId(id);
                employee.setBossId(bossId);
                employee.setName(name);
                employee.setPosition(position);
                employeeMap.put(id, employee);

                //get or create Boss of employee,
                // add in list subordinate current employee
                if (employeeMap.containsKey(bossId)) {
                    employee.setBoss(employeeMap.get(bossId));
                    employeeMap.get(bossId).getSubordinate().add(employee);
                } else if (bossId != null){
                    Employee employeeBoss = new Employee();
                    employeeBoss.getSubordinate().add(employee);
                    employeeMap.put(bossId, employeeBoss);
                }
            }
        }
        return employeeMap.get(1L);
    }
}
