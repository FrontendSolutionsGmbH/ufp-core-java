package com.froso.ufp.modules.optional.push.model;

import org.junit.*;

public class AppDeviceTypeEnumTest {

    @Test
    public void testForAppDeviceTypeEnum() {
        Assert.assertNotNull(AppDeviceTypeEnum.valueOf("IOS"));
        Assert.assertNotNull(AppDeviceTypeEnum.valueOf("ANDROID"));
        Assert.assertNotNull(AppDeviceTypeEnum.valueOf("WINDOWSPHONE"));
        Assert.assertNotNull(AppDeviceTypeEnum.valueOf("WEB"));
        Assert.assertNotNull(AppDeviceTypeEnum.valueOf("WINDOWS"));
    }
}