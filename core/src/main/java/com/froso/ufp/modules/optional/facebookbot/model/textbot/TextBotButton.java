package com.froso.ufp.modules.optional.facebookbot.model.textbot;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotButton implements ITextBotButton {

    private ButtonType buttonType;
    private String title;
    private String url;
    private String payload;

    public static final TextBotButton createUrlButton(String title, String url) {
        TextBotButton result = new TextBotButton();

        result.setButtonType(ButtonType.WEB_URL);
        result.setTitle(title);
        result.setUrl(url);
        return result;

    }

    public static final TextBotButton createPostbackButton(String title, String payload) {
        TextBotButton result = new TextBotButton();

        result.setButtonType(ButtonType.POSTBACK);
        result.setTitle(title);
        result.setPayload(payload);
        return result;

    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public void setButtonType(ButtonType buttonType) {
        this.buttonType = buttonType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
