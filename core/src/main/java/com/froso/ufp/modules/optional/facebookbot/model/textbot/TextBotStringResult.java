package com.froso.ufp.modules.optional.facebookbot.model.textbot;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotStringResult extends TextBotResult implements ITextBotStringResult {
    private String text;

    public TextBotStringResult(String text) {
        this.text = text;
    }

    public TextBotStringResult() {

    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
