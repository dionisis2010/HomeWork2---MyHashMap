package ru.homework1;

import ru.homework1.myhashmap.MyHashMap;

public class TestClass {
    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println(map.put("key1", "newValue"));
        map.print();

    }
}
