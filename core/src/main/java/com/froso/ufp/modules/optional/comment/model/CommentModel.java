package com.froso.ufp.modules.optional.comment.model;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.validation.constraints.*;
import org.joda.time.*;

public class CommentModel extends AbstractDataDocumentWithClientLink implements Comment {

    private static final long serialVersionUID = 5634045466747827958L;
    @NotNull
    private String comment;

    @NotNull
    @ApiModelProperty(notes = "The resource describes a list of resources that are this comment is refering to")
    private List<DataDocumentLink> commentResource = new ArrayList<>();
    @NotNull
    @ApiModelProperty(notes = "The list of describes a list of resources that are this comment is created from")
    private List<DataDocumentLink> relevants = new ArrayList<>();
    private DateTime date = DateTime.now();

    public CommentModel(String name) {

        super(name);
    }

    public List<DataDocumentLink> getCommentResource() {
        return commentResource;
    }

    public void setCommentResource(List<DataDocumentLink> commentResource) {
        this.commentResource = commentResource;
    }

    public List<DataDocumentLink> getRelevants() {
        return relevants;
    }

    public void setRelevants(List<DataDocumentLink> relevants) {
        this.relevants = relevants;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @JsonSerialize(using = JodaDateTimeSerializer.class)

    public DateTime getDate() {
        return date;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setDate(DateTime date) {
        this.date = date;
    }

}
