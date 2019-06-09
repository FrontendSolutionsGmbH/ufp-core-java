package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import javax.servlet.http.*;
import org.springframework.util.*;
import org.springframework.web.context.request.*;

/**
 * Created by ckleinhuix on 23.10.2014.
 * <p>
 * helper class, using the thread/session bound RequestContextHolder object provided by spring framework to access
 * request data in non-controller objects to retrieve currently active corporate-id
 */
public final class RequestRemoteHostRetriever {


    private RequestRemoteHostRetriever() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Gets current corporate id.
     *
     * @return the current corporate id
     */
    public static String getRemoteHostOfCurrentRequest() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();


        return attr.getRequest().getRemoteHost();
    }

    /**
     * Gets remote adress.
     *
     * @return the remote adress
     */
    public static String getRemoteAdress() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getRemoteAddr();
    }

    public static String getApiBasePath() {
        try {
            String completeURL = getCurrentRequest().getRequestURL().toString();
            String path = getCurrentRequest().getContextPath();


            return completeURL.substring(0, completeURL.indexOf(path)) + path;
        } catch (Exception e) {
            return "noApiBasePathFound-contact ckleinhuis to fix it via application property ...";
        }

    }

    public static HttpServletRequest getCurrentRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }


}
