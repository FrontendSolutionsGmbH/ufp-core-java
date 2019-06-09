package com.froso.ufp.modules.core.applicationproperty.model;

/**
 * Created by mr on 14.06.16.
 */
public class SinglePropertyRequestBody {
    String key;
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
