package com.froso.ufp.core.domain.events;

import com.froso.ufp.core.domain.interfaces.*;

public class DataDocumentCreateEvent<DATADOCUMENT extends IDataDocument> extends DataDocumentEvent<DATADOCUMENT> {

    private static final long serialVersionUID = 4979682898553615404L;

    public DataDocumentCreateEvent(DATADOCUMENT documentIn, Object source) {
        super(DataDocumentCRUDEvent.CREATE, documentIn, source);
    }

}
