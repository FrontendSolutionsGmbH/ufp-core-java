package com.froso.ufp.modules.optional.textbot.bureaubot.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("BOT"), @ResourceKeyword("MENU_BOT"),
        }),
        defaultView = @ResourceViewAnnotation(visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("name")
        }))

)
public class BureauBot extends ClientReference {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "BureauUrls";


    private Integer displayCount = 0;
    private String url;
    private String type;
    private String contentType;

    /**
     * Instantiates a new Bureau bot.
     */
    public BureauBot() {

        super(TYPE_NAME);
    }

    /**
     * Gets display count.
     *
     * @return the display count
     */
    public Integer getDisplayCount() {
        return displayCount;
    }

    /**
     * Sets display count.
     *
     * @param displayCount the display count
     */
    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
