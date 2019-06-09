package com.froso.ufp.core.exceptions;

/**
 * Created by ckleinhuix on 09.12.13.
 * <p>
 * general exception for any service exception...
 */
public class ServiceException extends UFPRuntimeException {

    /**
     * Constructor Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {

        super(message);
    }

    /**
     * Constructor Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {

        super(message, cause);
    }
}
