package ru.homework1.myhashmap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>(10);
        map.put("key", 6);
        int i =map.getValue("key");
        System.out.println(i);

    }
}
