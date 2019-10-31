package ru.homework1;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import ru.homework1.myhashmap.MyHashMap;
import sun.nio.cs.Surrogate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TestClass {
    public static void main(String[] args) {

        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.remove("key");
//        map.put("key4", "newvalue4");
        map.print();
    }



}
