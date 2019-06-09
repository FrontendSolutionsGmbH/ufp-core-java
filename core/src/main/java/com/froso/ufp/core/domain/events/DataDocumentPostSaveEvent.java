package com.froso.ufp.core.domain.events;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by ckleinhuix on 14.05.2015.
 */
public class DataDocumentPostSaveEvent<DATADOCUMENT extends IDataDocument> extends DataDocumentEvent<DATADOCUMENT> {


    private static final long serialVersionUID = 2606605373827179521L;

    public DataDocumentPostSaveEvent(DATADOCUMENT documentIn, Object source) {
        super(DataDocumentCRUDEvent.POST_UPDATE, documentIn, source);
    }


}
