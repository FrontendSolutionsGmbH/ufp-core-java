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
@Deprecated

public enum SpringMappedResults implements IResultStatusEnumCode {
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
     * Fatal error result status enum.
     */
    HTTP_MEDIATYPE_NOT_ACCEPTABLE(Constants.HANDLEHTTPMEDIATYPENOTACCEPTABLE),
    /**
     * Http mediatype not supported spring mapped results.
     */
    HTTP_MEDIATYPE_NOT_SUPPORTED(Constants.HANDLEHTTPMEDIATYPENOTSUPPORTED),
    /**
     * Handlebindexception spring mapped results.
     */
    HANDLEBINDEXCEPTION(Constants.HANDLEMISSINGSERVLETREQUESTPART),
    /**
     * No such request handlingmethod spring mapped results.
     */
    NO_SUCH_REQUEST_HANDLINGMETHOD(Constants.HANDLENOSUCHREQUESTHANDLINGMETHOD),
    /**
     * Http prequest method not supported spring mapped results.
     */
    HTTP_PREQUEST_METHOD_NOT_SUPPORTED(Constants.HANDLEHTTPREQUESTMETHODNOTSUPPORTED),
    /**
     * Bind exception spring mapped results.
     */
    BIND_EXCEPTION(Constants.HANDLEMISSINGSERVLETREQUESTPART),
    /**
     * Missing pathvariable spring mapped results.
     */
    MISSING_PATHVARIABLE(Constants.HANDLEMISSINGPATHVARIABLE),
    /**
     * Conversion not supported spring mapped results.
     */
    CONVERSION_NOT_SUPPORTED(Constants.HANDLECONVERSIONNOTSUPPORTED),
    /**
     * Servlet request binding exception spring mapped results.
     */
    SERVLET_REQUEST_BINDING_EXCEPTION(Constants.HANDLESERVLETREQUESTBINDINGEXCEPTION),
    /**
     * Type mismatch spring mapped results.
     */
    TYPE_MISMATCH(Constants.HANDLETYPEMISMATCH),
    /**
     * Http message not writable spring mapped results.
     */
    HTTP_MESSAGE_NOT_WRITABLE(Constants.HANDLEHTTPMESSAGENOTWRITABLE),
    /**
     * Method argument not valid spring mapped results.
     */
    METHOD_ARGUMENT_NOT_VALID(Constants.HANDLEMETHODARGUMENTNOTVALID),
    /**
     * Missing servlet requestpart spring mapped results.
     */
    MISSING_SERVLET_REQUESTPART(Constants.HANDLEMISSINGSERVLETREQUESTPART),
    /**
     * Handlehttpmessagenotreadable spring mapped results.
     */
    HANDLEHTTPMESSAGENOTREADABLE(Constants.HANDLEMISSINGSERVLETREQUESTPART),
    /**
     * Http message not readable exception spring mapped results.
     */
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(Constants.HTTPMESSAGENOTREADABLEEXCEPTION),

    /**
     * Missing servlet request parameter spring mapped results.
     */
    MISSING_SERVLET_REQUEST_PARAMETER(Constants.MISSING_SERVLET_REQUEST_PARAMETER),
    REMOTE_HOST_CHANGED(Constants.REMOTE_HOST_CHANGED);

    /*
                                Static Methods
                                 */
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, SpringMappedResults> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (SpringMappedResults s : EnumSet.allOf(SpringMappedResults.class)) {
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

    SpringMappedResults(int code) {

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
    public static SpringMappedResults get(int code) {

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
        public static final int BASE = 10000;
        /**
         * The constant HTTPMESSAGENOTREADABLEEXCEPTION.
         */
        public static final int HTTPMESSAGENOTREADABLEEXCEPTION = BASE + 1;
        /**
         * The constant CODE_HMAC_FAILURE.
         */
        public static final int HANDLEMISSINGSERVLETREQUESTPART = BASE + 2;
        /**
         * The constant HANDLEMETHODARGUMENTNOTVALID.
         */
        public static final int HANDLEMETHODARGUMENTNOTVALID = BASE + 3;
        /**
         * The constant CODE_MONGO_NETWORK.
         */
        public static final int CODE_MONGO_NETWORK = 1001;
        /**
         * The constant CODE_MONGO_CURSORNOTFOUND.
         */
        public static final int HANDLEHTTPMESSAGENOTWRITABLE = BASE + 4;
        /**
         * The constant HANDLETYPEMISMATCH.
         */
        public static final int HANDLETYPEMISMATCH = BASE + 5;
        /**
         * The constant HANDLECONVERSIONNOTSUPPORTED.
         */
        public static final int HANDLECONVERSIONNOTSUPPORTED = BASE + 6;
        /**
         * The constant HANDLESERVLETREQUESTBINDINGEXCEPTION.
         */
        public static final int HANDLESERVLETREQUESTBINDINGEXCEPTION = BASE + 7;
        /**
         * The constant HANDLEMISSINGPATHVARIABLE.
         */
        public static final int HANDLEMISSINGPATHVARIABLE = BASE + 8;
        /**
         * The constant HANDLEHTTPMEDIATYPENOTACCEPTABLE.
         */
        public static final int HANDLEHTTPMEDIATYPENOTACCEPTABLE = BASE + 9;
        /**
         * The constant HANDLEHTTPMEDIATYPENOTSUPPORTED.
         */
        public static final int HANDLEHTTPMEDIATYPENOTSUPPORTED = BASE + 10;
        /**
         * The constant HANDLEHTTPREQUESTMETHODNOTSUPPORTED.
         */
        public static final int HANDLEHTTPREQUESTMETHODNOTSUPPORTED = BASE + 11;
        /**
         * The constant HANDLENOSUCHREQUESTHANDLINGMETHOD.
         */
        public static final int HANDLENOSUCHREQUESTHANDLINGMETHOD = BASE + 12;
        /**
         * The constant MISSING_SERVLET_REQUEST_PARAMETER.
         */
        public static final int MISSING_SERVLET_REQUEST_PARAMETER = BASE + 13;
        public static final int REMOTE_HOST_CHANGED = BASE + 14;
        /**
         * The constant CODE_MONGO_DUPLICATEKEY.
         */
        public static final int CODE_CREATE_ORDER_INVALID = 10001;
        /**
         * The constant CODE_OK.
         */
        public static final int CODE_OK = 100;
    }
}
