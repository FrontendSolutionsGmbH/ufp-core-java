package com.froso.ufp.core.domain.documents.simple.plain;

public class KeyValueItemTyped<T> {

    private String key;
    private T value;

    public KeyValueItemTyped() {

    }

    public KeyValueItemTyped(String keyIn, T valueIn) {
        key = keyIn;
        value = valueIn;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public T getValue() {

        return value;
    }

    public void setValue(T value) {

        this.value = value;
    }
}
