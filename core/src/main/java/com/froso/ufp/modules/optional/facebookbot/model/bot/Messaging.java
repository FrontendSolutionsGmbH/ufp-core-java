package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class Messaging {
    private IDObject sender;
    private IDObject recipient;
    private String timestamp;
    private Postback postback;
    private Message message = new Message();

    /**
     * Gets postback.
     *
     * @return the postback
     */
    public Postback getPostback() {
        return postback;
    }

    /**
     * Sets postback.
     *
     * @param postback the postback
     */
    public void setPostback(Postback postback) {
        this.postback = postback;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public IDObject getSender() {


        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(IDObject sender) {
        this.sender = sender;
    }

    /**
     * Gets recipient.
     *
     * @return the recipient
     */
    public IDObject getRecipient() {


        return recipient;
    }

    /**
     * Sets recipient.
     *
     * @param recipient the recipient
     */
    public void setRecipient(IDObject recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

}
