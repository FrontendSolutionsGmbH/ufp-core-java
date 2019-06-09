package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 29.11.13 Time: 19:47 utility
 */
public final class IntrospectUtil {

    private IntrospectUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Find getters setters.
     *
     * @param c the c
     * @return the list
     */
    public static List<Method> findGettersSetters(Class<?> c) {
        List<Method> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method) || isSetter(method)) {
                list.add(method);
            }
        }
        return list;
    }

    /**
     * Is getter.
     *
     * @param method the method
     * @return the boolean
     */
    public static boolean isGetter(Method method) {

        boolean result = false;
        // check if it is a public method and has exactly zero paramerters
        if (isPublicAndHasZeroParameters(method)) {
            // test if begins with "get" standard semantic
            if (beginsWithGetAndHasNonVoidReturnType(method)) {
                result = true;
            }
            // Test for Boolean semantic
            if (beginsWithIsAndReturnTypeIsBoolean(method)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Is setter.
     *
     * @param method the method
     * @return the boolean
     */
    public static boolean isSetter(Method method) {

        return Modifier.isPublic(method.getModifiers()) &&
                method.getReturnType().equals(void.class) &&
                (1 == method.getParameterTypes().length) &&
                method.getName().matches("^set[A-Z].*");
    }

    private static boolean isPublicAndHasZeroParameters(Method method) {

        return Modifier.isPublic(method.getModifiers()) && (0 == method.getParameterTypes().length);
    }

    private static boolean beginsWithGetAndHasNonVoidReturnType(Method method) {

        return method.getName().matches("^get[A-Z].*") && !method.getReturnType().equals(void.class);
    }

    private static boolean beginsWithIsAndReturnTypeIsBoolean(Method method) {

        return method.getName().matches("^is[A-Z].*") && method.getReturnType().equals(boolean.class);
    }

    /**
     * Find setters.
     *
     * @param c the c
     * @return the list
     */
    public static List<Method> findSetters(Class<?> c) {

        List<Method> list = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (isSetter(method)) {
                list.add(method);
            }
        }
        return list;
    }
}
