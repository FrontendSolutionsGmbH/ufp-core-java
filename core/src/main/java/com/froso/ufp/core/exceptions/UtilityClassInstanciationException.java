package com.froso.ufp.core.exceptions;

/**
 * this exception is meant to be thrown from non instanceable classes, e.g. only with static methods/fields
 */
public class UtilityClassInstanciationException extends UFPRuntimeException {

    public UtilityClassInstanciationException() {

        super("Private Class Constructor Called!");
    }
}
