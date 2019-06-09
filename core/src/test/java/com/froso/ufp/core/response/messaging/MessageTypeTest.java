package com.froso.ufp.core.response.messaging;

import org.junit.*;

public class MessageTypeTest {
    @Test
    public void testForEnum() {
        Assert.assertNotNull(MessageType.valueOf("LOG"));
        Assert.assertNotNull(MessageType.valueOf("WARNING"));
        Assert.assertNotNull(MessageType.valueOf("ERROR"));
    }
}