package ru.homework1.myhashmap;


public class MyHashMap<K, V> implements MyMap<K, V> {

    private Element[] elements;
    private Integer[] hashCodes;
    /**
     * количество пустых ячеек
     */
    private int count = 0;
    /**
     * полсдений используемый id в массиве
     */
    private int id = -1;

    public MyHashMap(int arrayLength) {
        this.elements = new Element[arrayLength];
        this.hashCodes = new Integer[arrayLength];
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
    @Override
    public boolean put(K key, V value) {
//        int id = searchKey(key);
        int id = searchKeyWithHash(key);
        if (id != -1) {
            elements[id].setValue(value);
            return true;
        } else {
            if (intcrementID() == -1) return false;
            elements[this.id] = new Element((K) key, (V) value);
            hashCodes[this.id] = key.hashCode();
            count++;
            return true;
        }
    }

    /**
     * @param key
     * @return возвращает объект Element соттветсвующие key
     * @throws InvalidKeyException - бросается при обращении к key, которого нет в массиве
     */
    @Override
    public Element getElement(K key) {
        int id = searchKeyWithHash(key);
        if (id == -1) {
            throw new InvalidKeyException("Попытка обратиться к несуществующему ключу");
        } else {
            return elements[id];
        }
    }

    /**
     * @param key
     * @return возвращает value соответствующее key
     * @throws InvalidKeyException - бросается при обращении к key, которого нет в массиве
     */
    @Override
    public V getValue(K key) {
        int id = searchKeyWithHash(key);
        if (id == -1) {
            throw new InvalidKeyException("Попытка обратиться к несуществующему ключу");
        } else {
            return (V) elements[id].getValue();
        }
    }

    /**
     * удаление происходит путем присвоения ячейке с указанным key значения null
     *
     * @param key
     * @return вернет true если удаление прошло успешно
     * вернет false если нет елемента с таким key
     */
    @Override
    public boolean delete(K key) {
//        int id = searchKey(key);
        int id = searchKeyWithHash(key);
        if (id > 0) {
            elements[id] = null;
            hashCodes[id] = null;
            count--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * присваивает всем элементам массива null
     *
     * @return
     */
    @Override
    public boolean clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
            hashCodes[i] = null;
        }
        this.count = 0;
        this.id = 0;
        return true;
    }

    /**
     * @return возвращает количество занятых ячеек в массиве
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * @param key
     * @return true сли в массиве есть ключ
     */
    @Override
    public boolean haveKey(K key) {
        if (searchKeyWithHash(key) != -1) return true;
        else return false;
    }

    /**
     * выводит в консоль каждый элемент с новой строки
     */
    public void print() {
        for (int i = 0; i < elements.length; i++) {
            System.out.println(i + ". " + hashCodes[i] + " " + elements[i]);
        }
    }

    /**
     * пределяет доступную для записи ячейку
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
     * принимает значение key преобразует в hashCode и производит поиск по массиву
     *
     * @param key
     * @return возвращает индекс элемента соответсвтующего ключю
     * возвращает -1 если такого ключа нет
     */
    private int searchKeyWithHash(K key) {
        int id;
        for (id = 0; id < elements.length; id++) {
            if (hashCodes[id] == null) continue;
            if (hashCodes[id] == key.hashCode()) return id;
        }
        return -1;
    }

    //    /**
//     * @param key
//     * @return проверяет наличие ключа через equals
//     */
//    public boolean haveKey(K key){
//        if (searchKey(key) != -1) return true;
//        else return false;
//    }

    //    /**
//     * проверяет наличие ключа через equals
//     * @param key
//     * @return если в массиве есть указанный key, возвращает индекс ячейки, инче возвращает -1
//     */
//    private int searchKey(K key) {
//        int id;
//        for (id = 0; id < elements.length; id++) {
//            if (elements[id] == null) continue;
//            if (elements[id].getKey().equals(key)) return id;
//        }
//        return -1;
//    }

}
