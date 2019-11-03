package ru.homework2.myhashmap;


import java.security.InvalidParameterException;
import java.util.*;


/**
 * собственная реализация HashMap на основе хэштаблиц.
 * Обработка коллизий происходит методом цепочек (в конфликтующих корзинах формируется односвязный список)
 * информация хранится в виде пар ключ/значение
 * @param <K> key -уникальный объект, по которому происходит доступ к значению
 * @param <V> value - значение привязанное к ключю
 */
public class MyHashMap<K, V> implements Map<K, V> {

    private final static int DEFAULT_CAPACITY = 10;
    private final static double DEFAULT_LOAD_FACTOR = 0.75;

    private MyEntry<K, V>[] bundles;
    private int size = 0;
    private int capacity;
    private final double loadFactor;
    private int threshold;

    /**
     * служебное поле для сравнения по хэшкоду, чтобы не пресчитывать при нескольких итерация подряд с одним ключем
     */
    private int lastUsedKeyHashCode;


    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * @param capacity   исходный размер внутреннего массива
     * @param loadFactor число от 0 до 1 определяющий при каком проценте заполненности внутреннего массива произойдет его расшиерение
     * @throws InvalidParameterException если loadFactor или capacity не валидные
     */
    public MyHashMap(int capacity, double loadFactor) {
        validParameters(capacity, loadFactor);
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        this.bundles = new MyEntry[capacity];
        calculateThreshold();
    }

    private void validParameters(int capacity, double loadFactor) {
        if ((loadFactor < 0 || loadFactor > 1) || capacity < 0) {
            throw new InvalidParameterException("не валидные параметры");
        }
    }


    /**
     * вычисляет индекс в массиве на основе hashcode
     */
    private int generateID(K key) {
        if (key == null) {
            this.lastUsedKeyHashCode = 0;
            return 0;
        }
        this.lastUsedKeyHashCode = key.hashCode();
        return Math.abs(this.lastUsedKeyHashCode) % this.capacity;
    }

    private MyEntry<K, V> getHeaderEntryInBundle(int id) {
        return bundles[id];
    }

    /**
     * @return возвращает true если нет Entry по указанному индексу
     */
    private boolean isEmptyBundle(int id) {
        return this.bundles[id] == null;
    }

    /**
     * служебный метод сравнения ключей по хэшкоду, для повышения производительности
     */
    private boolean fastCompareKeys(MyEntry<K, V> entry, K key) {
        if (entry.getKeyHashCode() != this.lastUsedKeyHashCode) {
            return false;
        } else if (key == null && entry.getKey() == null) {
            return true;
        } else {
            return entry.getKey().equals(key);
        }
    }

    private void resize() {
        resize(this.capacity * 2);
    }

    /**
     * расширяет внутренний массив до указанного параметра (создает новый, перезаписывает все Entry по новым id)
     *
     * @param newCapacity определяет размер внутреннего массива (по умолчанию больше в 2 раза исходного)
     */
    private void resize(int newCapacity) {
        final MyEntry<K, V>[] oldEntries = this.getAllEntries();
        this.capacity = newCapacity;
        this.bundles = new MyEntry[newCapacity];
        this.size = 0;
        calculateThreshold();

        for (MyEntry<K, V> oldEntry : oldEntries) {
            oldEntry.setNext(null);
            put(oldEntry);
        }
    }

    /**
     * @return массив всех Entry (поле Entry.nextCollision обнуляется)
     */
    private MyEntry<K, V>[] getAllEntries() {
        final MyEntry<K, V>[] allEntries = new MyEntry[this.size];
        int allEntriesID = 0;
        for (int bundleID = 0; bundleID < bundles.length; bundleID++) {
            if (isEmptyBundle(bundleID)) {
                continue;
            } else {
                MyEntry<K, V> entry = getHeaderEntryInBundle(bundleID);
                allEntries[allEntriesID] = entry;
                allEntriesID++;
                while (entry.hasNext()) {
                    entry = entry.getNext();
                    allEntries[allEntriesID] = entry;
                    allEntriesID++;
                }
            }
        }
        return allEntries;
    }

    /**
     * служебный метод для работы динамического расширения в целях производительности, чтобы избежать создания лишних объектов
     */
    private void put(MyEntry<K, V> newEntry) {
        int id = generateID(newEntry.getKey());

        if (isEmptyBundle(id)) {
            bundles[id] = newEntry;
            incrementSize();
            return;
        }
        MyEntry entry = bundles[id];
        while (true) {
            if (entry.equalsKey(newEntry)) {
                entry.setValue(newEntry.getValue());
                size++;
                return;
            }
            if (entry.hasNext()) {
                entry = entry.getNext();
            } else break;
        }
        entry.setNext(newEntry);
        incrementSize();
    }


    /**
     * @return количество хранящихся Entry
     */
    @Override
    public int size() {
        return this.size;
    }

    private void incrementSize() {
        this.size++;
        checkCapacity();
    }

    private void checkCapacity() {
        if (this.size > this.threshold) resize();
    }

    private void calculateThreshold() {
        this.threshold = (int) (this.loadFactor * this.capacity);
    }


    /**
     * @return возвращает true если мапа пустая
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return возвращает true если полученный ключ есть в мапе, иначе false
     */
    @Override
    public boolean containsKey(Object key) {
        return search((K) key) != null;
    }

    /**
     * @return возвращает true если полученное значение есть в мапе, иначе false
     */
    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    /**
     * служебный метод для поиска по мапе
     *
     * @return возвращает Entry с соответсвующим ключем, либо null если такого ключа нет
     */
    private MyEntry<K, V> search(K key) {
        final int bundleID = generateID(key);
        return searchInBundle(key, bundleID);
    }

    private MyEntry<K, V> searchInBundle(K key, int bundleID) {
        MyEntry<K, V> entry = getHeaderEntryInBundle(bundleID);
        if (entry == null) {
            return null;
        }
        for (int i = 0; i < countEntriesInBundle(bundleID); i++) {
            if (fastCompareKeys(entry, key)) {
                return entry;
            } else {
                entry = entry.getNext();
            }
        }
        return null;
    }

    private int countEntriesInBundle(int id) {
        MyEntry<K, V> entry = getHeaderEntryInBundle(id);
        int countEntries = 1;
        while (entry.hasNext()) {
            entry = entry.getNext();
            countEntries++;
        }
        return countEntries;
    }

    private MyEntry<K, V> getNextCollision(MyEntry<K, V> checkedEntry) {
        if (checkedEntry.hasNext()) {
            return checkedEntry.getNext();
        } else {
            return null;
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
        MyEntry<K, V> entry = search(key);
        if (entry == null) {
            final int bundleID = generateID(key);
            entry = getHeaderEntryInBundle(bundleID);
            bundles[bundleID] = new MyEntry<K, V>(key, value);
            bundles[bundleID].setNext(entry);
            incrementSize();
            return null;
        } else {
            return entry.setValue(value);
        }
    }


    /**
     * добавляет все элементы мапы
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> puttedMap) {
        if ((puttedMap.size() + this.size) >= this.threshold) {
            resize(calculateNewCapacity(puttedMap));
        }
        for (Map.Entry<? extends K, ? extends V> pair : puttedMap.entrySet()) {
            this.put(pair.getKey(), pair.getValue());
        }
    }

    private int calculateNewCapacity(Map<? extends K, ? extends V> puttedMap) {
        return (int) ((puttedMap.size() + this.size) / loadFactor) * 2;
    }


    /**
     * удаляет Entry из мапы
     * @return возвращает старое значение, либо null, если такого ключа нет
     */
    @Override
    public V remove(Object key) {
        final int bundleID = generateID((K) key);
        final MyEntry<K, V> removedEntry = search((K) key);
        if (removedEntry == null) {
            return null;
        } else if (removedEntry == getHeaderEntryInBundle(bundleID)) {
            return removeEntry(removedEntry, null);
        } else {
            MyEntry<K, V> prevEntry = getHeaderEntryInBundle(bundleID);
            while (prevEntry.getNext() != removedEntry) {
                prevEntry = prevEntry.getNext();
            }
            return removeEntry(removedEntry, prevEntry);
        }
    }

    private V removeEntry(MyEntry<K, V> entry, MyEntry<K, V> prevEntry) {
        V oldValue = entry.getValue();
        if (prevEntry != null) {
            prevEntry.setNext(getNextCollision(entry));
        }
        size--;
        return oldValue;
    }

    /**
     * присваивает всем ячейкам массива null
     */
    @Override
    public void clear() {
        Arrays.fill(this.bundles, null);
        this.size = 0;
    }

    /**
     * @return возвращает множество всех ключей из мапы
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        MyEntry<K, V>[] arrayEntries = getAllEntries();
        for (MyEntry<K, V> arrayEntry : arrayEntries) {
            set.add(arrayEntry.getKey());
        }
        return set;
    }

    /**
     * @return возвращает множество всех значений из мапы
     */
    @Override
    public Collection<V> values() {
        MyEntry<K, V>[] arrayEntries = getAllEntries();
        List<V> listValues = new ArrayList<>(arrayEntries.length);
        for (MyEntry<K, V> arrayEntry : arrayEntries) {
            listValues.add(arrayEntry.getValue());
        }
        return listValues;
    }

    /**
     * @return возвращается множество копий всех пар ключ/значение
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (MyEntry<K, V> entry : getAllEntries()) {
            set.add(new MyEntry<K, V>(entry.getKey(), entry.getValue()));
        }
        return set;
    }


    /**
     * bundleID, keyHashCode, key и value каждого Entry с новой строки
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        MyEntry<K,V> entry;
        for (int i = 0; i < this.bundles.length; i++) {
            if (this.bundles[i] != null) {
                entry = this.bundles[i];
                while (true) {
                    res.append(i).append(" ").append(entry).append("\n");
                    if (entry.hasNext()) entry = entry.getNext();
                    else break;
                }
            }
        }
        return res.toString();
    }
}
