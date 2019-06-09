package com.froso.ufp.core.domain.documents.simple.plain.enums;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 14:33 To change
 * this template use File | Settings | File Templates.
 */
public class EnumDescription implements IDataObject {

    private final List<String> values = new ArrayList<>();
    private String description;
    private String name;

    /**
     * Gets values.
     *
     * @return the values
     */
    public List<String> getValues() {

        return values;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {

        this.description = description;
    }
}
