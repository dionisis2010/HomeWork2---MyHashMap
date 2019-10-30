package ru.homework1.myhashmap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<>();
        System.out.println(map.put("key", "value"));
        System.out.println(map.put("key1", "value"));
        System.out.println(map.put("key2", "value"));
        System.out.println(map.delete("key1"));
        System.out.println(map.delete("dsgdf"));
        System.out.println(map.put("key1","newvalue"));
        System.out.println(map.put("key4","value"));
        map.print();

    }
}
