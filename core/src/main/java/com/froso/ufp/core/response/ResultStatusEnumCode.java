package com.froso.ufp.core.response;

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
public enum ResultStatusEnumCode implements IResultStatusEnumCode {
    /*
    Enum Members are herewith created using a constructor function defined below, since every
    enum is a java class after all, the ResultStatusEnumCode provides a constructor method below,
    which takes an integer as constructor parameter!
     */

    /**
     * Ok result status enum.
     */
    OK(Constants.CODE_OK),
    /**
     * Ok result status enum code.
     */
    CODE_UNDEFINED(Constants.CODE_UNDEFINED),
    /**
     * Fatal error result status enum.
     */
    FATAL_ERROR(Constants.CODE_FATAL_ERROR),
    /**
     * Not owner result status enum code.
     */
    NOT_OWNER(Constants.CODE_NOT_OWNER),
    /**
     * Error hmac verification failure result status enum.
     */
    ERROR_HMAC_VERIFICATION_FAILURE(Constants.CODE_HMAC_FAILURE),
    /**
     * Error mongodb network result status enum.
     */
    ERROR_MONGODB_NETWORK(Constants.CODE_MONGO_NETWORK),
    /**
     * Error mongodb cursornotfound result status enum.
     */
    ERROR_MONGODB_CURSORNOTFOUND(Constants.CODE_MONGO_CURSORNOTFOUND),
    /**
     * Error mongodb duplicatekey result status enum.
     */
    ERROR_MONGODB_DUPLICATEKEY(Constants.CODE_MONGO_DUPLICATEKEY),
    /**
     * Error resource notavailable result status enum.
     */
    ERROR_RESOURCE_NOTAVAILABLE(1100),
    /**
     * Error resource unmapped request result status enum.
     */
    ERROR_UNMAPPED_REQUEST(1101),
    /**
     * Error user change auth_email invalid input result status enum.
     */
    ERROR_USER_CHANGE_PASSWORD_INVALID_INPUT(5500),
    /**
     * Error create user invalid input result status enum.
     */
    ERROR_CREATE_USER_INVALID_INPUT(5000),
    /**
     * Error create user already existant result status enum.
     */
    ERROR_CREATE_USER_ALREADY_EXISTANT(5001),
    /**
     * Error create user result status enum.
     */
    ERROR_CREATE_USER(5002),
    /**
     * Error update user invalid input result status enum.
     */
    ERROR_UPDATE_USER_INVALID_INPUT(5100),
    /**
     * Error login user auth_email name not valid result status enum.
     */
    ERROR_LOGIN_USER_PASSWORD_NAME_NOT_VALID(5500),
    /**
     * Error user requires activation result status enum.
     */
    ERROR_USER_REQUIRES_ACTIVATION(5501),
    /**
     * Error user is blocked result status enum.
     */
    ERROR_USER_IS_BLOCKED(5502),
    /**
     * Error user not existant result status enum.
     */
    ERROR_USER_NOT_EXISTANT(5503),
    /**
     * Error user has active order result status enum.
     */
    ERROR_USER_HAS_ACTIVE_ORDER(5513),
    /**
     * Error token invalid result status enum.
     */
    ERROR_TOKEN_EXPIRED(5003),
    /**
     * Error token invalid result status enum code.
     */
    ERROR_TOKEN_INVALID(5004),
    /**
     * Error token invalid role result status enum.
     */
    ERROR_TOKEN_INVALID_ROLE(5005),
    /**
     * Error validation result status enum code.
     */

    /**
     * Error appversion notsupported result status enum.
     */
    ERROR_APPVERSION_NOTSUPPORTED(11000),
    /**
     * Error not yet implemented result status enum.
     */
    ERROR_NOT_YET_IMPLEMENTED(666),

    /**
     * Error method not allowed result status enum.
     */
    ERROR_METHOD_NOT_ALLOWED(12000),

    /**
     * Error image creation result status enum.
     */
    ERROR_IMAGE_CREATION(13000),

    /**
     * Error validation result status enum.
     */
    ERROR_VALIDATION(500000),

    /**
     * Error validation result status enum.
     */
    ERROR_NO_DELETE_PRIVILEGE(1500000),
    /**
     * Error no read privilege result status enum code.
     */
    ERROR_NO_READ_PRIVILEGE(1500001),
    /**
     * Error no update privilege result status enum code.
     */
    ERROR_NO_UPDATE_PRIVILEGE(1500002),
    /**
     * Error no create privilege result status enum code.
     */
    ERROR_NO_CREATE_PRIVILEGE(1500003),

    /**
     * The Error java.
     */
// JAVA MAIN EXCEPTIONS THAT GET MAPPED...
    ERROR_JAVA(600000),
    /**
     * Error java invalid argument result status enum.
     */
    ERROR_JAVA_INVALID_ARGUMENT(600001),

    /**
     * Error import result status enum.
     */
    ERROR_IMPORT(700000),

    /**
     * Error export result status enum.
     */
    ERROR_EXPORT(800000);
    /*
                                Static Methods
                                 */
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, ResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (ResultStatusEnumCode s : EnumSet.allOf(ResultStatusEnumCode.class)) {
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

    ResultStatusEnumCode(int code) {

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
    public static ResultStatusEnumCode get(int code) {

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
        public static final int CODE_FATAL_ERROR = -1;
        public static final int CODE_NOT_OWNER = 2001;
        /**
         * The constant CODE_HMAC_FAILURE.
         */
        public static final int CODE_HMAC_FAILURE = -10;
        /**
         * The constant CODE_MONGO_NETWORK.
         */
        public static final int CODE_MONGO_NETWORK = 1001;
        /**
         * The constant CODE_MONGO_CURSORNOTFOUND.
         */
        public static final int CODE_MONGO_CURSORNOTFOUND = 1002;
        /**
         * The constant CODE_MONGO_DUPLICATEKEY.
         */
        public static final int CODE_MONGO_DUPLICATEKEY = 1003;
        /**
         * The constant CODE_CREATE_ORDER_INVALID.
         */
        /**
         * The constant CODE_OK.
         */
        public static final int CODE_OK = 100;
        /**
         * The constant CODE_UNDEFINED.
         */
        public static final int CODE_UNDEFINED = 101;
    }
}
