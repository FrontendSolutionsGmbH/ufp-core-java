package com.froso.ufp.core.domain.events;

import com.froso.ufp.core.domain.interfaces.*;
import org.springframework.context.*;

/**
 * Created by ckleinhuix on 14.05.2015.
 *
 * @param <DATADOCUMENT> the type parameter
 */
public class DataDocumentEvent<DATADOCUMENT extends IDataDocument> extends ApplicationEvent {

    private static final long serialVersionUID = 6647530631970081096L;
    private DataDocumentCRUDEvent eventName;
    private DATADOCUMENT dataDocument;

    /**
     * Instantiates a new Data document event.
     *
     * @param name       the name
     * @param documentIn the document in
     * @param source     the source
     */
    protected DataDocumentEvent(DataDocumentCRUDEvent name, DATADOCUMENT documentIn, Object source) {
        super(source);
        dataDocument = documentIn;
        eventName = name;
    }

    /**
     * Gets data document.
     *
     * @return the data document
     */
    public DATADOCUMENT getDataDocument() {
        return dataDocument;
    }

    /**
     * Sets data document.
     *
     * @param dataDocument the data document
     */
    public void setDataDocument(DATADOCUMENT dataDocument) {
        this.dataDocument = dataDocument;
    }

    /**
     * Gets event name.
     *
     * @return the event name
     */
    public DataDocumentCRUDEvent getEventName() {
        return eventName;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    public String toString() {
        return "DataDocumentEvent:" + eventName.toString();
    }
}
