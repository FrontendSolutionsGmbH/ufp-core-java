package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.response.*;
import java.util.*;

public enum AuthenticateManagerResultStatusEnumCode implements IResultStatusEnumCode {
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
    AUTHORIZATION_INVALID(Constants.AUTH_INVALID),

    NEW_EQUALS_OLD(Constants.NEW_EQUALS_OLD);

    /*
                                Static Methods
                                 */
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, AuthenticateManagerResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (AuthenticateManagerResultStatusEnumCode s : EnumSet.allOf(AuthenticateManagerResultStatusEnumCode.class)) {
            LOOKUP.put(s.getCode(), s);
        }
    }

    private final int code;


    AuthenticateManagerResultStatusEnumCode(int code) {

        this.code = code;
    }

    /*
    this method lets us retrieve the Enum Entry for a particular code!
     */
    public static AuthenticateManagerResultStatusEnumCode get(int code) {

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
        public static final int BASE = 15000;
        public static final int AUTH_INVALID = BASE + 1;
        public static final int NEW_EQUALS_OLD = BASE + 2;
        public static final int CODE_HMAC_FAILURE = -10;
        public static final int CODE_MONGO_NETWORK = 1001;
        public static final int CODE_MONGO_CURSORNOTFOUND = 1002;
        public static final int CODE_MONGO_DUPLICATEKEY = 1003;
        public static final int CODE_CREATE_ORDER_INVALID = 10001;
        public static final int CODE_OK = 100;
    }
}
