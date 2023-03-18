package com.ivlevks.hw3.dated_map;

public class DatedMapTest {

    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("1", "test1");
        datedMap.put("2", "test2");
        datedMap.put("3", "test3");

        System.out.println(datedMap.keySet());
        System.out.println(datedMap.containsKey("2"));
        System.out.println(datedMap.get("3"));
        System.out.println(datedMap.getKeyLastInsertionDate("3"));
        System.out.println();

        datedMap.remove("3");
        System.out.println(datedMap.get("3"));
        System.out.println(datedMap.getKeyLastInsertionDate("3"));
        System.out.println();

        System.out.println(datedMap.getKeyLastInsertionDate("1"));
        System.out.println(datedMap.getKeyLastInsertionDate("2"));
    }
}
