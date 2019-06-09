package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;

/**
 * Created by ckleinhuix on 21.02.2015.
 */
public final class NumberUtil {
    public static final Double EPSILON = 0.001d;

    private NumberUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Compare double.
     *
     * @param a the a
     * @param b the b
     * @return the boolean
     */
    public static boolean compareDouble(Double a,
                                        Double b) {

        return Math.abs(a - b) < EPSILON;
    }

    /**
     * Compare float.
     *
     * @param a the a
     * @param b the b
     * @return the boolean
     */
    public static boolean compareFloat(Float a,
                                       Float b) {

        return Math.abs(a - b) < EPSILON;
    }
}
