package ru.homework1.myhashmap;

import java.security.InvalidParameterException;
import java.util.Arrays;
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

    private static int DEFAULT_CAPACITY = 3;
    private static double DEFAULT_LOADFACTOR = 0.75;

    private MyEntry<K, V>[] entries;
    private int size = 0;
    private int capacity;
    private double loadFactor;
    private int threshold;


    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int capacity) {
        this(capacity, DEFAULT_LOADFACTOR);
    }

    /**
     * @param capacity   исходный размер внутреннего массива
     * @param loadFactor число от 0 до 1 определяющий при каком проценте заполненности внутреннего массива произойдет его расшиерение
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

    private int generateID(K key) {
        validKey(key);
        return key.hashCode() % capacity;
    }

    private boolean isEmptyID(int id) {
        return entries[id] == null;
    }

    private void calculateThreshold() {
        this.threshold = (int) (this.loadFactor * this.capacity);
    }

    private void validKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void checkCapasity() {
        if (size >= threshold) resize();
    }

    private void resize() {
        resize(capacity * 2);
    }

    public void resize(int newCapacity) {
        MyEntry<K,V>[] oldEntries = this.getAllEntries();
        this.capacity = newCapacity;
        entries = new MyEntry[newCapacity];
        this.size = 0;
        for (int i =0; i < oldEntries.length;i++){
            oldEntries[i].setNextCollision(null);
            put(oldEntries[i]);
        }
    }

    public MyEntry<K, V>[] getAllEntries() {
        MyEntry<K, V>[] allEntries = new MyEntry[size];
        int id = 0;
        for (int i = 0; i < entries.length; i++) {
            if (isEmptyID(i)) {
                continue;
            }
            allEntries[id] = entries[i];
            id++;
            while (allEntries[id-1].hasNext()) {
                allEntries[id] = allEntries[id-1].getNextCollision();
                id++;
            }
        }
        return allEntries;
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
        return search((K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    private MyEntry search(K key) {
        validKey(key);
        MyEntry entry = entries[generateID(key)];
        if (entry == null) return null;

        while (true) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
            if (entry.hasNext()) {
                entry = entry.getNextCollision();
            } else return null;
        }
    }

    @Override
    public V get(Object key) {
        return (V) search((K) key).getValue();
    }

    @Override
    public V put(K key, V value) {
        int id = generateID(key);

        if (isEmptyID(id)) {
            return easyPut(key, value, id);
        }
        MyEntry entry = entries[id];
        while (true) {
            if (entry.getKey().equals(key)) {
                return (V) update(entry, value);
            }
            if (entry.hasNext()) {
                entry = entry.getNextCollision();
            } else break;
        }
        entry.setNextCollision(new MyEntry<K, V>(key, value));
        size++;
        return null;
    }
    public void put(MyEntry<K,V> newEntry) {
        int id = generateID(newEntry.getKey());

        if (isEmptyID(id)) {
            entries[id] = newEntry;
            size++;
            return;
        }
        MyEntry entry = entries[id];
        while (true) {
            if (entry.getKey().equals(newEntry.getKey())) {
                entry.setValue(newEntry.getValue());
                size++;
                return;
            }
            if (entry.hasNext()) {
                entry = entry.getNextCollision();
            } else break;
        }
        entry.setNextCollision(newEntry);
        size++;
    }

    private V easyPut(K key, V value, int id) {
        entries[id] = new MyEntry<>(key, value);
        size++;
        return null;
    }

    private V update(MyEntry<K, V> entry, V value) {
        V oldValue = (V) entry.getValue();
        entry.setValue(value);
        return oldValue;
    }

    private MyEntry getEntry(int id) {
        return entries[id];
    }

    @Override
    public V remove(Object key) {
        int id = generateID((K) key);
        if (entries[id] == null) return null;
        if (entries[id].getKey().equals(key)) {
            V oldValue = (V) entries[id].getValue();
            entries[id] = entries[id].hasNext() ? entries[id].getNextCollision() : null;
            size--;
            return oldValue;
        }
        if (!entries[id].hasNext()) return null;
        MyEntry lastEntry = entries[id];
        MyEntry checkedEntry = entries[id].getNextCollision();
        while (true) {
            if (checkedEntry.getKey().equals(key)) {
                V oldValue = (V) checkedEntry.getValue();
                lastEntry.setNextCollision(checkedEntry.getNextCollision());
                size--;
                return oldValue;
            } else if (checkedEntry.hasNext()) {
                lastEntry = checkedEntry;
                checkedEntry = checkedEntry.getNextCollision();
            } else return null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m.size() > this.capacity) {
            resize((int) ((m.size()+this.size)/loadFactor));
        }
        for (Map.Entry<? extends K, ? extends V> pair : m.entrySet()) {
            this.put(pair.getKey(), pair.getValue());
        }
    }

    /**
     * присваивает всем ячейкам массива null
     */
    @Override
    public void clear() {
        Arrays.fill(entries, null);
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
                MyEntry entry = entries[i];

                while (true) {
                    System.out.println(i + " " + entry);
                    if (entry.hasNext()) entry = entry.getNextCollision();
                    else break;
                }
            }
        }
    }
}
