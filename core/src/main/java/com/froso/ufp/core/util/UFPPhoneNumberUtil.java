package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;

/**
 * Created by ckleinhuix on 15.03.14.
 */
public final class UFPPhoneNumberUtil {
    private static final String GERMANY = "+49";

    private UFPPhoneNumberUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    public static String phoneNumberRemoveNonNumbers(String phoneNumber) {

        String result = phoneNumber;
        if (null != phoneNumber) {
            result = phoneNumber.replaceAll("[^0-9+]", "");


        }
        return result;
    }
}
