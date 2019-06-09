package com.froso.ufp.testutil;

import com.froso.ufp.core.util.*;
import org.junit.*;
import org.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

public class TestUtil {

    // private static final String TEST_NON_EXISTING_ID = "non_existing_id";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);
    //Make the Test String a HEXADECIMAL VALUE! because the designsettings expect an hexadecimal string, and other
    // setters/getters should be unaffected by this "special" string
    private static final String STRING_TEST_STRING = "0xFF00FF";
    private static final Boolean BOOLEAN_TEST_VALUE1 = true;
    private static final Boolean BOOLEAN_TEST_VALUE2 = true;
    private static final Integer INT_TEST_VALUE = 100;
    private static final float FLOAT_TEST_VALUE = 0.5f;

    private TestUtil() {
    }

    /**
     * helper function for calling the enrich feature on the result of a getter method
     *
     * @param object the object
     */
    public static void checkGettersAndSettersDeprecated(Object object) {
        // Include To String Test
        Assert.assertNotNull(object.toString());

        // Nun gehen wir noch jedes kindobject durch und erweitern es um die resources.... ja, so sieht es aus!
        // Some introspection:
        LOGGER.info("Testing " + object.getClass().getName());
        List<Method> gettersAndSetters = IntrospectUtil.findGettersSetters(object.getClass());
        // We want to check that the input type of the setter is the same as the getOutput type of the getters!
        for (Method method : gettersAndSetters) {
            if (IntrospectUtil.isSetter(method)) {
                // Extract the name of the property
                if ((method.getParameterTypes()[0].equals(boolean.class)) || (method.getParameterTypes()[0].equals(Boolean.class))) {
                    // a Boolean "is" setter method
                    // Extract the name
                    String propertyName = getMethodNameWithoutGetOrSet(method);
                    LOGGER.info(object.getClass().getName() + ": Booplean Property Name is" + propertyName);
                    Method getterMethod = findGetter(propertyName, gettersAndSetters);
                    if (null != getterMethod) {
                        // Nasty check, to prevent problems with boolean "is" getters (when using beanUtils
                        // .copyProperties)
                        // we throw an error if getter begins with "is"
                        Assert.assertNotEquals("Boolean Property getters have to start with 'get' instead of 'is' found in Class: " +
                                object.getClass().getName() + " Property was:" + propertyName, getterMethod.getName().indexOf("is"), 0);
                        // Ok, now we can compare the types of the getter and setters
                        testSetterParameterEqualsGetterReturnType(getterMethod, method);
                        //Assert.assertEquals(getterMethod.getReturnType(), method.getParameterTypes()[0]);
                        testSetterAndGetterEquality(object, getterMethod, method);
                    }
                } else {
                    // a "normal" getter method
                    // Extract the name
                    String propertyName = getMethodNameWithoutGetOrSet(method);
                    LOGGER.info("Property Name is " + propertyName);
                    Method getterMethod = findGetter(propertyName, gettersAndSetters);
                    if (null != getterMethod) {
                        // Ok, now we can compare the types of the getter and setters
                        testSetterParameterEqualsGetterReturnType(getterMethod, method);
                        //Assert.assertEquals(getterMethod.getReturnType(), method.getParameterTypes()[0]);
                        testSetterAndGetterEquality(object, getterMethod, method);
                    }
                }
            }
        }
    }

    private static String getMethodNameWithoutGetOrSet(Method method) {
        return method.getName().substring(3);
    }

    private static Method findGetter(String propertyName, List<Method> methods) {

        for (Method method : methods) {
            if (("get" + propertyName).equals(method.getName())) {
                return method;
            }
            if (("is" + propertyName).equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    private static void testSetterParameterEqualsGetterReturnType(Method getterMethod, Method setterMethod) {
        try {
            Assert.assertEquals(getterMethod.getReturnType(), setterMethod.getParameterTypes()[0]);

        } catch (Exception e) {
            // hmm, an error occured :(
            LOGGER.error("An Error Occured while testing:" + e.getMessage(), e);
            Assert.fail(setterMethod.getName() + "/" + getterMethod.getName() + " Failed");
        }
    }

    private static void testSetterAndGetterEquality(Object owner, Method getterMethod, Method setterMethod) {

        try {
            setterMethod.setAccessible(true);
            getterMethod.setAccessible(true);
            if (getterMethod.getReturnType().equals(String.class)) {
                // If String we perform a simple set/get with a string...
                setterMethod.invoke(owner, STRING_TEST_STRING);
                Assert.assertEquals(getterMethod.invoke(owner), STRING_TEST_STRING);
            } else if (getterMethod.getReturnType().equals(Boolean.class)) {
                // If String we perform a simple set/get with a boolean...
                setterMethod.invoke(owner, BOOLEAN_TEST_VALUE1);
                Assert.assertEquals(getterMethod.invoke(owner), BOOLEAN_TEST_VALUE1);
                setterMethod.invoke(owner, BOOLEAN_TEST_VALUE2);
                Assert.assertEquals(getterMethod.invoke(owner), BOOLEAN_TEST_VALUE2);
            } else if (getterMethod.getReturnType().equals(Integer.class)) {
                // If String we perform a simple set/get with a string...
                setterMethod.invoke(owner, INT_TEST_VALUE);
                Assert.assertEquals(getterMethod.invoke(owner), INT_TEST_VALUE);
            } else if (getterMethod.getReturnType().equals(Float.class)) {
                // If String we perform a simple set/get with a string...
                setterMethod.invoke(owner, FLOAT_TEST_VALUE);
                Assert.assertEquals(getterMethod.invoke(owner), FLOAT_TEST_VALUE);
            } else {
                // If String we perform a simple set/get with a string...
                Object result = getterMethod.invoke(owner);
                setterMethod.invoke(owner, getterMethod.invoke(owner));
                Assert.assertEquals(getterMethod.invoke(owner), result);
            }
        } catch (Exception e) {
            // hmm, an error occured :(
            LOGGER.error("An Error Occured while testing:" + e.getMessage(), e);
            Assert.fail(setterMethod.getName() + "/" + getterMethod.getName() + " Failed");
        }
    }
}
