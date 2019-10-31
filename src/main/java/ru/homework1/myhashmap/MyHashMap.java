package ru.homework1.myhashmap;

import javafx.util.Pair;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * собственная реализация HashMap на основе хэштаблиц
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements Map<K, V> {

    private Entry<K, V>[] entries;
    private int size = 0;
    private int capacity;
    private double loadFactor;
    private int threshold;

    public MyHashMap() {
        this(20);
    }

    public MyHashMap(int capacity) {
        this(capacity, 0.75);
    }

    /**
     * @exception InvalidParameterException
     * @param capacity исходный размер внутреннего массива
     * @param loadFactor число от 0 до 1 определяющий при каком проценте заполненности внутреннего массива произойдет его расшиерение
     */
    public MyHashMap(int capacity, double loadFactor) {
        if (loadFactor < 0 || loadFactor > 1 || capacity < 0) {
            throw new InvalidParameterException();
        } else {
            this.loadFactor = loadFactor;
            this.capacity = capacity;
        }
        calculateThreshold();
    }

    private int generateID(K key) {
        return key.hashCode() & capacity;
    }

    private void calculateThreshold() {
        this.threshold = (int) (this.loadFactor * this.capacity);
    }

    private boolean validKey(K key) {
        return key != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        int id = generateID(key);
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
//        if(m.size() > count){
//
//        }
//        for (Map.Entry<? extends K, ? extends V> pair : m.entrySet()){
//            map.put(pair.getKey(), pair.getValue());
//        }
    }

    /**
     * присваивает всем ячейкам массива null
     */
    @Override
    public void clear() {
        for (int i = 0; i < entries.length; i++) {
            entries[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
