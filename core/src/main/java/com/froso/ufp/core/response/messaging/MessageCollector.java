package com.froso.ufp.core.response.messaging;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser:Christian Kleinhuis (ck@froso.de)
 * Date: 30.10.13
 * Time: 21:08
 * Singleton for collection messages ...  main purpose is for outputting messages in development mode,
 * to be invoked by all classes that want to getOutput something... works like a console...
 */
public final class MessageCollector {

    private List<UFPResponseMessage> messages;

    /**
     * Constructor Message collector.
     */
    public MessageCollector() {

        messages = new ArrayList<>();
    }

    /**
     * concrete message saving, the "_" is viable here because the public interface
     * shall be convenient, the internal handling is the internal problem of this class!
     *
     * @param message the message
     */
    public void addMessage(String message) {

        messages.add(new UFPResponseMessage(message));
    }

    /**
     * Add message error.
     *
     * @param message the message
     */
    public void addMessageError(String message) {

        messages.add(new UFPResponseMessage(message, MessageType.ERROR));
    }

    /**
     * Add message warning.
     *
     * @param message the message
     */
    public void addMessageWarning(String message) {

        messages.add(new UFPResponseMessage(message, MessageType.WARNING));
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<UFPResponseMessage> getMessages() {

        return messages;
    }

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<UFPResponseMessage> messages) {

        this.messages = messages;
    }
}
