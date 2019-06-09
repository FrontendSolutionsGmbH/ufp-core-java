package com.froso.ufp.modules.core.workflow.model.trigger;

import com.froso.ufp.core.domain.events.*;

/**
 * Created by ckleinhuix on 25.01.2016.
 */
public class CRUDTrigger implements Trigger {

    private DataDocumentCRUDEvent event;
    private String typeName;
    private String elementId;

    /**
     * Gets event.
     *
     * @return the event
     */
    public DataDocumentCRUDEvent getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(DataDocumentCRUDEvent event) {
        this.event = event;
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets type name.
     *
     * @param typeName the type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets element id.
     *
     * @return the element id
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Sets element id.
     *
     * @param elementId the element id
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }
}
