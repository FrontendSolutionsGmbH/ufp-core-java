package com.froso.ufp.modules.optional.facebookbot.model.textbot;

import java.util.*;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotButtonResult extends TextBotStringResult implements ITextBotButtonsResult {
    private List<ITextBotButton> buttons = new ArrayList<>();


    /**
     * Instantiates a new Text bot button result.
     *
     * @param text the text
     */
    public TextBotButtonResult(String text) {
        super(text);
    }

    /**
     * Instantiates a new Text bot button result.
     */
    public TextBotButtonResult() {
    }

    public List<ITextBotButton> getButtons() {
        return buttons;
    }

    /**
     * Sets buttons.
     *
     * @param buttons the buttons
     */
    public void setButtons(List<ITextBotButton> buttons) {
        this.buttons = buttons;
    }


}
