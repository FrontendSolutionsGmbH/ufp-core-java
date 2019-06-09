package com.froso.ufp.modules.core.worker.model;

import com.froso.ufp.core.response.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de)leinhuis (ck@froso.de) Date: 01.11.13 Time:
 * 12:32
 *
 * Wow, what a nasty Enumeration, with included mapping to numbers, method found on stackoverflow, and is by far the
 * most suitable for the situation, because we want to classify statusses of the application by concrete numbers, so,
 * here is a reference on the retrieval of it
 *
 * source: http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 *
 * i took myself the time to document on it!
 *
 * ach du lieber jott, vielleicht sollte dies eine basisklasse für alle von dieser anwendung genutzten enumerationen
 * werden ? naja, mal drüber nachdenken ...
 */

public enum UFPWorkerErrorEnumCode implements IResultStatusEnumCode {
    /*
    Enum Members are herewith created using a constructor function defined below, since every
    enum is a java class after all, the ResultStatusEnumCode provides a constructor method below,
    which takes an integer as constructor parameter!
     */

    /**
     * Ok result status enum.
     */
    OK(Constants.UFP_ERROR_OK),
    /**
     * Fatal error result status enum.
     */
    UFP_WORKER_CANT_BE_CREATED(Constants.UFP_WORKER_CANT_BE_CREATED),
    UFP_WORKER_CANT_BE_DELETED(Constants.UFP_WORKER_CANT_BE_DELETED);
    /*
                                Static Methods
                                 */
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, UFPWorkerErrorEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (UFPWorkerErrorEnumCode s : EnumSet.allOf(UFPWorkerErrorEnumCode.class)) {
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

    UFPWorkerErrorEnumCode(int code) {

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
    public static UFPWorkerErrorEnumCode get(int code) {

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

    public static class Constants {
        /**
         * The constant HTTP_MESSAGE_NOT_READABLE_EXCEPTION.
         */
        public static final int UFP_ERROR_BASE = 800000;
        public static final int UFP_WORKER_CANT_BE_CREATED = UFP_ERROR_BASE + 1;
        public static final int UFP_WORKER_CANT_BE_DELETED = UFP_ERROR_BASE + 2;

        public static final int UFP_ERROR_OK = 100;
    }
}
