package com.froso.ufp.core.response.messaging;

import com.froso.ufp.core.domain.interfaces.*;
import java.text.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser:Christian Kleinhuis (ck@froso.de)
 * Date: 30.10.13
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
public class UFPResponseMessage implements IDataObject {

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final Date date;
    private String body;
    private MessageType type;

    /**
     * Constructor Message.
     *
     * @param messageIn the message in
     */
    public UFPResponseMessage(String messageIn) {

        body = messageIn;
        date = new Date();
        type = MessageType.LOG;
    }

    /**
     * Constructor Message.
     *
     * @param messageIn the message in
     * @param typeIn    the type in
     */
    public UFPResponseMessage(String messageIn,
                              MessageType typeIn) {

        body = messageIn;
        date = new Date();
        type = typeIn;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public MessageType getType() {

        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(MessageType type) {

        this.type = type;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {

        return dateFormat.format(date);
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {

        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {

        this.body = body;
    }
}
