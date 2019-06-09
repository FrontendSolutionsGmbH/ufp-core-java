package com.froso.ufp.modules.core.applicationproperty.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.joda.time.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * The type Simple application property.
 */
@Document(collection = ApplicationPropertyOverride.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                )
        )

)
public final class ApplicationPropertyOverride extends AbstractDataDocumentWithClientLink {
    public static final String TYPE_NAME = "ApplicationPropertyOverride";
    @Indexed(unique = true)
    private String key = "defaultKey";
    private String value = "defaultValue";

    @NotNull
    @ApiModelProperty(dataType = "java.language.String", example = "00:00:00")
    private LocalTime start = LocalTime.MIDNIGHT;
    @NotNull
    @ApiModelProperty(dataType = "java.language.String", example = "00:00:00")
    private LocalTime end = new LocalTime(12, 0, 0, 0);

    public ApplicationPropertyOverride() {

        super(TYPE_NAME);
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public LocalTime getStart() {
        return start;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setStart(LocalTime start) {
        this.start = start;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public LocalTime getEnd() {
        return end;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @AssertTrue(message = "endTime must be after startTime")
    private boolean isOk() {
        if (getStart() == null) {
            return false;
        }
        if (getEnd() == null) {
            return false;
        }
        return getStart().isBefore(getEnd());
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

}
