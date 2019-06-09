package com.froso.ufp.core.exceptions;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 16:47 To change
 * this
 * template use File | Settings | File Templates.
 */
public class ImageGenerationException
        extends UFPRuntimeException {


    /**
     * Constructor Resource exception.
     *
     * @param message the message
     */
    public ImageGenerationException(String message) {

        super(message);

    }

    /**
     * Instantiates a new Image generation exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ImageGenerationException(String message, Throwable cause) {

        super(message, cause);

    }

}
