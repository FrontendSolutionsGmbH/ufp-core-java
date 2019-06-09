package com.froso.ufp.modules.optional.authenticate.email.model;

import com.froso.ufp.core.response.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de)leinhuis (ck@froso.de) Date: 01.11.13 Time:
 * 12:32
 * <p>
 * Wow, what a nasty Enumeration, with included mapping to numbers, method found on stackoverflow, and is by far the
 * most suitable for the situation, because we want to classify statusses of the application by concrete numbers, so,
 * here is a reference on the retrieval of it
 * <p>
 * source: http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 * <p>
 * i took myself the time to document on it!
 * <p>
 * ach du lieber jott, vielleicht sollte dies eine basisklasse für alle von dieser anwendung genutzten enumerationen
 * werden ? naja, mal drüber nachdenken ...
 */
public enum EmailAuthResultStatusEnumCode implements IResultStatusEnumCode {
    /*
    Enum Members are herewith created using a constructor function defined below, since every
    enum is a java class after all, the ResultStatusEnumCode provides a constructor method below,
    which takes an integer as constructor parameter!
     */

    /**
     * Ok result status enum.
     */
    OK(ResultStatusEnumCode.Constants.CODE_OK),
    /**
     * Fatal error result status enum.
     */
    INVALID_USERNAME_PASSWORD_COMBINATION(Constants.INVALID_PW),
    NEW_USER_CREATED_VALIDATION_EMAIL_SEND(Constants.NEW_USER_VALIDATION_MAIL_SEND),
    NEW_NONCE_CREATED_EMAIL_SEND(Constants.NEW_NONCE_CREATED_EMAIL_SEND),
    USERNAME_OR_EMAIL_ALREADY_REGISTERED(Constants.USERNAME_OR_EMAIL_ALREADY_REGISTERED),
    INVALID_NONCE(Constants.INVALID_NONCE),
    AUTHORIZATION_NOT_FOUND(Constants.AUTHORIZATION_NOT_FOUND),
    INVALID_EMAIL(Constants.NEW_USER_VALIDATION_MAIL_SEND),
    EMAIL_NOT_VALIDATED(Constants.EMAILUNVERIFIED);


    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, EmailAuthResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (EmailAuthResultStatusEnumCode s : EnumSet.allOf(EmailAuthResultStatusEnumCode.class)) {
            LOOKUP.put(s.getCode(), s);
        }
    }

    /**
     * the status code int value
     */
    private final int code;

    /**
     * and here comes the constructor which is called with an int value
     *
     * @param code the code
     */

    EmailAuthResultStatusEnumCode(int code) {

        this.code = code;
    }

    /**
     * Get result status enum.
     *
     * @param code the code
     * @return the result status enum
     */
/*
    this method lets us retrieve the Enum Entry for a particular code!
     */
    public static EmailAuthResultStatusEnumCode get(int code) {

        return LOOKUP.get(code);
    }
    /**
     * fields belonging to particular instances, or how should i name it ? haha
     */

    /**
     * and for every enumeration that is concretely used, one is allowed to call this getcode function to map the
     * statusenum-string to a value!
     *
     * @return code code
     */
    public final int getCode() {

        return code;
    }

    /**
     * The type Constants.
     */
    public static class Constants {
        /**
         * The constant HTTP_MESSAGE_NOT_READABLE_EXCEPTION.
         */
        /**
         * The constant CODE_HMAC_FAILURE.
         */
        public static final int SMS_BASE = 180000;
        /**
         * The constant MASTER_PASSWORD_INVALID.
         */
        public static final int INVALID_PW = SMS_BASE + 1;
        /**
         * The constant CODE_SMS_NONCE_REQUIRED_NONCE_SEND.
         */
        public static final int EMAILUNVERIFIED = SMS_BASE + 2;
        /**
         * The constant MASTER_PASSWORD_NOT_FOUND.
         */
        public static final int NEW_USER_VALIDATION_MAIL_SEND = SMS_BASE + 3;
        public static final int USERNAME_OR_EMAIL_ALREADY_REGISTERED = SMS_BASE + 4;
        public static final int INVALID_NONCE = SMS_BASE + 5;
        public static final int AUTHORIZATION_NOT_FOUND = SMS_BASE + 6;
        public static final int NEW_NONCE_CREATED_EMAIL_SEND = SMS_BASE + 7;
    }
}
