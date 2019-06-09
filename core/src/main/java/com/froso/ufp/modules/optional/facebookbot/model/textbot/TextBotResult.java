package com.froso.ufp.modules.optional.facebookbot.model.textbot;

import java.util.*;

/**
 * Created by ck on 18.08.2016.
 */
public class TextBotResult {

    private List<Recipient> recipients = new ArrayList<>();

    /**
     * Gets recipients.
     *
     * @return the recipients
     */
    public List<Recipient> getRecipients() {
        return recipients;
    }

    /**
     * Sets recipients.
     *
     * @param recipients the recipients
     */
    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}
