package com.froso.ufp.core.domain.interfaces;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:48
 *
 * this interface defines a method for any object to have any type of property associated with it
 *
 * currently it is just a marker interface
 */
public interface IAdditionalPropertiesUser {

    /**
     * Gets additional properties.
     *
     * @return the additional properties
     */
    Map<String, Object> getAdditionalProperties();
}
