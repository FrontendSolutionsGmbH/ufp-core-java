package com.froso.ufp.core.domain.events;

import java.lang.reflect.*;
import org.slf4j.*;
import org.springframework.context.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 *
 * @param <T>     the type parameter
 * @param <EVENT> the type parameter
 */
public abstract class EventHandlerBase<T, EVENT extends DataDocumentEvent> implements ApplicationListener<EVENT> {

    /**
     * Instantiates a new Handler clear cache.
     */


    /**
     * Instantiates a new Handler clear cache.
     */
    public EventHandlerBase() {
        //LOGGER.info("EVENT HANDLER Base");
    }

    /**
     * Gets class of repository.
     *
     * @return the class of repository
     */
    public Class<T> getClassOfRepository() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }

    /**
     * On application event.
     *
     * @param event the event
     */
    public void onApplicationEvent(EVENT event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here
        if (getClassOfRepository().isInstance(event.getDataDocument())) {
            doHandleEvent((T) event.getDataDocument());
        }
        // check if user shall be blocked, upon auth_email reset attempt, or auth_email fail
    }


    /**
     * Do handle event.
     *
     * @param element the element
     */
    abstract protected void doHandleEvent(T element);
}


