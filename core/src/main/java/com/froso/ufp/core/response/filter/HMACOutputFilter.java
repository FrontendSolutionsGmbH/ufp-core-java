package com.froso.ufp.core.response.filter;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

/**
 * The type Hmac output filter.
 */
//@Component
@Order(100)
public class HMACOutputFilter
        extends OncePerRequestFilter {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(HMACOutputFilter.class);
    /**
     * The constant SECURITY_PREFIX.
     */
    public static final String SECURITY_PREFIX = ")]}',\n";

    @Autowired
    private IPropertyService propertyService;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain
            filterChain) throws
            ServletException,
            IOException {


//        LOGGER.info("doFilter");
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        filterChain.doFilter(request, responseWrapper);
        if (
                response.getContentType() != null && (
                        response.getContentType().equals(ResponseHandler.APPLICATION_CONTENT_TYPE_UTF8) ||
                                response.getContentType().contains("text/html") ||
                                response.getContentType().contains(ResponseHandler.APPLICATION_XML) ||
                                response.getContentType().equals(ResponseHandler.APPLICATION_JSON)
                )
        ) {
            // just add header to response if it is json... security issues
            String responseContentOriginal = new String(responseWrapper.getDataStream(), responseWrapper.getCharacterEncoding());
            String responseContent = responseContentOriginal;
            /*
            if (
                    (response.getContentType() == null) && (
                            (response.getContentType().equals(ResponseHandler.APPLICATION_CONTENT_TYPE_UTF8)) ||
                                    (response.getContentType().equals(ResponseHandler.APPLICATION_JSON)
                                    ))) {
                // prepend json vulnerability prefix
                responseContent = SECURITY_PREFIX + responseContent;
            }
            */


            // VERY STRANGE BEHAVIOUR OF RESPONSE WRAPPER
            // SOMETIMES HEADER IS MISSING; SOMETIMES CONTENT, which is the reason for the stupid double checks here... :/
            //  responseWrapper.addHeader(HeaderNames.HEADER_X_RETAIL_HMAC, HMACUtil.computeHmac(responseContent,
            // PropertyService.getProperty(HMACFilter.PROP_NAME_SECRET1)));

            //  LOGGER.info("Creating HMAC Response");
            //   LOGGER.info(responseContent);


            response.setHeader(HeaderNames.HEADER_X_RETAIL_HMAC, HMACUtil.computeHmac(responseContent,
                    propertyService.getPropertyValue(HMACFilter.PROP_NAME_SECRET1)));
            //     LOGGER.info("Creating HMAC Response END");


            try {

                response.getWriter().write(responseContent);
                response.flushBuffer();
            } catch (Exception e) {

                LOGGER.error("HMAC OUTPUT Filter Problem: " + e.getMessage(), e);
                // writing to response errornous, use wrapper insteaD
                //  response.getOutputStream().write(responseContent.getBytes());
                // todo: fixme: use ufp response object
                response.getOutputStream().write(e.getMessage().getBytes());


                //    responseWrapper.getOutputStream().write(responseContent.getBytes());


            }
        } else {
            // no json output generated, leave alone
            // throughput response as is
            response.getOutputStream().write(responseWrapper.getDataStream());

        }

    }


}
