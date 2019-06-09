
package com.froso.ufp.modules.core.user.model;

import org.junit.*;
import org.slf4j.*;

public class CoreUserTest {

    private static final String TEST_ID = "1000";
    private static final String TEST_ID2 = "1001";

    private static final String TEST_PASSWORD = "Test1234";
    private static final String TEST_EMAIL = "test@test.com";

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreUserTest.class);

    /**
     * Test if id behaves correctly.
     */
    @Test
    public void TestIfIdBehavesCorrectly() {

        CoreUser testCoreUser1 = new CoreUser();
        testCoreUser1.setId(TEST_ID);
        Assert.assertEquals(testCoreUser1.getId(), TEST_ID);
        CoreUser coreUser = new CoreUser();
    }

    /**
     * Test if hash code behaves correctly.
     */
    @Test
    public void TestIfHashCodeBehavesCorrectly() {

        CoreUser testCoreUser1 = new CoreUser();
        testCoreUser1.setId(TEST_ID);
        CoreUser testCoreUser2 = new CoreUser();
        testCoreUser2.setId(TEST_ID);
        CoreUser testCoreUser3 = new CoreUser();
        testCoreUser3.setId(null);

        LOGGER.info("HAS CODE EQUALITY hash1 " + testCoreUser1.hashCode());
        LOGGER.info("HAS CODE EQUALITY hash2 " + testCoreUser2.hashCode());
        Assert.assertEquals(testCoreUser1.hashCode(), testCoreUser1.hashCode());
        Assert.assertNotEquals(testCoreUser1.hashCode(), testCoreUser3.hashCode());
    }
}
