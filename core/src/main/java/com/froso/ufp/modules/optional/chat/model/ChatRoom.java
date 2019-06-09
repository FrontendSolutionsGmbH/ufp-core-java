package com.froso.ufp.modules.optional.chat.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import java.util.*;

/**
 * Created by ck on 20.09.2016.
 */
public abstract class ChatRoom extends AbstractDataDocumentWithClientLinkAndName {

    /**
     * The constant TYPE_NAME.
     */

    private List<DataDocumentLink> topic = new ArrayList<>();


    /**
     * Instantiates a new Chat data.
     *
     * @param name the name
     */
    public ChatRoom(String name) {

        super(name);

    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public List<DataDocumentLink> getTopic() {
        return topic;
    }

    /**
     * Sets topic.
     *
     * @param topic the topic
     */
    public void setTopic(List<DataDocumentLink> topic) {
        this.topic = topic;
    }

}
