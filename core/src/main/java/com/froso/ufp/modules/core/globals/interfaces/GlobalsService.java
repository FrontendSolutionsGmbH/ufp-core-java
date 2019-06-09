package com.froso.ufp.modules.core.globals.interfaces;

import java.util.*;

/**
 * Created by ckleinhuix on 04.12.2015.
 */
public interface GlobalsService {
    void add(String key, String value);

    void add(String key, Object object);

    void add(String key, IGlobalsPropertyProvider object);

    void addProperty(String key, String propertyName);

    Map<String, Object> getAllProperties();
}
