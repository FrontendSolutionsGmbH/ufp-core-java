package com.froso.ufp.modules.optional.push.model.push;

import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created with IntelliJ IDEA.
 * CoreUser: ck
 * Date: 24.03.14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class PushData
        extends MessageDataBase {
    // PushMessageTypeEnum.ORDER_STATUS_UPDATED
    public static final String TYPE_NAME_ORDER = "ORDER_STATUS_UPDATED";
    //PushMessageTypeEnum.SUGGESTION_RECEIVED
    public static final String TYPE_NAME_SUGGESTIOM = "SUGGESTION_RECEIVED";
    @Indexed
    private String deviceToken;
    private String type = TYPE_NAME_ORDER;

    private List<AppDevice> appDevices = new ArrayList<>();

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public List<AppDevice> getAppDevices() {
        return appDevices;
    }

    public void setAppDevices(List<AppDevice> appDevices) {
        this.appDevices = appDevices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
