package ru.homework2and3.myhashmap;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MyHashMapTest {

    private Map<String, String> map;
    private Map<String, String> mymap;

    private static final int DEFAULT_INIT = 500;
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Before
    public void init() {
        map = new HashMap<>();
        mymap = new MyHashMap<>();
        for (int i = 0; i < DEFAULT_INIT; i++) {
            map.put(KEY + i, VALUE + i);
            mymap.put(KEY + i, VALUE + i);
        }
        map.put(null, "nullValue1");
        mymap.put(null, "nullValue1");
        map.put(null, "nullValue2");
        mymap.put(null, "nullValue2");
    }

    @Test
    public void put() {
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.get(KEY + i), map.get(KEY + i));
        }
    }

    @Test
    public void size() {
        assertEquals(mymap.size(), map.size());
        map.put(KEY, VALUE);
        mymap.put(KEY, VALUE);
        assertEquals(mymap.size(), map.size());

        map.put(KEY, VALUE);
        mymap.put(KEY, VALUE);
        assertEquals(mymap.size(), map.size());

        map.remove("key2");
        mymap.remove("key2");
        assertEquals(mymap.size(), map.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(mymap.isEmpty(), map.isEmpty());
        map.put(KEY, VALUE);
        mymap.put(KEY, VALUE);
        assertEquals(mymap.isEmpty(), map.isEmpty());
        map.remove(KEY);
        mymap.remove(KEY);
        assertEquals(mymap.isEmpty(), map.isEmpty());
        map.put(KEY, VALUE);
        mymap.put(KEY, VALUE);
        assertEquals(mymap.isEmpty(), map.isEmpty());
    }

    @Test
    public void containsKey() {
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.containsKey(KEY + i), map.containsKey(KEY + i));
        }
    }

    @Test
    public void containsValue() {
        for (int i = 0; i < mymap.size(); i++) {
            assertEquals(mymap.containsValue(VALUE + i), map.containsValue(VALUE + i));
        }
    }

    @Test
    public void get() {
        assertEquals(mymap.get("key1"), map.get("key1"));
        assertEquals(mymap.get("key"), map.get("key"));
        assertEquals(mymap.get("key50"), map.get("key50"));
        assertEquals(mymap.get(null), map.get(null));
    }

    @Test
    public void remove() {
        for (int i = 0; i < map.size(); i++) {
            assertEquals(mymap.remove(KEY + i), map.remove(KEY + i));
            assertEquals(mymap.size(), map.size());
        }
        assertEquals(mymap.isEmpty(), map.isEmpty());
    }

    @Test
    public void putAll() {
        Map<String, String> newMap = new HashMap<>();
        String newKey = "newKey";
        String newValue = "newValue";
        for (int i = 0; i < DEFAULT_INIT; i++) {
            newMap.put(newKey + i, newValue + i);
        }
        map.putAll(newMap);
        mymap.putAll(newMap);
        for (int i = 0; i < DEFAULT_INIT; i++) {
            assertEquals(mymap.get(KEY + i), map.get(KEY + i));
            assertEquals(mymap.get(newKey + i), map.get(newKey + i));
        }
    }

    @Test
    public void clear() {
        map.clear();
        mymap.clear();
        assertEquals(mymap.get("key1"), map.get("key1"));
        assertEquals(mymap.get(null), map.get(null));
        assertEquals(mymap.isEmpty(), map.isEmpty());
        assertEquals(mymap.size(), map.size());
    }

    @Test
    public void keySet() {
        Set set1 = map.keySet();
        Set set2 = mymap.keySet();

        for (int i = 0; i < set1.size(); i++) {
            assertEquals(set2.contains(KEY + i), set1.contains(KEY + i));
        }
        assertEquals(set2.size(), set1.size());
    }

    @Test
    public void values() {
        Collection<String> col1 = map.values();
        Collection<String> col2 = mymap.values();

        for (int i = 0; i < col1.size(); i++) {
            assertEquals(col2.contains(VALUE + i), col1.contains(VALUE + i));
        }
//        assertEquals(col2, col1);
        assertEquals(col2.size(), col1.size());
    }

    @Test
    public void entrySet() {
        Set set1 = map.entrySet();
        Set set2 = mymap.entrySet();

        for (int i = 0; i < set1.size(); i++) {
            assertEquals(set2.contains(KEY + i), set1.contains(KEY + i));
        }
        assertEquals(set2.size(), set1.size());
    }
}