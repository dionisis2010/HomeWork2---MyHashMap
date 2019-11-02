package ru.homework2.myhashmap;

import java.util.Map;
import java.util.Objects;

public class MyEntry<K, V> implements Map.Entry<K, V> {

    private MyEntry nextCollision;
    private K key;
    private V value;
    private int hashCode;

    MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hashCode = key.hashCode();
    }

    boolean hasNext() {
        return nextCollision != null;
    }

    boolean equalsKey(MyEntry<K, V> entry) {
        if (this.getHashCode() != entry.getHashCode()) {
            return false;
        } else {
            return this.getKey().equals(entry.getKey());
        }
    }

    @Override
    public String toString() {
        return this.hashCode + " " + this.key + " " + this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyEntry<?, ?> myEntry = (MyEntry<?, ?>) o;
        return key.equals(myEntry.key) &&
                Objects.equals(value, myEntry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    int getHashCode() {
        return hashCode;
    }

    MyEntry getNextCollision() {
        return nextCollision;
    }

    void setNextCollision(MyEntry nextCollision) {
        this.nextCollision = nextCollision;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

}
