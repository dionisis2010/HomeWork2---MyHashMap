package ru.homework1.myhashmap;

public class Element<K, V> {
    private K key;
    private V value;

    @Override
    public String toString() {
        return this.getKey() + " " + this.getValue();
    }

    Element(K key, V value) {
        this.key = key;
        this.value = value;
    }

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    void setValue(V value) {
        this.value = value;
    }
}
