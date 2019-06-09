package com.froso.ufp.core.domain.events;

import org.junit.*;

public class DataDocumentCRUDEventTest {
    @Test
    public void testForEnum() {
        Assert.assertNotNull(DataDocumentCRUDEvent.valueOf("CREATE"));
        Assert.assertNotNull(DataDocumentCRUDEvent.valueOf("READ"));
        Assert.assertNotNull(DataDocumentCRUDEvent.valueOf("DELETE"));
        Assert.assertNotNull(DataDocumentCRUDEvent.valueOf("UPDATE"));
        Assert.assertNotNull(DataDocumentCRUDEvent.valueOf("POST_UPDATE"));
    }
}
