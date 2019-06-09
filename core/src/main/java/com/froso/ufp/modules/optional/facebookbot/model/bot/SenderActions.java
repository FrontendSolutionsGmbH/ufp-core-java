package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ck on 18.08.2016.
 */
public class SenderActions {
    private IDObject recipient = new IDObject();
    private String sender_action;

    /**
     * Construct typing sender actions.
     *
     * @param recipient the recipient
     * @return the sender actions
     */
    public static SenderActions constructTyping(String recipient) {
        SenderActions result = new SenderActions();

        result.getRecipient().setId(recipient);
        result.setSender_action("typing_on");
        return result;
    }

    /**
     * Construct message seen sender actions.
     *
     * @param recipient the recipient
     * @return the sender actions
     */
    public static SenderActions constructMessageSeen(String recipient) {
        SenderActions result = new SenderActions();

        result.getRecipient().setId(recipient);
        result.setSender_action("mark_seen");
        return result;
    }

    /**
     * Construct typing finished sender actions.
     *
     * @param recipient the recipient
     * @return the sender actions
     */
    public static SenderActions constructTypingFinished(String recipient) {
        SenderActions result = new SenderActions();

        result.getRecipient().setId(recipient);
        result.setSender_action("typing_off");
        return result;
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
     * Gets sender actions.
     *
     * @return the sender actions
     */
    public String getSender_action() {
        return sender_action;
    }

    /**
     * Sets sender actions.
     *
     * @param sender_action the sender actions
     */
    public void setSender_action(String sender_action) {
        this.sender_action = sender_action;
    }
}
