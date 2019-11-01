package ru.homework1.myhashmap;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * собственная реализация HashMap на основе хэштаблиц
 *
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> implements Map<K, V> {

    private static int DEFAULT_CAPACITY = 10;
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
     * @throws InvalidParameterException если loadFactor или capacity не валидные
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

    /**
     * вычисляет индекс в массиве на основе hashcode
     */
    private int generateID(K key) {
        validKey(key);
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * @return возвращает true если нет Entry по указанному индексу
     */
    private boolean isEmptyID(int id) {
        return entries[id] == null;
    }

    /**
     * @throws NullPointerException бросается если на входе "наловый" key
     */
    private void validKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void resize() {
        resize(capacity * 2);
    }

    /**
     * расширяет внутренний массив до указанного параметра (создает новый, перезаписывает все Entry по новым id)
     *
     * @param newCapacity определяет размер внутреннего массива (по умолчанию больше в 2 раза исходного)
     */
    private void resize(int newCapacity) {
        MyEntry<K, V>[] oldEntries = this.getAllEntries();
        this.capacity = newCapacity;
        entries = new MyEntry[newCapacity];
        this.size = 0;
        calculateThreshold();
        for (int i = 0; i < oldEntries.length; i++) {
            oldEntries[i].setNextCollision(null);
            put(oldEntries[i]);
        }
    }

    /**
     * служебный метод для работы динамического расширения в целях производительности, чтобы избежать создания лишних объектов
     */
    private void put(MyEntry<K, V> newEntry) {
        int id = generateID(newEntry.getKey());

        if (isEmptyID(id)) {
            entries[id] = newEntry;
            incrementSize();
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
        incrementSize();
    }

    /**
     * @return массив всех Entry (поле Entry.nextCollision обнуляется)
     */
    private MyEntry<K, V>[] getAllEntries() {
        MyEntry<K, V>[] allEntries = new MyEntry[size];
        int id = 0;
        for (int i = 0; i < entries.length; i++) {
            if (isEmptyID(i)) {
                continue;
            }
            allEntries[id] = entries[i];
            id++;
            while (allEntries[id - 1].hasNext()) {
                allEntries[id] = allEntries[id - 1].getNextCollision();
                id++;
            }
        }
        return allEntries;
    }


    /**
     * @return количество хранящихся Entry
     */
    @Override
    public int size() {
        return size;
    }

    private void incrementSize() {
        size++;
        checkCapacity();
    }

    private void checkCapacity() {
        if (size >= threshold) resize();
    }

    private void calculateThreshold() {
        this.threshold = (int) (this.loadFactor * this.capacity);
    }


    /**
     * @return возвращает true если мапа пустая
     */
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
        return values().contains(value);
    }


    /**
     * служебный метод для поиска по мапе
     *
     * @return возвращает Entry с соответсвующим ключем, либо null если такого ключа нет
     */
    private MyEntry search(K key) {
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

    /**
     * @return возвращает значение соответсвующее ключю
     */
    @Override
    public V get(Object key) {
        MyEntry<K, V> entry = search((K) key);
        return entry != null ? entry.getValue() : null;
    }

    /**
     * добавляет в мапу пару ключ-значение, если указанный ключ есть в мапе обновляет значение
     *
     * @return вовращает староее значение value если оно было, либо null, если такого key не было
     */
    @Override
    public V put(K key, V value) {
        int id = generateID(key);

        if (isEmptyID(id)) {
            entries[id] = new MyEntry<>(key, value);
            incrementSize();
            return null;
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
        incrementSize();
        return null;
    }

    private V update(MyEntry<K, V> entry, V value) {
        V oldValue = (V) entry.getValue();
        entry.setValue(value);
        return oldValue;
    }

    /**
     * добавляет все элементы мапы
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if ((m.size() + this.size) >= this.capacity) {
            resize((int) ((m.size() + this.size) / loadFactor) * 2);
        }
        for (Map.Entry<? extends K, ? extends V> pair : m.entrySet()) {
            this.put(pair.getKey(), pair.getValue());
        }
    }


    /**
     * удаляет Entry из мапы
     * @return возвращает старое значение, либо null, если такого ключа нет
     */
    @Override
    public V remove(Object key) {
        int id = generateID((K) key);
        if (isEmptyID(id)) return null;
        MyEntry tmpPrevEntry;
        MyEntry entry = entries[id];
        if (entry.getKey().equals(key)) {//если верхний в серьге
            entries[id] = entry.hasNext() ? entry.getNextCollision() : null;
            size--;
            return (V) entry.getValue();
        } else {//не первый в серьге
            while (true) {
                if (entry.hasNext()) {//есть дальнейший
                    tmpPrevEntry = entry;
                    entry = entry.getNextCollision();
                } else return null;
                if (entry.getKey().equals(key)) {
                    if (!entry.hasNext()) {//следующий - пусто
                        tmpPrevEntry.setNextCollision(null);
                    } else {//следующий не пусто
                        tmpPrevEntry.setNextCollision(entry.getNextCollision());
                    }
                    size--;
                    return (V) entry.getValue();
                }
            }
        }
    }

    /**
     * присваивает всем ячейкам массива null
     */
    @Override
    public void clear() {
        Arrays.fill(entries, null);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        MyEntry<K, V>[] arrayEntries = getAllEntries();
        for (int i = 0; i < arrayEntries.length; i++) {
            set.add(arrayEntries[i].getKey());
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        MyEntry<K, V>[] arrayEntries = getAllEntries();
        List<V> listValues = new ArrayList<>(arrayEntries.length);
        for (int i = 0; i < arrayEntries.length; i++) {
            listValues.add(arrayEntries[i].getValue());
        }

        return listValues;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        MyEntry<K, V>[] arrayEntries = getAllEntries();
        for (int i = 0; i < arrayEntries.length; i++) {
            set.add(arrayEntries[i]);
        }
        return set;
    }


    /**
     * выводит в консоль индекс корзины, хэшкод ключа, ключ и значение каждого Entry
     */
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
