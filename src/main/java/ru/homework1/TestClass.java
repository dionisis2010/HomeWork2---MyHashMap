package ru.homework1;

import ru.homework1.myhashmap.MyHashMap;

import java.util.HashMap;

public class TestClass {
    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<>();
//        map.put("key1", "value1");
//        map.put("key2", "value2");
//        map.put("key3", "value3");
//        map.put("key4", "value3");
//        map.put("key5", "value3");
//        map.put("key6", "value3");
        System.out.println(map.generateID("abracadabra"));
        System.out.println(map.generateID("key1"));
        System.out.println(map.generateID("key2"));
        System.out.println(map.generateID("key2"));
        System.out.println(map.generateID("key5"));
        System.out.println(map.generateID("keyrdsak.gjdsfkgjsd5"));
        System.out.println(map.generateID("123"));
        System.out.println(map.generateID("1500"));
        map.print();

    }
}
