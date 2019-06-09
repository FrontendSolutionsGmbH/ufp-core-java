package com.froso.ufp.core.domain.documents;

import com.froso.ufp.core.domain.documents.simple.plain.meta.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.builder.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 01.11.13 Time: 13:17
 * <p>
 * The Data Document for once implements the DataObject Interface, just to be used as a general Data Element, the
 * IDataObject Interface is just a marker interface
 * <p>
 * All Elements that we organize in the mongodb use a "lastChange" timestamp, that is used to inform a client about
 * wether it has to update stored data,
 */
public abstract class AbstractDataDocument extends AbstractDocument implements IDataDocument {

    public static final int HASH_CODE_ODD_NUMBER_START = 17;
    public static final int HASH_CODE_ODD_NUMBER_MULTIPLIER = 37;
    public static final String META_DATA_LAST_CHANGED_TIMESTAMP = "metaData.lastChangedTimestamp";
    @ApiModelProperty(hidden = false, required = false, readOnly = true, position = 1000, notes = "Internal Housekeeping Object")
    private DataDocumentMetaData metaData;
    @ApiModelProperty(hidden = true, required = false, position = 100, notes = "Additional properties")
    private Map<String, Object> additionalProperties = new HashMap<>();

    protected AbstractDataDocument(String type) {

        metaData = new DataDocumentMetaData(type);
    }

    @Override
    public DataDocumentMetaData getMetaData() {

        return metaData;
    }

    @Override
    public void setMetaData(DataDocumentMetaData meta) {

        this.metaData = meta;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {

        return additionalProperties;
    }

    @Override
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {

        this.additionalProperties = additionalProperties;
    }

    public String toString() {

        return "type: " + metaData.getType() + " id: " + getId();

    }

    /**
     * we create the hashcode from the object id and the object type in each case they are strings and so the hashcode
     * of the object type is multiplied by an prime number, as common in java we use 31, giving great distinction
     * between hashes created among a various subclasses then the hash of the object id is just added to it
     *
     * @return int
     * @Remark: the java int class performs a modulus operation on under and overflows!
     */
    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(HASH_CODE_ODD_NUMBER_START, HASH_CODE_ODD_NUMBER_MULTIPLIER).
                append(metaData).
                append(additionalProperties).
                appendSuper(super.hashCode()).
                toHashCode();
    }

    /**
     * The equals function is overriden for our data documents, the type and the id together make up the identifier for
     * an object, the equals method is used for various functionality like using this object in java sets easily
     *
     * @param toCompare the to compare
     * @return boolean
     */
    @Override
    public boolean equals(Object toCompare) {

        boolean result = false;
        if (this == toCompare) {
            result = true;
        } else if (toCompare != null && this.getClass().equals(toCompare.getClass())) {
            AbstractDataDocument castedToCompare = (AbstractDataDocument) toCompare;
            if (this.getId().equals(castedToCompare.getId()) && this.getMetaData().getType().equals(castedToCompare.getMetaData().getType())) {
                result = true;
            }
        }

        return result;
    }

}
