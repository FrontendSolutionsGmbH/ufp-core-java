package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class Attachment {
    private String type;
    private AnyPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AnyPayload getPayload() {
        return payload;
    }

    public void setPayload(AnyPayload payload) {
        this.payload = payload;
    }
}
