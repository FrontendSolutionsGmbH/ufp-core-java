package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.froso.ufp.core.response.*;
import java.util.*;

public enum SMSResultStatusEnumCode implements IResultStatusEnumCode {
    /*
    Enum Members are herewith created using a constructor function defined below, since every
    enum is a java class after all, the ResultStatusEnumCode provides a constructor method below,
    which takes an integer as constructor parameter!
     */

    /**
     * Ok result status enum.
     */
    OK(ResultStatusEnumCode.Constants.CODE_OK),

    SMS_NONCE_INVALID(Constants.CODE_SMS_NONCE_INVALID),
    SMS_NONCE_EXPIRED(Constants.CODE_SMS_NONCE_EXPIRED),
    SMS_NONCE_NOT_INITIALIZED(Constants.CODE_SMS_NONCE_EXPIRED),
    SMS_AUTHORIZATION_DISABLED(Constants.CODE_SMS_NONCE_DISABLED),

    SMS_PHONENUMBER_UNKNOWN(Constants.UNKNOWN_PHONENUMBER),

    SMS_NUMBER_ALREADY_REGISTERED(Constants.SMS_NUMBER_ALREADY_REGISTERED),
    SMS_INVALID_PHONE_NUMBER(Constants.CODE_SMS_INVALID_PHONE_NUMBER);

    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<Integer, SMSResultStatusEnumCode> LOOKUP = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (SMSResultStatusEnumCode s : EnumSet.allOf(SMSResultStatusEnumCode.class)) {
            LOOKUP.put(s.getCode(), s);
        }
    }

    private final int code;

    SMSResultStatusEnumCode(int code) {

        this.code = code;
    }


    /*
    this method lets us retrieve the Enum Entry for a particular code!
     */
    public static SMSResultStatusEnumCode get(int code) {

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
        public static final int SMS_BASE = 190000;
        public static final int CODE_SMS_NONCE_INVALID = SMS_BASE + 1;
        public static final int CODE_SMS_NONCE_REQUIRED_NONCE_SEND = SMS_BASE + 2;
        public static final int CODE_SMS_INVALID_PHONE_NUMBER = SMS_BASE + 3;
        public static final int CODE_SMS_NONCE_EXPIRED = SMS_BASE + 4;
        public static final int CODE_SMS_NONCE_DISABLED = SMS_BASE + 5;
        public static final int CODE_SMS_NEW_NUMBER_NONCE_SEND = SMS_BASE + 6;
        public static final int UNKNOWN_PHONENUMBER = SMS_BASE + 7;
        public static final int SMS_NUMBER_ALREADY_REGISTERED = SMS_BASE + 8;
        public static final int CODE_SMS_NONCE_NOT_INITIALIZED = SMS_BASE + 9;
    }
}
