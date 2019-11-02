package ru.homework2.myhashmapalpha;

import java.util.Objects;

public class Element<K, V> {

    private K key;
    private V value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element<?, ?> element = (Element<?, ?>) o;
        return key.equals(element.key) &&
                Objects.equals(value, element.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

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
