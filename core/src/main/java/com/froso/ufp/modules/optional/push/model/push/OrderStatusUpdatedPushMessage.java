package com.froso.ufp.modules.optional.push.model.push;

import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: alex
 * Date: 16.11.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class OrderStatusUpdatedPushMessage extends AbstractBasePushMessage {
    private static final String KEY_ORDERID = "orderId";
    private static final String KEY_USERID = "userId";
    private static final String MP3_FILENAME = AbstractBasePushMessage.DEFAULT_SOUNDFILENAME;
    private String orderId;
    private String userId;
    private String message;

    /**
     * Constructor Order status updated push message.
     */
    public OrderStatusUpdatedPushMessage(String messageIn) {

        super(LanguageEnum.GERMAN);
        setSoundFileName(MP3_FILENAME);
        message = messageIn;
    }


    public String getMessageTitle() {
        return message;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {

        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {

        this.userId = userId;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrderId() {

        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(String orderId) {

        this.orderId = orderId;
    }

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    @Override
    public Map<String, String> getParameters() {

        Map<String, String> map = new HashMap<>();
        map.put(KEY_ORDERID, orderId);
        map.put(KEY_USERID, userId);
        return map;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    @Override
    public String getMessageType() {

        return PushMessageTypeEnum.ORDER_STATUS_UPDATED.toString();
    }
}
