package com.froso.ufp.core.domain.documents.simple.plain.meta;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;
import org.joda.time.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 02.11.13 Time: 10:47
 * <p>
 * The Metadata Object is for storing information about the object, since it is subject to be extended it is maintained
 * in this dataobject, for not cluttering the datadocument too much!
 */
public class DataDocumentMetaData {

    @ApiModelProperty(hidden = false,
            allowEmptyValue = true,
            required = false,
            readOnly = true,
            notes = "The resource name")
    private String type;

    @ApiModelProperty(hidden = false,
            readOnly = true,
            required = false,
            notes = "Date of last modification")
    @Indexed
    private DateTime lastChangedTimestamp;
    @ApiModelProperty(hidden = false,
            readOnly = true,
            required = false,
            notes = "Creation date")
    @Indexed
    private DateTime creationTimestamp;

    @ApiModelProperty(hidden = false,
            readOnly = true,
            required = false,
            notes = "Reference to last modification user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DataDocumentLink<ICoreUser> lastEditorUserLink = new DataDocumentLink<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = false,
            readOnly = true,
            required = false,
            notes = "Reference to creator user")
    private DataDocumentLink<ICoreUser> creatorUserLink = new DataDocumentLink<>();

    public DataDocumentMetaData(String type) {
        // Upon creation current time is used, for updating this needs to be set manually!
        lastChangedTimestamp = DateTime.now();
        creationTimestamp = DateTime.now();
        this.type = type;
    }

    private DataDocumentMetaData() {
        lastChangedTimestamp = DateTime.now();
        creationTimestamp = DateTime.now();
    }

    public String getType() {

        return type;
    }


    public DataDocumentLink<ICoreUser> getLastEditorUserLink() {
        return lastEditorUserLink;
    }

    public void setLastEditorUserLink(DataDocumentLink<ICoreUser> lastEditorUserLink) {
        this.lastEditorUserLink = lastEditorUserLink;
    }

    public DataDocumentLink<ICoreUser> getCreatorUserLink() {
        return creatorUserLink;
    }

    public void setCreatorUserLink(DataDocumentLink<ICoreUser> creatorUserLink) {
        this.creatorUserLink = creatorUserLink;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getLastChangedTimestamp() {

        return new DateTime(lastChangedTimestamp);
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setLastChangedTimestamp(DateTime lastChangedTimestamp) {

        this.lastChangedTimestamp = new DateTime(lastChangedTimestamp);
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCreationTimestamp() {

        return creationTimestamp;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCreationTimestamp(DateTime creationTimestamp) {
        // Save as copy!
        this.creationTimestamp = new DateTime(creationTimestamp);
    }
}
