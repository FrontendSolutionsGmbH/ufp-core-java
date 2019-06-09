package com.froso.ufp.modules.optional.facebookbot.model.bot;

import org.apache.commons.lang.*;

/**
 * Created by ck on 19.08.2016.
 */
public class QuickButton {
    public static final int FB_MAX_QUICKBUTTON_TITLE_WIDTH = 20;
    private String content_type;

    private String title;
    private String payload;

    /**
     * Instantiates a new Quick button.
     */
    public QuickButton() {
        this.content_type = "text";
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = StringUtils.abbreviate(title, FB_MAX_QUICKBUTTON_TITLE_WIDTH);
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }


    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContent_type() {
        return content_type;
    }

    /**
     * Sets content type.
     *
     * @param content_type the content type
     */
    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
}
