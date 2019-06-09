package com.froso.ufp.modules.optional.facebookbot.model.bot;

import java.util.*;

/**
 * Created by ck on 16.08.2016.
 */
public class PageData extends IDObject {
    private String time;
    private List<Messaging> messaging;

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets messaging.
     *
     * @return the messaging
     */
    public List<Messaging> getMessaging() {
        return messaging;
    }

    /**
     * Sets messaging.
     *
     * @param messaging the messaging
     */
    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }

}
