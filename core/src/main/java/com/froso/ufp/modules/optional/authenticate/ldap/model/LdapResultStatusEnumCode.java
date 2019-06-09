package com.froso.ufp.modules.optional.authenticate.ldap.model;

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
public enum LdapResultStatusEnumCode implements IResultStatusEnumCode {
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
    UsernamePassword_NONCE_INVALID(Constants.CODE_UsernamePassword_NONCE_INVALID),
    UsernamePassword_NONCE_EXPIRED(Constants.CODE_UsernamePassword_NONCE_EXPIRED),
    UsernamePassword_AUTHORIZATION_DISABLED(Constants.CODE_UsernamePassword_NONCE_DISABLED),

    /**
     * UsernamePassword nonce required nonce send UsernamePassword result status enum.
     */
    UsernamePassword_NONCE_REQUIRED_NONCE_SEND(Constants.CODE_UsernamePassword_NONCE_REQUIRED_NONCE_SEND),
    UsernamePassword_NEW_NUMBER_NONCE_SEND(Constants.CODE_UsernamePassword_NEW_NUMBER_NONCE_SEND),

    /**
     * UsernamePassword invalid phone number UsernamePassword result status enum.
     */
    UsernamePassword_NUMBER_ALREADY_REGISTERED(Constants.UsernamePassword_NUMBER_ALREADY_REGISTERED),
    UsernamePassword_INVALID_ACCESS(Constants.CODE_UsernamePassword_INVALID_ACCESS);

    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, LdapResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (LdapResultStatusEnumCode s : EnumSet.allOf(LdapResultStatusEnumCode.class)) {
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

    LdapResultStatusEnumCode(int code) {

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
    public static LdapResultStatusEnumCode get(int code) {

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
        public static final int UsernamePassword_BASE = 210000;
        /**
         * The constant MASTER_PASSWORD_INVALID.
         */
        public static final int CODE_UsernamePassword_NONCE_INVALID = UsernamePassword_BASE + 1;
        /**
         * The constant CODE_UsernamePassword_NONCE_REQUIRED_NONCE_SEND.
         */
        public static final int CODE_UsernamePassword_NONCE_REQUIRED_NONCE_SEND = UsernamePassword_BASE + 2;
        /**
         * The constant MASTER_PASSWORD_NOT_FOUND.
         */
        public static final int CODE_UsernamePassword_INVALID_ACCESS = UsernamePassword_BASE + 3;
        public static final int CODE_UsernamePassword_NONCE_EXPIRED = UsernamePassword_BASE + 4;
        public static final int CODE_UsernamePassword_NONCE_DISABLED = UsernamePassword_BASE + 5;
        public static final int CODE_UsernamePassword_NEW_NUMBER_NONCE_SEND = UsernamePassword_BASE + 6;
        public static final int UNKNOWN_PHONENUMBER = UsernamePassword_BASE + 7;
        public static final int UsernamePassword_NUMBER_ALREADY_REGISTERED = UsernamePassword_BASE + 8;
    }
}
