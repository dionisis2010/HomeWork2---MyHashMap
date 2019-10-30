package ru.homework1.myhashmap;

public class MyHashMap<K, V> {
    private int count = 0;
    private Element[] elements;
    private int[] hashCodes;
    private int id = -1;

    public MyHashMap(int arrayLength) {
        this.elements = new Element[arrayLength];
        this.hashCodes = new int[arrayLength];
    }

    public MyHashMap() {
        this(10);
    }

    /**
     * @param key
     * @param value
     * @return возвращает true если успешно прошла запись в массив, если уже был эллемент с таким key, то презапишет value
     * вернет false если нет свободного места в массиве
     */
    public boolean put(K key, V value) {
        int id = searchKey(key);
        if (id != -1) {
            elements[id].setValue(value);
            return true;
        } else {
            if (intcrementID() == -1) return false;
            elements[this.id] = new Element((K) key, (V) value);
            count++;
            return true;
        }
    }

    /**
     * удаление происходит путем присвоения ячейке с указанным key значения null
     *
     * @param key
     * @return вернет true если удаление прошло успешно
     * вернет false если нет елемента с таким key
     */
    public boolean delete(K key) {
        int id = searchKey(key);
        if (id > 0) {
            elements[id] = null;
            count--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * выводит в консоль каждый элемент с новой строки
     */
    public void print() {
        for (int i = 0; i < elements.length; i++) {
            System.out.println(i + ". " + elements[i]);
        }
    }

    public boolean clear(){
        for (int i = 0; i< elements.length; i++){
            elements[i] = null;
        }
        this.count=0;
        this.id = 0;
        return true;
    }

    public int size(){
        return count;
    }

    public boolean search(K key){
        if (searchKey(key) != -1) return true;
        else return false;
    }

    /**
     * @return возвращает -1 если нет свободной ячейки в массиве или индекс свободной ячейки
     */
    private int intcrementID() {
        if (count >= elements.length) return -1;
        for (int newID = this.id + 1; newID < elements.length; newID++) {
            if (elements[newID] == null) {
                return this.id = newID;
            }
        }
        for (int id = 0; id < this.id; id++) {
            if (elements[id] == null) {
                return this.id = id;
            }
        }
        return id = -1;
    }

    /**
     * @param key
     * @return если в массиве есть указанный key, возвращает индекс ячейки, инче возвращает -1
     */
    private int searchKey(K key) {
        int id;
        for (id = 0; id < elements.length; id++) {
            if (elements[id] == null) continue;
            if (elements[id].getKey().equals(key)) return id;
        }
        return -1;
    }


}
