package ru.homework1.myhashmap;

public interface MyMap<K, V> {

    boolean put(K key, V value);

    boolean delete(K key);

    Element getElement(K key);

    V getValue(K key);

    boolean clear();

    int size();

    boolean haveKey(K key);

    void print();
}
