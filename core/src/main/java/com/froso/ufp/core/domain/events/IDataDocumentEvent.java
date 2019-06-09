package com.froso.ufp.core.domain.events;

/**
 * Created by ckleinhuix on 14.05.2015.
 *
 * @param <DATADOCUMENT> the type parameter
 */
public interface IDataDocumentEvent<DATADOCUMENT> {


    /**
     * Gets data document.
     *
     * @return the data document
     */
    DATADOCUMENT getDataDocument();

    /**
     * Sets data document.
     *
     * @param dataDocument the data document
     */
    void setDataDocument(DATADOCUMENT dataDocument);

    /**
     * Gets event name.
     *
     * @return the event name
     */
    DataDocumentCRUDEvent getEventName();

}
