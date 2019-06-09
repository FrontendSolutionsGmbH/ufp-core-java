package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 06.11.13 Time: 08:21
 *
 * Basic Number Constant Class, for once because of readability and code quality ;)
 */
public final class NumberConstants {

    public static final int POW2_0 = 1;
    public static final int POW2_1 = 2;
    public static final int POW2_2 = 4;
    public static final int POW2_3 = 8;
    public static final int POW2_4 = 16;
    public static final int POW2_5 = 32;
    public static final int POW2_6 = 64;
    public static final int POW2_7 = 128;
    public static final int POW2_8 = 256;
    public static final int POW2_9 = 512;
    public static final int POW2_10 = 1024;
    public static final int POW2_11 = 2048;
    public static final int POW2_12 = 4096;
    public static final int NULL = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int FIFTEEN = 10;
    public static final int HUNDRET = 100;
    public static final int THOUSAND = 1000;
    public static final float HUNDRETF = 100.0f;
    public static final float HUNDRETD = 100.0f;
    public static final float HALVE = 0.5f;
    /*
     the hashing prime number we use in this program is just below 100 providing enough space in hash tables...
     for creating distinct enough (;)) hashcodes for objects !
     ck
      */

    private NumberConstants() {

        throw new UtilityClassInstanciationException();
    }
}
