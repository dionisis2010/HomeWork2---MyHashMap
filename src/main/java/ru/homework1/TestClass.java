package ru.homework1;

import ru.homework1.myhashmap.MyHashMap;

public class TestClass {
    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<>();
        MyHashMap<String,String> map1 = new MyHashMap<>(50);
        MyHashMap<String,String> map2 = new MyHashMap<>(50, 2);

    }
}
