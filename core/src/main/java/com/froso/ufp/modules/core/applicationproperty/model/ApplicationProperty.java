package com.froso.ufp.modules.core.applicationproperty.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

/**
 * The type Simple application property.
 */
@Document(collection = ApplicationProperty.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ))

)
public final class ApplicationProperty extends AbstractDataDocument {

    public static final String TYPE_NAME = "ApplicationProperty";
    private static final long serialVersionUID = 1;
    @Indexed(unique = true)
    @NotNull
    private String key = "defaultKey";

    private String value = "defaultValue";

    public ApplicationProperty() {

        super(TYPE_NAME);
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {

        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {

        this.value = value;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {

        return key;
    }

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(String key) {

        this.key = key;
    }

}
