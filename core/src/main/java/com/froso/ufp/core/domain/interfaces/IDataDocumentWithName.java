package com.froso.ufp.core.domain.interfaces;

import org.springframework.hateoas.*;

/**
 * Created by ckleinhuix on 02.12.2015.
 */
public interface IDataDocumentWithName extends IDataDocument, IDataObject, IAdditionalPropertiesUser, Identifiable<String> {
    /**
     * Gets additional properties.
     *
     * @return the additional properties
     */
    String getName();

    /**
     * Sets additional properties.
     *
     * @param name the name
     */
    void setName(String name);
}
