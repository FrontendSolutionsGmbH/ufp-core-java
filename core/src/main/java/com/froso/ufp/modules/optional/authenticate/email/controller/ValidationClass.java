package com.froso.ufp.modules.optional.authenticate.email.controller;

/**
 * Created by ck on 07.07.2016.
 */
public class ValidationClass<T> {
    private T elementNew;
    private T elementOld;

    public T getElementNew() {
        return elementNew;
    }

    public void setElementNew(T elementNew) {
        this.elementNew = elementNew;
    }

    public T getElementOld() {
        return elementOld;
    }

    public void setElementOld(T elementOld) {
        this.elementOld = elementOld;
    }

}
