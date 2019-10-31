package ru.homework1.myhashmapalpha;

/**
 * альфа версия собственной реализации Map, новый элемент записывает в следующую "свободную ячейку" массива,
 * поиск ключа происходит с помощью простого перебора всех элементов массива по хэшкоду
 * отсутвует механизм авторасширения и копирования массива, а так же добавления в "нашу" мапу другой мапы
 * @param <K>
 * @param <V>
 */
public class MyHashMapAlpha<K, V> implements MyMap<K, V> {

    private Element[] elements;
    private Integer[] hashCodes;
    private int size = 0;
    private int lastUseID = -1;

    public MyHashMapAlpha(int arrayLength) {
        this.elements = new Element[arrayLength];
        this.hashCodes = new Integer[arrayLength];
    }

    public MyHashMapAlpha() {
        this(10);
    }

    /**
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
            elements[this.lastUseID] = new Element((K) key, (V) value);
            hashCodes[this.lastUseID] = key.hashCode();
            size++;
            return true;
        }
    }

    /**
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
            size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * присваивает всем элементам массива null
     */
    @Override
    public boolean clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
            hashCodes[i] = null;
        }
        this.size = 0;
        this.lastUseID = 0;
        return true;
    }

    /**
     * @return возвращает количество занятых ячеек в массиве
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true сли в массиве есть ключ
     */
    @Override
    public boolean haveKey(K key) {
        return searchKeyWithHash(key) != -1;
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
     *
     * @return возвращает -1 если нет свободной ячейки в массиве или индекс свободной ячейки
     */
    private int intcrementID() {
        if (size >= elements.length) return -1;
        for (int newID = this.lastUseID + 1; newID < elements.length; newID++) {
            if (elements[newID] == null) {
                return this.lastUseID = newID;
            }
        }
        for (int id = 0; id < this.lastUseID; id++) {
            if (elements[id] == null) {
                return this.lastUseID = id;
            }
        }
        return this.lastUseID = -1;
    }

    /**
     * принимает значение key преобразует в hashCode и производит поиск по массиву
     * @return возвращает индекс элемента соответсвтующего ключю
     * возвращает -1 если такого ключа нет
     */
    private int searchKeyWithHash(K key) {
        validKey(key);
        int id;
        for (id = 0; id < elements.length; id++) {
            if (hashCodes[id] == null) continue;
            if (hashCodes[id] == key.hashCode()) return id;
        }
        return -1;
    }

    private void validKey(K key){
        if (key == null){
            throw new NullPointerException();
        }
    }
}
