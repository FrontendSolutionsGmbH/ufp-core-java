package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import com.google.common.io.*;

/**
 * Created by ckleinhuix on 09.03.14.
 */
final class Base64Util {
    private Base64Util() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Base 32 decode.
     *
     * @param input the input
     * @return the byte [ ]
     */
    public static byte[] base32Decode(String input) {

        return BaseEncoding.base64Url().omitPadding().decode(input);
    }

    /**
     * Base 32 encode.
     *
     * @param input the input
     * @return the string
     */
    public static String base32Encode(byte[] input) {

        return BaseEncoding.base64Url().omitPadding().encode(input);
    }
}
