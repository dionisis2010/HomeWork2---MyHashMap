package ru.homework1.myhashmap;

public class MyHashMap<K, V> {
    private int count = 0;
    private int arrayLength;
    private Element[] elements;
    private int id = -1;

    public MyHashMap(int arrayLength) {
        this.arrayLength=arrayLength;
        this.elements = new Element[arrayLength];
    }
    public MyHashMap(){
        this(10);
    }

    /**
     *
     * @param key
     * @param value
     * @return возвращает true если успешно прошла запись в массив, если уже был эллемент с таким key, то презапишет value
     * вернет false если нет свободного места в массиве
     */
    public boolean put(K key, V value) {
        int id = searchKey(key);
        if (id != -1){
            elements[id].setValue(value);
            return true;
        }
        else{
            if (intcrementID() == -1) return false;
            elements[this.id] = new Element((K) key, (V) value);
            count++;
            return true;
        }
    }

    /**
     * удаление происходит путем присвоения ячейке с указанным key значения null
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
        for (int i = 0; i< elements.length; i++){
            System.out.println(i +". " + elements[i]);
        }
    }

    /**
     * @return возвращает -1 если нет свободной ячейки в массиве или индекс свободной ячейки
     */
    private int intcrementID() {
        if (count >= elements.length) return -1;
        for (int id = this.id + 1; id < elements.length; id++) {
            if (elements[id] == null) {
                this.id = id;
                return id;
            }
        }
        for (int id = 0; id < this.id; id++) {
            if (elements[id] == null) {
                this.id = id;
                return id;
            }
        }
        return id =-1;
    }

    /**
     * @param key
     * @return если в массиве есть указанный key, возвращает индекс ячейки, инче возвращает -1
     */
    private int searchKey(K key) {
        int id;
        for (id = 0; id < elements.length; id++) {
            if (elements[id] == null) continue;

            Element element = elements[id];
            if (element.getKey().equals(key)) return id;
            else continue;
        }
        return -1;
    }


}
