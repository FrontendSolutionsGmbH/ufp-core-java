package com.froso.ufp.modules.core.applicationproperty.interfaces;

import com.froso.ufp.modules.core.applicationproperty.exceptions.*;

/**
 * Created by ckleinhuix on 02.12.2015.
 */
public interface IPropertyService {
    String getPropertyValue(String key) throws PropertyException;

    String getPropertyValue(String key, String defaultValue) throws PropertyException;

    Boolean getPropertyValueBoolean(String key) throws PropertyException;

    Integer getPropertyValueInteger(String key) throws PropertyException;

    Integer getPropertyValueInteger(String key, Integer defaulValue) throws PropertyException;

    void registerProperty(String key, String defaultValue) throws PropertyException;


}
