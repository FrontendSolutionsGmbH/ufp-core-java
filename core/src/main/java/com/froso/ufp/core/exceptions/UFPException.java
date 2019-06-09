package com.froso.ufp.core.exceptions;

/**
 * Created with IntelliJ IDEA. SimpleUser: ckleinhuix Date: 01.12.13 Time: 14:00 To change this template use File |
 * Settings |
 * File Templates.
 */
public class UFPException extends Exception {


    /**
     * Constructor Resource not found exception.
     *
     * @param message the message
     */
    public UFPException(String message) {

        super(message);
    }

    /**
     * Instantiates a new Ufp exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UFPException(String message, Throwable cause) {

        super(message, cause);
    }

}
