package com.froso.ufp.modules.core.user.model;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * CoreUser: ck
 * Date: 20.02.14
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public enum SalutationEnum {


    /*
    Enum Members are herewith created using a constructor function defined below, since every
    enum is a java class after all, the ResultStatusEnumCode provides a constructor method below,
    which takes an integer as constructor parameter!
     */

    MALE(1, "Herr"),
    FEMALE(2, "Frau"),
    COMPANY(5, "Firma");

    /*
    Static Methods
     */
    /*
    the lookup variable uses a Map<> object for referencing status codes to enum values
     */
    private static final Map<String, SalutationEnum> LOOKUPVALUE = new HashMap<>();
    private static final Map<Integer, SalutationEnum> LOOKUPKEY = new HashMap<>();

    /*
    this seldom used java constructTyping of placing executable code right inside the class definition
    serves the purpose to actually create the useable hashmap of the existing enums, since the
    enum field is initialised above, and the static lookup is created already here is a good
    place to initialise this lookup table through the static executed block of code ;)
     */
    static {
        for (SalutationEnum s : EnumSet.allOf(SalutationEnum.class)) {
            LOOKUPKEY.put(s.getKey(), s);
            LOOKUPVALUE.put(s.getValue(), s);
        }
    }

    /**
     * the status code int value
     */
    private final String value;
    private final Integer key;

    /**
     * and here comes the constructor which is called with an int value
     *
     * @param keyIn   the key in
     * @param valueIn the value in
     */

    SalutationEnum(Integer keyIn,
                   String valueIn) {

        this.value = valueIn;
        this.key = keyIn;
    }

    /**
     * Gets by key.
     *
     * @param code the code
     * @return the by key
     */
/*
    this method lets us retrieve the Enum Entry for a particular code!
     */
    public static SalutationEnum getByKey(Integer code) {

        return LOOKUPKEY.get(code);
    }

    /**
     * Gets by value.
     *
     * @param code the code
     * @return the by value
     */
    public static SalutationEnum getByValue(String code) {

        return LOOKUPVALUE.get(code);
    }
    /**
     * fields belonging to particular instances, or how should i name it ? haha
     */

    /**
     * and for every enumeration that is concretely used, one is allowed to call this getcode function to map the
     * statusenum-string to a value!
     *
     * @return value value
     */
    String getValue() {

        return value;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public Integer getKey() {

        return key;
    }
}
