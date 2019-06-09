package com.froso.ufp.modules.optional.textbot.yesno2.model;

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
        })


)
public class YesNoTextBotModel extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "YesNoTextBotModel";
    private List<String> facebookIdsVoted = new ArrayList<>();

    private Integer yesCount = 0;
    private Integer noCount = 0;
    private String text;

    /**
     * Constructor Simple app device.
     */
    public YesNoTextBotModel() {

        super(TYPE_NAME);
    }

    /**
     * Gets facebook ids voted.
     *
     * @return the facebook ids voted
     */
    public List<String> getFacebookIdsVoted() {
        return facebookIdsVoted;
    }

    /**
     * Sets facebook ids voted.
     *
     * @param facebookIdsVoted the facebook ids voted
     */
    public void setFacebookIdsVoted(List<String> facebookIdsVoted) {
        this.facebookIdsVoted = facebookIdsVoted;
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
     * Gets yes count.
     *
     * @return the yes count
     */
    public Integer getYesCount() {
        return yesCount;
    }

    /**
     * Sets yes count.
     *
     * @param yesCount the yes count
     */
    public void setYesCount(Integer yesCount) {
        this.yesCount = yesCount;
    }

    /**
     * Gets no count.
     *
     * @return the no count
     */
    public Integer getNoCount() {
        return noCount;
    }

    /**
     * Sets no count.
     *
     * @param noCount the no count
     */
    public void setNoCount(Integer noCount) {
        this.noCount = noCount;
    }
}
