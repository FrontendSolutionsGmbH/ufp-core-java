package com.froso.ufp.core.response.request;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: ckleinhuix
 * Date: 01.12.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
class RequestException extends RuntimeException {

    /**
     * Constructor Request exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RequestException(String message,
                            Throwable cause) {

        super(message, cause);
    }
}
