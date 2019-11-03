package ru;


import ru.homework2.myhashmap.MyEntry;
import ru.homework2.myhashmap.MyHashMap;

import java.awt.image.Kernel;
import java.util.*;

public class TestClass {


    public static void main(String[] args) {

        HashMap<Integer, Integer> hm = new HashMap<>();
        hm.put(1, 101);
        Set<Map.Entry<Integer, Integer>> es = hm.entrySet();
        for(Map.Entry<Integer, Integer> entry : es) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        hm.put(2, 102);
        System.out.println();
        for(Map.Entry<Integer, Integer> entry : es) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
