package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ck on 16.08.2016.
 */
public class PostbackData {

    private IDObject sender;
    private IDObject recipient;
    private Postback postback;

    public IDObject getSender() {
        return sender;
    }

    public void setSender(IDObject sender) {
        this.sender = sender;
    }

    public IDObject getRecipient() {
        return recipient;
    }

    public void setRecipient(IDObject recipient) {
        this.recipient = recipient;
    }

    public Postback getPostback() {
        return postback;
    }

    public void setPostback(Postback postback) {
        this.postback = postback;
    }
}
