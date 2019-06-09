package com.froso.ufp.modules.optional.push.model;

import org.junit.*;

public class LanguageEnumTest {

    @Test
    public void testForLanguageEnum() {
        Assert.assertNotNull(LanguageEnum.valueOf("GERMAN"));
        Assert.assertNotNull(LanguageEnum.valueOf("ENGLISH"));
    }
}
