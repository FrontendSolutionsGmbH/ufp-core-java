package com.froso.ufp.modules.optional.facebookbot.model.bot;

import java.util.*;

/**
 * Created by ck on 16.08.2016.
 */

public class CallbackData<T> {
    private String object;
    private List<T> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<T> getEntry() {
        return entry;
    }

    public void setEntry(List<T> entry) {
        this.entry = entry;
    }
}
