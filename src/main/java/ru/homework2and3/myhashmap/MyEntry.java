package ru.homework2and3.myhashmap;

import java.util.Map;
import java.util.Objects;

/**
 * хранит пару ключ/значение, может образовывать односвязный список
 * @param <K> ключ
 * @param <V> значение
 */
public class MyEntry<K, V> implements Map.Entry<K, V> {

    private MyEntry<K,V> nextEntry;
    private final K key;
    private final int keyHashCode;
    private V value;

    MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.keyHashCode = key == null ? 0 : key.hashCode();
    }

    boolean hasNext() {
        return nextEntry != null;
    }

    MyEntry<K,V> getNext() {
        return this.nextEntry;
    }

    void setNext(MyEntry<K,V> nextCollision) {
        this.nextEntry = nextCollision;
    }

    /**
     * @return возвращает строку содержащую через пробел хэшкод ключа, ключ и значение
     */
    @Override
    public String toString() {
        return this.keyHashCode + " " + this.key + " " + this.value;
    }

    /**
     * @return true если объекты идентичны, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyEntry<?, ?> myEntry = (MyEntry<?, ?>) o;
        return key.equals(myEntry.key) &&
                Objects.equals(value, myEntry.value);
    }

    /**
     * @return возвращает ключ
     */
    @Override
    public K getKey() {
        return key;
    }

    boolean equalsKey(MyEntry<K, V> entry) {
        if (this.getKeyHashCode() != entry.getKeyHashCode()) {
            return false;
        } else {
            return this.getKey().equals(entry.getKey());
        }
    }

    /**
     * @return хэшкод пары ключ/занчение
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    int getKeyHashCode() {
        return keyHashCode;
    }

    /**
     * @return возвращает значение
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * @return устанавливает новое значение
     */
    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }


}
