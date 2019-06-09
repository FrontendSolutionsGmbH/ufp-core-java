package com.froso.ufp.modules.optional.messaging.model.messaging;

import com.froso.ufp.modules.core.user.model.*;

public abstract class LowLevelMessageBase<MESSAGETYPE, RESULTTYPE> extends AbstractDataDocumentWithCoreUserLink {

    private static final long serialVersionUID = -1045130198376226265L;
    private MESSAGETYPE message;
    private RESULTTYPE result;
    private LowLevelMessageStatus info = new LowLevelMessageStatus();

    public LowLevelMessageBase(String typeName, MESSAGETYPE messageDataIn) {
        super(typeName);
        message = messageDataIn;
    }

    public MESSAGETYPE getMessageData() {
        return message;
    }

    public void setMessageData(MESSAGETYPE messageData) {
        this.message = messageData;
    }

    public LowLevelMessageStatus getInfo() {
        return info;
    }

    public void setInfo(LowLevelMessageStatus info) {
        this.info = info;
    }

    public RESULTTYPE getResult() {
        return result;
    }

    public void setResult(RESULTTYPE result) {
        this.result = result;
    }
}
