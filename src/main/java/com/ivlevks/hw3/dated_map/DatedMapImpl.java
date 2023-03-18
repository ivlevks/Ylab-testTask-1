package com.ivlevks.hw3.dated_map;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap{
    private final Map <String, String> mainMap = new HashMap<>();
    private final Map <String, Date> dateMap = new HashMap<>();

    @Override
    public void put(String key, String value) {
        mainMap.put(key, value);
        dateMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return mainMap.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return mainMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        mainMap.remove(key);
        dateMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return mainMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        if (mainMap.containsKey(key)) {
            return dateMap.get(key);
        }
        return null;
    }
}
