package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.model.push.*;
import java.util.*;

/**
 * Created by alex on 14.03.14.
 */
interface PushSender {

    /**
     * Send push messages and get inactive device tokens.
     *
     * @param message              the message
     * @param totalInactiveDevices the total inactive devices
     * @param devicesToUpdate      the devices to update
     * @param deviceList           the device list
     * @param useProduction        the use production
     * @return the int
     */
    int sendPushMessagesAndGetInactiveDeviceTokens(AbstractBasePushMessage message,
                                                   List<String> totalInactiveDevices,
                                                   List<AppDevice> devicesToUpdate,
                                                   List<AppDevice> deviceList,
                                                   Boolean useProduction);

    /**
     * Test connection.
     *
     * @param status the status
     */
    void testConnection(ServiceStatus status);
}
