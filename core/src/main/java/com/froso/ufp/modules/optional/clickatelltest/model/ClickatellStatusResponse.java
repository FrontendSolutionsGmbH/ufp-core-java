package com.froso.ufp.modules.optional.clickatelltest.model;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by mr on 06.06.16.
 */
public class ClickatellStatusResponse implements IDataObject {


    private String messageId;
    private String charge;
    private String status;
    private String statusString;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }


}
