package ru.homework1;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import ru.homework1.myhashmap.MyHashMap;
import sun.nio.cs.Surrogate;

import java.util.*;

public class TestClass {
    static Map<String, String> map = new HashMap<>();
    static Map<String, String> mymap = new MyHashMap<>();
    static int DEFAULT_INIT = 100;

    static String key = "key";
    static String value = "value";

    static void init() {
        init(DEFAULT_INIT);
    }

    static void init(int size) {
        for (int i = 0; i < size; i++) {
            map.put(key + i, value + i);
            mymap.put(key + i, value + i);
        }
    }



    public static void main(String[] args) {

        init();
        MyHashMap myHashMap = (MyHashMap) mymap;
        myHashMap.print();
    }




}
