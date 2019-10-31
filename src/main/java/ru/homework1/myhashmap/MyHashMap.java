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

    private MyEntry[] entries;
    private int size = 0;
    private int capacity;
    private double loadFactor;
    private int threshold;

    public MyHashMap() {
        this(10);
    }

    public MyHashMap(int capacity) {
        this(capacity, 0.75);
    }

    /**
     * @param capacity   исходный размер внутреннего массива
     * @param loadFactor число от 0 до 1 определяющий при каком проценте заполненности внутреннего массива произойдет его расшиерение
     * @throws InvalidParameterException
     */
    public MyHashMap(int capacity, double loadFactor) {
        if (loadFactor < 0 || loadFactor > 1 || capacity < 0) {
            throw new InvalidParameterException();
        }
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        entries = new MyEntry[capacity];
        calculateThreshold();
    }

    public int generateID(K key) {
        if (!validKey(key)) {
            throw new NullPointerException();
        }
//        int h =key.hashCode();
//        return (h^(h>>>16)) & (capacity - 1);
        return (key.hashCode()) & capacity;
    }

    private boolean isEmptyID(int id) {
        return entries[id] == null;
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
        int id = generateID((K) key);
        return entries[id] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        int id = generateID((K) key);
//        if(isEmptyID(id)){
//            return null;
//        } else {
//            return (V) entries[id].getValue();
//        }
        return isEmptyID(id) ? null : (V) entries[id].getValue();
    }


    @Override
    public V put(K key, V value) {
        int id = generateID(key);
        int hash = key.hashCode();
        MyEntry newEntry = new MyEntry(key, value);
        MyEntry oldEntry = entries[id];
        if (isEmptyID(id)) {                                               // если айди путстой
            oldEntry = newEntry;
        } else if (newEntry.getHashCode() == oldEntry.getHashCode()) {      //если хэши одинаковые
            oldEntry.setValue(value);


        } else if (oldEntry.getNextCollision() == null) {                // если нет коллизий
            oldEntry.setNextCollision(newEntry);
        } else {
            oldEntry = oldEntry.getNextCollision();
        }


        V oldValue = entries[id] == null ? null : get(key);
        entries[id] = new MyEntry<>(key, value);
        size++;
        return oldValue;
    }

    private void putCollision(MyEntry parentEntry) {

    }


    private MyEntry getEntry(int id) {
        return entries[id];
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

    public void print() {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                System.out.println(i + ". " + entries[i]);
            }
        }
    }
}
