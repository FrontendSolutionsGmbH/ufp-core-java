package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import javax.servlet.http.*;
import org.springframework.util.*;
import org.springframework.web.context.request.*;

/**
 * Created by ckleinhuix on 23.10.2014.
 * <p>
 * helper class, using the thread/session bound RequestContextHolder object provided by spring framework to access
 * request data in non-controller objects to retrieve currently active corporate-id
 */
public final class RequestTokenRetriever {


    private RequestTokenRetriever() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Gets remote adress.
     *
     * @return the remote adress
     */
    public static String getCurrentUserToken() {
        try {
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return getCurrentRequest().getHeader(HeaderNames.HEADER_X_UFP_TOKEN);
        } catch (Exception e) {

            return null;

        }
    }


    /**
     * Gets current request.
     *
     * @return the current request
     */
    public static HttpServletRequest getCurrentRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }


}
