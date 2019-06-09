package com.froso.ufp.core.response.request;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.exceptions.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import javax.servlet.http.*;
import org.slf4j.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 13.11.13 Time: 17:22 To change this
 * template use File | Settings | File Templates.
 */
public final class RequestHelper {

    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);
    private final HttpServletRequest request;

    /**
     * constructor
     *
     * @param requestIn the request param used by this class for enriching elements before getOutput!
     */
    public RequestHelper(HttpServletRequest requestIn) {

        request = requestIn;
    }

    /**
     * helper function for retrieving of current API url
     *
     * @return the base path of the application
     */
    public String getApiUrl() {

        String result = "";
        try {
            result = PropertyServiceRepositoryImpl.getProperty(ServerService.PROP_NAME_APPLICATION_SERVER_API);
        } catch (PropertyException ignored) {
            LOGGER.debug(ignored.getMessage(), ignored);
            //falls property nicht vorhanden nehme aus request
            if (null != request) {
                String protocol = request.getProtocol();
                result = protocol + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            }
        }
        return result;
    }

}
