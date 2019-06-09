package com.froso.ufp.core.domain.documents;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.hateoas.*;

import java.util.*;

/**
 * entry class for documents of the application, a document is a mongodb collection element documents are collected in
 * such mongodb collections
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public abstract class AbstractDocument implements Identifiable<String> {

    @ApiModelProperty(position = 1000, example = "9703bd50-57a4-4776-8ded-f0458bfe2a6d", notes = "This is the primary id of the DataDocument, and ufp uses uuid string format.")
    @Id
    //  index creation leads to problems, not needed anyway due to mongo default indexing id column .... : @Indexed(unique = true, background = true)
    @JsonView(JacksonViews.Minimal.class)
    private String id;

    @JsonCreator
    protected AbstractDocument() {
        // On Construction we always create a new UUID !
        id = generateID();
    }

    public static String generateID() {

        return UUID.randomUUID().toString();
    }

    public void generateNewId() {
        this.setId(generateID());
    }

    @Override
    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

}
