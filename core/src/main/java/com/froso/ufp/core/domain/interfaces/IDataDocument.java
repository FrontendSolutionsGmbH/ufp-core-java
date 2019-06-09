package com.froso.ufp.core.domain.interfaces;

import com.froso.ufp.core.domain.documents.simple.plain.meta.*;
import java.util.*;
import org.springframework.hateoas.*;

/**
 * Created by ckleinhuix on 02.12.2015.
 */
public interface IDataDocument extends IDataObject, IAdditionalPropertiesUser, Identifiable<String> {
    /**
     * Gets meta data.
     *
     * @return the meta data
     */
    DataDocumentMetaData getMetaData();

    /**
     * Sets meta data.
     *
     * @param meta the meta
     */
    void setMetaData(DataDocumentMetaData meta);


    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(String id);

    /**
     * Sets additional properties.
     *
     * @param additionalProperties the additional properties
     */
    void setAdditionalProperties(Map<String, Object> additionalProperties);
}
