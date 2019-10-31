package ru.homework1.myhashmap;

import java.util.Objects;

public class MyEntry<K,V> {

    private MyEntry nextCollision;
    private K key;
    private V value;
    private int hashCode;

    public MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hashCode = key.hashCode();
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

    public MyEntry getNextCollision() {
        return nextCollision;
    }

    public void setNextCollision(MyEntry nextCollision) {
        this.nextCollision = nextCollision;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }
}
