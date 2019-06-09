package com.froso.ufp.core.util;

import java.lang.reflect.*;
import org.apache.commons.beanutils.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 29.11.13 Time: 11:45
 *
 * Code Taken from "stackoverflow http://stackoverflow
 * .com/questions/1301697/helper-in-order-to-copy-non-null-properties-from-object-to-another-java
 *
 * the purpose is to copy non-null properties of an object to another, this is mainly used by the updating process, any
 * non null value is transfered to the existing object
 */
public class NullAwareBeanUtils extends BeanUtilsBean {

    /**
     * Copy property.
     *
     * @param dest  the dest
     * @param name  the name
     * @param value the value
     * @throws IllegalAccessException the illegal access exception
     * @throws IllegalAccessException the illegal access exception
     */
    @Override
    public void copyProperty(Object dest,
                             String name,
                             Object value) throws IllegalAccessException, InvocationTargetException {

        if (null != value) {
            // Just copy non-null values
            super.copyProperty(dest, name, value);
        }
    }
}
