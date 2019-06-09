package com.froso.ufp.modules.optional.media.model;

import org.junit.*;

public class PictureTypeEnumTest {
    @Test
    public void testForPictureTypeEnum() {
        Assert.assertNotNull(PictureTypeEnum.valueOf("MEDIA_HOST"));
        Assert.assertNotNull(PictureTypeEnum.valueOf("RESOURCE"));
        Assert.assertNotNull(PictureTypeEnum.valueOf("URL"));
        Assert.assertNotNull(PictureTypeEnum.valueOf("MEDIA_HOST_REIMPORT"));
        Assert.assertNotNull(PictureTypeEnum.valueOf("PROTECTED_MEDIA_HOST"));
    }
}
