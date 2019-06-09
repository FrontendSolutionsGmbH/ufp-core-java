package com.froso.ufp.modules.optional.sms.exception;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser:Christian Kleinhuis (ck@froso.de)
 * Date: 14.11.13
 * Time: 20:30
 * dispatch sms sending exceptions using this class!
 */
public class SMSSenderException extends RuntimeException {

    /**
     * Constructor SMS sender exception.
     *
     * @param message the message
     */
    public SMSSenderException(String message) {

        super(message);
    }
}
