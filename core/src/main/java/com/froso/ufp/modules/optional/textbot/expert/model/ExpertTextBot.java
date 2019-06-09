package com.froso.ufp.modules.optional.textbot.expert.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import java.util.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("BOT"), @ResourceKeyword("MENU_BOT")
        }), defaultView = @ResourceViewAnnotation(
        sort = @ResourceFilterSortValues(
                @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
        ),
        visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("name"),
                @ResourceVisibleColumn("text"),
                @ResourceVisibleColumn("buttonText"),
                @ResourceVisibleColumn("option1"),
                @ResourceVisibleColumn("option2"),
                @ResourceVisibleColumn("option3"),
                @ResourceVisibleColumn("type"),
                @ResourceVisibleColumn("method"),
                @ResourceVisibleColumn("url"),
        }))

)
public class ExpertTextBot extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "ExpertTextBot";

    @TextIndexed
    @ApiModelProperty(notes = "The text to be displayed when this entry becomes active", position = 103)
    private String text;
    @TextIndexed
    @ApiModelProperty(notes = "The text to be displayed on the button linking to this entry", position = 102)
    private String buttonText;
    @UfpPossibleLinkTypes({ExpertTextBot.class})
    @ApiModelProperty(notes = "Possible options, remember to include a loop to root if reached bottom", position = 110)
    private List<DataDocumentLink<ExpertTextBot>> options;


    @ApiModelProperty(notes = "Node type at least one entry of type ROOT is required, all other entries should be NODE", position = 101)
    private ExpertbotNodeStatus type = ExpertbotNodeStatus.NODE;

    @ApiModelProperty(notes = "The actual button type\n\n" +
            "NORMAL_BUTTON", position = 100)
    private ExpertbotButtonType buttonType = ExpertbotButtonType.NORMAL_BUTTON;


    /**
     * Constructor Simple app device.
     */
    public ExpertTextBot() {
        super(TYPE_NAME);
    }


    /**
     * Gets button type.
     *
     * @return the button type
     */
    public ExpertbotButtonType getButtonType() {
        return buttonType;
    }

    /**
     * Sets button type.
     *
     * @param buttonType the button type
     */
    public void setButtonType(ExpertbotButtonType buttonType) {
        this.buttonType = buttonType;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ExpertbotNodeStatus getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(ExpertbotNodeStatus type) {
        this.type = type;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets button text.
     *
     * @return the button text
     */
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Sets button text.
     *
     * @param buttonText the button text
     */
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    /**
     * Gets options.
     *
     * @return the options
     */
    public List<DataDocumentLink<ExpertTextBot>> getOptions() {
        return options;
    }

    /**
     * Sets options.
     *
     * @param options the options
     */
    public void setOptions(List<DataDocumentLink<ExpertTextBot>> options) {
        this.options = options;
    }

}
