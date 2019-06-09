package com.froso.ufp.modules.optional.push.model;

import com.froso.ufp.modules.core.user.model.*;
import org.junit.*;

public class AppDeviceTest {

    @Test
    public void testAppDevice() {

        CoreUser coreUser = new CoreUser();
        AppDevice appDevice = new AppDevice();
        appDevice.getCoreUser().setId(coreUser.getId());
    }

}
