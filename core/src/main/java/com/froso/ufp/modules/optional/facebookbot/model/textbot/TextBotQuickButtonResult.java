package com.froso.ufp.modules.optional.facebookbot.model.textbot;

import java.util.*;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotQuickButtonResult extends TextBotStringResult implements ITextBotQuickButtonsResult {
    private List<ITextBotQuickButton> quick_replies = new ArrayList<>();


    /**
     * Instantiates a new Text bot button result.
     *
     * @param text the text
     */
    public TextBotQuickButtonResult(String text) {
        super(text);
    }

    /**
     * Instantiates a new Text bot button result.
     */
    public TextBotQuickButtonResult() {
    }

    @Override
    public List<ITextBotQuickButton> getQuick_replies() {
        return quick_replies;
    }

    /**
     * Sets quick replies.
     *
     * @param quick_replies the quick replies
     */
    public void setQuick_replies(List<ITextBotQuickButton> quick_replies) {
        this.quick_replies = quick_replies;
    }


}
