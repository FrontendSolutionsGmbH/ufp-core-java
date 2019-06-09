package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.util.*;

/**
 * Created by ckleinhuix on 02.12.2014.
 */
public final class StringUtil {

    private StringUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * String in list.
     *
     * @param search the search
     * @param list   the list
     * @return the boolean
     */
    public static boolean stringInList(String search,
                                       List<String> list) {

        if (null != list && search != null) {
            for (String string : list) {
                if (string.equals(search)) {
                    return true;
                }
            }
        }
        return false;
    }
}
