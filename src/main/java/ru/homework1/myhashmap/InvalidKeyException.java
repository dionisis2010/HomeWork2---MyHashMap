package ru.homework1.myhashmap;

public class InvalidKeyException extends RuntimeException{
    public InvalidKeyException(String message) {
        super(message);
    }
}
