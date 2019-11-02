package ru.myhashmapalpha;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashMapAlphaTest {
    private MyHashMapAlpha<String,String> map = new MyHashMapAlpha<>(5);
    {
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
    }

    @Test
    public void put() {
        assertTrue(map.put("key4", "value4"));
        assertTrue(map.put("key4", "newValue4"));
        assertTrue(map.put("key5", "value5"));
        assertFalse(map.put("key6", "value6"));
        assertEquals(map.getElement("key1"), new Element<>("key1", "value1"));
    }

    @Test
    public void getElement() {
        assertEquals(map.getElement("key1"), new Element<>("key1","value1"));
        assertEquals(map.getElement("key2"), new Element<>("key2","value2"));
        assertEquals(map.getElement("key3"), new Element<>("key3","value3"));
    }

    @Test
    public void getValue() {
        assertEquals(map.getValue("key1"), "value1");
        assertEquals(map.getValue("key2"), "value2");
        assertEquals(map.getValue("key3"), "value3");
    }

    @Test
    public void delete() {
        assertTrue(map.delete("key2"));
        assertFalse(map.delete("key2"));
    }


    @Test
    public void size() {
        assertEquals(map.size(),3);
        map.put("key4","value4");
        assertEquals(map.size(),4);
        map.delete("key2");
        assertEquals(map.size(),3);
        map.put("key3", "newValue");
        assertEquals(map.size(),3);
    }

}