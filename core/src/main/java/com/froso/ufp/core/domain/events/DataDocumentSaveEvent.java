package com.froso.ufp.core.domain.events;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by ckleinhuix on 14.05.2015.
 *
 * @param <DATADOCUMENT> the type parameter
 */
public class DataDocumentSaveEvent<DATADOCUMENT extends IDataDocument> extends DataDocumentEvent<DATADOCUMENT> {


    private static final long serialVersionUID = 2166742763029725070L;

    /**
     * Instantiates a new Data document save event.
     *
     * @param documentIn the document in
     * @param source     the source
     */
    public DataDocumentSaveEvent(DATADOCUMENT documentIn, Object source) {
        super(DataDocumentCRUDEvent.UPDATE, documentIn, source);
    }


}
