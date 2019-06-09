package com.froso.ufp.core.util;

import org.junit.*;

import java.lang.reflect.*;
import java.util.*;

public class IntrospectUtilTest {

    private static final String EXAMPLE_STRING = "EXAMPLE_STRING";

    @Test
    public void testForIntrospectUtil() {

        TestData testData = new TestData();
        testData.setString(EXAMPLE_STRING);
        testData.setStringWithReturnType("test");
        testData.getStringWithParam(5);
        Assert.assertEquals(EXAMPLE_STRING, testData.getString());

        List<Method> gettersAndSetters = IntrospectUtil.findGettersSetters(testData.getClass());
        Assert.assertEquals(gettersAndSetters.size(), 2);

    }

    private class TestData {

        private String privateStringValue;

        public TestData() {
        }

        public String getString() {
            return privateStringValue;
        }

        public void setString(String stringValue) {

            this.privateStringValue = stringValue;
        }

        public String getStringWithParam(int value) {
            return privateStringValue;
        }

        public int setStringWithReturnType(String stringValue) {

            return 2;
        }
    }
}
