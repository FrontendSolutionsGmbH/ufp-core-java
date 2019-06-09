package com.froso.ufp.core.exceptions;

/**
 * Created by ckleinhuix on 01.03.2015.
 * <p>
 * used by the config system to expose a initialisation exception
 */
public class RequiredPropertyNotSetException extends UFPRuntimeException {
    /**
     * Constructor Credential exception.
     *
     * @param message the message
     */
    public RequiredPropertyNotSetException(String message) {

        super(message);
    }

    /**
     * Constructor Credential exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RequiredPropertyNotSetException(String message, Throwable cause) {

        super(message, cause);
    }
}
