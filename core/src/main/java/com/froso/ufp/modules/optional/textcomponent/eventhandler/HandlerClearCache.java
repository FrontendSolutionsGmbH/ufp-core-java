package com.froso.ufp.modules.optional.textcomponent.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import java.lang.reflect.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 *
 * @param <T>     the type parameter
 * @param <EVENT> the type parameter
 */
public class HandlerClearCache<T, EVENT extends DataDocumentEvent> extends EventHandlerBase<T, EVENT> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerClearCache.class);
    @Autowired
    private TextComponentValueCacheService textComponentValueCacheService;
    /**
     * Instantiates a new Handler clear cache.
     */


    /**
     * Instantiates a new Handler clear cache.
     */
    public HandlerClearCache() {
        LOGGER.info("EVENT HANDLER CLEAR CACHE REGISTERED ");
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
            this.doHandleEvent((T) event.getDataDocument());
        }
        // check if user shall be blocked, upon auth_email reset attempt, or auth_email fail


    }


    /**
     * Handle event.
     *
     * @param element the element
     */
    @Override
    protected void doHandleEvent(T element) {

        textComponentValueCacheService.clearCache();


    }


}


