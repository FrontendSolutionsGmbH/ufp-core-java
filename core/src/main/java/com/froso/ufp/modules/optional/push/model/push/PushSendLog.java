package com.froso.ufp.modules.optional.push.model.push;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import org.joda.time.*;

/**
 * Created by ckleinhuis on 31.01.2017.
 */
public class PushSendLog {

    private String payload;
    private DateTime date = DateTime.now();
    private String response;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getDate() {
        return date;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
