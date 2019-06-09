package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;

import java.security.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 30.10.13 Time: 15:48 To change this
 * template use File | Settings | File Templates.
 */
public final class RandGen {

    private static Random randomGenerator = null;
    private static Integer lastInt;
    private static String alphabet = "abcdefghijklmnopqrst";

    private RandGen() {

        throw new UtilityClassInstanciationException();
    }

    /**
     * Next int.
     *
     * @param range the range
     * @return the int
     */
    public static int nextInt(int range) {
        if (range <= 0) {
            return 0;
        }
        lastInt = getRandom().nextInt(range);
        return lastInt;
    }

    /**
     * Random enum t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    private static Random getRandom() {

        if (null == randomGenerator) {
            createRandom();
        }
        return randomGenerator;
    }

    private static void createRandom() {

        randomGenerator = new SecureRandom();
    }

    /**
     * Next char.
     *
     * @return the character
     */
    public static Character nextChar() {

        return alphabet.charAt(getRandom().nextInt(alphabet.length()));
    }

    /**
     * Gets llast inc.
     *
     * @return the llast inc
     */
    public static int getLlastInc() {

        return lastInt;
    }

    /**
     * Next int.
     *
     * @return the int
     */
    public static Integer nextInt() {

        RandGen.lastInt = getRandom().nextInt();
        return lastInt;
    }

    /**
     * Next float.
     *
     * @param range the range
     * @return the float
     */
    public static float nextFloat(float range) {

        return (float) ((double) getRandom().nextFloat() * (double) range);
    }

    /**
     * Next float.
     *
     * @return the float
     */
    public static float nextFloat() {

        return getRandom().nextFloat();
    }

    /**
     * Gets random uUID.
     *
     * @return the random uUID
     */
    public static String getRandomUUID() {

        return UUID.randomUUID().toString();
    }
}
