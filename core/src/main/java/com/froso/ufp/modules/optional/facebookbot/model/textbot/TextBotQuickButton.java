package com.froso.ufp.modules.optional.facebookbot.model.textbot;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotQuickButton implements ITextBotQuickButton {

    private String title;
    private String payload;

    /**
     * Create quick text button text bot quick button.
     *
     * @param title   the title
     * @param payload the payload
     * @return the text bot quick button
     */
    public static final TextBotQuickButton createQuickTextButton(String title, String payload) {
        TextBotQuickButton result = new TextBotQuickButton();

        result.setTitle(title);
        result.setPayload(payload);
        return result;

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
        this.title = title;
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
}
