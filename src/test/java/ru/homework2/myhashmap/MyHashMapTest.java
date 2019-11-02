package ru.homework2.myhashmap;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MyHashMapTest {

    static Map<String, String> map = new HashMap<>();
    static Map<String, String> mymap;
    static int DEFAULT_INIT = 500;

    static String key = "key";
    static String value = "value";

    private static void init() {
        init(DEFAULT_INIT);
    }

    private static void init(int size) {
        map = new HashMap<>();
        mymap = new MyHashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(key + i, value + i);
            mymap.put(key + i, value + i);
        }
    }

    @Test
    public void put() {
        init();
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.get(key + i), map.get(key + i));
        }
    }

    @Test
    public void size() {
        init();
        assertEquals(mymap.size(), map.size());
        map.put(key, value);
        mymap.put(key, value);
        assertEquals(mymap.size(), map.size());

        map.put(key, value);
        mymap.put(key, value);
        assertEquals(mymap.size(), map.size());

        map.remove("key2");
        mymap.remove("key2");
        assertEquals(mymap.size(), map.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(mymap.isEmpty(), map.isEmpty());
        map.put(key, value);
        mymap.put(key, value);
        assertEquals(mymap.isEmpty(), map.isEmpty());
        map.remove(key);
        mymap.remove(key);
        assertEquals(mymap.isEmpty(), map.isEmpty());
        init(1);
        assertEquals(mymap.isEmpty(), map.isEmpty());
    }

    @Test
    public void containsKey() {
        init();
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.containsKey(key + i), map.containsKey(key + i));
        }
    }

    @Test
    public void containsValue() {
        init();
        for (int i = 0; i < mymap.size(); i++) {
            assertEquals(mymap.containsValue(value + i), map.containsValue(value + i));
        }
    }

    @Test
    public void get() {
        init();
        assertEquals(mymap.get("key1"), map.get("key1"));
        assertEquals(mymap.get("key"), map.get("key"));
        assertEquals(mymap.get("key50"), map.get("key50"));
    }

    @Test
    public void remove() {
        init();
        for (int i = 0; i < map.size(); i++) {
            assertEquals(mymap.remove(key + i), map.remove(key + i));
            assertEquals(mymap.size(), map.size());
        }
        assertEquals(mymap.isEmpty(), map.isEmpty());
    }

    @Test
    public void putAll() {
        init();
        Map<String, String> newMap = new HashMap<String, String>();
        String newKey = "newKey";
        String newValue = "newValue";
        for (int i = 0; i < DEFAULT_INIT; i++) {
            newMap.put(newKey + i, newValue + i);
        }
        map.putAll(newMap);
        mymap.putAll(newMap);
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.get(key + i), map.get(key + i));
            assertEquals(mymap.get(newKey + i), map.get(newKey + i));
        }
    }

    @Test
    public void clear() {
        init();
        map.clear();
        mymap.clear();
        assertEquals(mymap.get("key1"), map.get("key1"));
        assertEquals(mymap.isEmpty(), map.isEmpty());
        assertEquals(mymap.size(), map.size());
    }

    @Test
    public void keySet() {
        init();
        Set set1 = map.keySet();
        Set set2 = mymap.keySet();

        for (int i = 0; i < set1.size(); i++) {
            assertEquals(set2.contains(key + i), set1.contains(key + i));
        }
        assertEquals(set2.size(), set1.size());
    }

    @Test
    public void values() {
        init();
        Collection<String> col1 =  map.values();
        Collection<String> col2 =  mymap.values();

        for (int i = 0; i < col1.size(); i++) {
            assertEquals(col2.contains(value+i), col1.contains(value+i));
        }
        assertEquals(col2.size(), col1.size());
    }

    @Test
    public void entrySet() {
        init();
        Set set1 = map.entrySet();
        Set set2 = mymap.entrySet();

        for (int i = 0; i < set1.size(); i++) {
            assertEquals(set2.contains(key + i), set1.contains(key + i));
        }
        assertEquals(set2.size(), set1.size());
    }
}