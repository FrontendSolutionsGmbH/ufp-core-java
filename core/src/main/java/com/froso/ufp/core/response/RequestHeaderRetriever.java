package com.froso.ufp.core.response;

import com.froso.ufp.core.exceptions.*;
import org.springframework.web.context.request.*;

/**
 * Created by ckleinhuix on 23.10.2014.
 * <p>
 * helper class, using the thread/session bound RequestContextHolder object provided by spring framework to access
 * request data in non-controller objects to retrieve currently active corporate-id
 */
public final class RequestHeaderRetriever {


    /**
     * The constant NOTSET.
     */
    public static final String NOTSET = null;

    private RequestHeaderRetriever() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }


    /**
     * Gets current client.
     *
     * @return the current client
     */
    public static String getCurrentClient() {
        String result = NOTSET;
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            result = attr.getRequest().getHeader(HeaderNames.HEADER_X_UFP_CLIENT);
            if (result == null) {
                result = NOTSET;
            }
        }
        return result;
    }

    /**
     * Gets current path.
     *
     * @return the current path
     */
    public static String getCurrentPath() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getPathTranslated();

    }
}
