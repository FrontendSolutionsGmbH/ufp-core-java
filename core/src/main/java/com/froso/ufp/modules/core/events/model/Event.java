package com.froso.ufp.modules.core.events.model;

import com.froso.ufp.core.domain.documents.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class Event extends AbstractDataDocumentWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "Event";


    /**
     * Constructor Simple app device.
     */
// Constructor
    public Event() {

        super(TYPE_NAME);
    }

}
