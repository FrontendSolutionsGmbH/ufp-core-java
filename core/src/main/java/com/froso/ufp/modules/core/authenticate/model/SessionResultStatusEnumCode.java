package com.froso.ufp.modules.core.authenticate.model;

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

public enum SessionResultStatusEnumCode implements IResultStatusEnumCode {
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
     * Error token invalid result status enum.
     */
    ERROR_TOKEN_EXPIRED(Constants.CODE_ERROR_TOKEN_EXPIRED),
    /**
     * Error token invalid result status enum code.
     */
    ERROR_TOKEN_INVALID(Constants.CODE_ERROR_TOKEN_INVALID),
    /**
     * Error token invalid role result status enum.
     */
    ERROR_TOKEN_INVALID_ROLE(Constants.CODE_ERROR_TOKEN_INVALID_ROLE);
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, SessionResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (SessionResultStatusEnumCode s : EnumSet.allOf(SessionResultStatusEnumCode.class)) {
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
    SessionResultStatusEnumCode(int code) {

        this.code = code;
    }

    /**
     * this method lets us retrieve the Enum Entry for a particular code!
     *
     * @param code the code
     * @return the result status enum
     */
    public static SessionResultStatusEnumCode get(int code) {

        return LOOKUP.get(code);
    }

    /**
     * fields belonging to particular instances, or how should i name it ?
     * and for every enumeration that is concretely used, one is allowed to call this getcode function to map the
     * statusenum-string to a value!
     *
     * @return code code
     */
    public final int getCode() {
        return code;
    }


    public static class Constants {
        public static final int CODE_HMAC_FAILURE = -10;
        public static final int CODE_ERROR_TOKEN_EXPIRED = 5003;
        public static final int CODE_ERROR_TOKEN_INVALID = 5004;
        public static final int CODE_ERROR_TOKEN_INVALID_ROLE = 5005;
    }
}
