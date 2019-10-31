package ru.homework1.myhashmap;

import javafx.util.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * собственная реализация HashMap на основе хэштаблиц
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K,V> implements Map<K,V> {


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
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

    @Override
    public void clear() {

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
