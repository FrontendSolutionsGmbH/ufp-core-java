package com.froso.ufp.modules.optional.textbot.simple.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import java.util.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("BOT"), @ResourceKeyword("MENU_BOT")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name")
                }))

)
public class SimpleTextBotModel extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "SimpleTextBot";

    private List<String> attractionKeys = new ArrayList<>();


    private List<String> answers = new ArrayList<>();

    /**
     * Constructor Simple app device.
     */
    public SimpleTextBotModel() {

        super(TYPE_NAME);
    }


    /**
     * Gets attraction keys.
     *
     * @return the attraction keys
     */
    public List<String> getAttractionKeys() {
        return attractionKeys;
    }

    /**
     * Sets attraction keys.
     *
     * @param attractionKeys the attraction keys
     */
    public void setAttractionKeys(List<String> attractionKeys) {
        this.attractionKeys = attractionKeys;
    }

    /**
     * Gets answers.
     *
     * @return the answers
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Sets answers.
     *
     * @param answers the answers
     */
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

}
