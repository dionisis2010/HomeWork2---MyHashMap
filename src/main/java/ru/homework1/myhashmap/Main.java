package ru.homework1.myhashmap;

import javax.swing.plaf.synth.SynthToolTipUI;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        MyMap<String, String> map = new MyHashMap<>();
        map.put("key", "123");
        Element element = map.getElement("key");
        System.out.println(element);
        map.print();

    }
}
