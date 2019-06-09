package com.froso.ufp.core.exceptions;

import com.froso.ufp.core.response.binding.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 16:47 To change
 * this
 * template use File | Settings | File Templates.
 */
public class ValidationException
        extends UFPRuntimeException {

    private final UfpBindingResult bindingResult;

    public ValidationException(String message,
                               UfpBindingResult bindingResultIn) {

        super(message);
        bindingResult = bindingResultIn;
    }

    public UfpBindingResult getBindingResult() {
        return bindingResult;
    }
}
