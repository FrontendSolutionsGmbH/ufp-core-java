package com.froso.ufp.modules.optional.push.model;

import org.junit.*;

public class CreateOrUpdateEnumTest {

    @Test
    public void testForCreateOrUpdateEnum() {
        Assert.assertNotNull(CreateOrUpdateEnum.valueOf("CREATED"));
        Assert.assertNotNull(CreateOrUpdateEnum.valueOf("UPDATED"));
    }
}
