package ru.homework1.myhashmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.homework1.myhashmap.MyHashMap.*;


public class MyHashMapTest {

    MyHashMap<String, Integer> map = new MyHashMap<>(10);

    {
        map.put("key1", 1);
        map.put("new key", 100);
        map.put("Shalom", 625);
        map.put("Hello, World", 72);
        map.put("AbraCadabra", 1994);
    }

    @Test
    public void put() {
        assertEquals(map.size(), 5);
        assertEquals(map.put("key1", 5), true);
        assertEquals(map.size(), 5);
        assertEquals(map.put("key6", 5), true);
        assertEquals(map.put("key7", 1000), true);
        assertEquals(map.put("key8", 42), true);
        assertEquals(map.put("key8", 625), true);
        assertEquals(map.put("key9", 9), true);
        assertEquals(map.put("key10", 10), true);
        assertEquals(map.put("key11", 11), false);
        assertEquals(map.size(), 10);
        assertEquals(map.haveKey("key6"), true);
        assertEquals(map.delete("key6"), true);
        assertEquals(map.haveKey("key6"), false);
        assertEquals(map.put("123", 123), true);
        assertEquals(map.put("new", 999), false);
        map.clear();
        assertEquals(map.size(),0);
        assertEquals(map.put("key", 10), true);
    }


}