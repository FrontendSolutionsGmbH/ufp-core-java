package com.froso.ufp.core;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.util.*;
import org.junit.*;

import java.lang.reflect.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 04.12.13 Time: 10:24
 * <p>
 * although it seems awkward, but not only for achieving a higher testing coverage, as well for testing the
 * implementations, a utility class should not be able to be instanciated not even using introspection and setting
 * accessor to public this is done here, we demand that any utility class shall throw an error when it is tried to be
 * instanciated
 */
public class UtilityClassesThrowErrorOnCreationTest {

    /**
     * Test for introspect util instanciation.
     */
    @Test(expected = UtilityClassInstanciationException.class)
    public void testForIntrospectUtilInstanciation() {

        testForPrivateConstructor(IntrospectUtil.class);
    }

    private void testForPrivateConstructor(Class classToTest) {

        try {
            Constructor constructor = classToTest.getDeclaredConstructor();
            // Verify that constructor is private
            Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (NoSuchMethodException ignored) {
            Assert.assertFalse("No Declared Constructor", true);
        } catch (IllegalAccessException ignored) {
            Assert.assertFalse("IllegalAccessException ", true);
        } catch (InvocationTargetException exception) {
            // Check if the Utility class exception is thrown
            Assert.assertTrue(exception.getTargetException() instanceof UtilityClassInstanciationException);
            if (exception.getTargetException() instanceof UtilityClassInstanciationException) {
                throw new UtilityClassInstanciationException();
            }
        } catch (InstantiationException ignored) {
            Assert.assertFalse("InstantiationException", true);
        } catch (Exception exception) {
            Assert.assertFalse("Exception occured:" + exception.getMessage(), true);
        }
    }

    /**
     * Test for randgen instanciation.
     */
    @Test(expected = UtilityClassInstanciationException.class)
    public void testForRandgenInstanciation() {

        testForPrivateConstructor(RandGen.class);
    }

    /**
     * Test for number constants instanciation.
     */
    @Test(expected = UtilityClassInstanciationException.class)
    public void testForNumberConstantsInstanciation() {

        testForPrivateConstructor(NumberConstants.class);
    }

    /**
     * Test parallel.
     */
    @Test(expected = UtilityClassInstanciationException.class)
    public void testParallel() {

        testForPrivateConstructor(Parallel.class);
    }
}
