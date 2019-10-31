package ru.homework1.myhashmap;

import org.junit.Test;
import ru.homework1.myhashmapalpha.MyHashMapAlpha;


public class MyHashMapAlphaTest {

    MyHashMapAlpha<String, Integer> map = new MyHashMapAlpha<>(10);

    {
        map.put("key1", 1);
        map.put("new key", 100);
        map.put("Shalom", 625);
        map.put("Hello, World", 72);
        map.put("AbraCadabra", 1994);
    }

    @Test
    public void put() {


    }


}