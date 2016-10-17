package com.example.zer.myapplication;

import java.util.HashMap;

public class ReceiversStates {

    private static ReceiversStates instance = new ReceiversStates();

    public static ReceiversStates getInstance() {
        return instance;
    }

    private ReceiversStates() {
    }

    private HashMap<String, Boolean> map = new HashMap<>();

    public HashMap<String, Boolean> getMap() {
        return map;
    }

    public void addValue(String key, Boolean value) {
        map.put(key, value);
    }
}
