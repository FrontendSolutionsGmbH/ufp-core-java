package com.froso.ufp.modules.optional.textbot.yesno2.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
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
                        @ResourceVisibleColumn("enabled"),
                        @ResourceVisibleColumn("urlToCall")
                }))

)
public class YesNoTextBotConfig extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "YesNoTextBotConfig";
    @ApiModelProperty(notes = "Called after a vote has been casted urltocall is created from voteviewUrl")
    private String urlToCall = "http://office.froso.de:8099/browser?url=$1";
    @ApiModelProperty(notes = "GET Url that gets ?yes=XX&no=YY get parameters attached")
    private String voteViewUrl = "https://demo-dev.froso.de:8101/web/admin/index.html#/yesnoview";
    @ApiModelProperty(notes = "Enabled voting globally")
    private Boolean enabled = true;
    @ApiModelProperty(notes = "Enabled voting globally")
    @UfpPossibleLinkTypes({YesNoTextBotModel.class})
    private List<DataDocumentLink<YesNoTextBotModel>> selection = new ArrayList<>();

    /**
     * Constructor Simple app device.
     */
    public YesNoTextBotConfig() {

        super(TYPE_NAME);
    }

    /**
     * Gets enabled.
     *
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets selection.
     *
     * @return the selection
     */
    public List<DataDocumentLink<YesNoTextBotModel>> getSelection() {
        return selection;
    }

    /**
     * Sets selection.
     *
     * @param selection the selection
     */
    public void setSelection(List<DataDocumentLink<YesNoTextBotModel>> selection) {
        this.selection = selection;
    }

    /**
     * Gets vote view url.
     *
     * @return the vote view url
     */
    public String getVoteViewUrl() {
        return voteViewUrl;
    }

    /**
     * Sets vote view url.
     *
     * @param voteViewUrl the vote view url
     */
    public void setVoteViewUrl(String voteViewUrl) {
        this.voteViewUrl = voteViewUrl;
    }

    /**
     * Gets url to call.
     *
     * @return the url to call
     */
    public String getUrlToCall() {
        return urlToCall;
    }

    /**
     * Sets url to call.
     *
     * @param urlToCall the url to call
     */
    public void setUrlToCall(String urlToCall) {
        this.urlToCall = urlToCall;
    }


}
