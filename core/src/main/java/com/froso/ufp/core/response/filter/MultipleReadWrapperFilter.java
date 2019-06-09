package com.froso.ufp.core.response.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.core.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
@Component
@Order(0)
public class MultipleReadWrapperFilter

        implements Filter {
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MultipleReadWrapperFilter.class);

    /**
     * Instantiates a new Multiple read wrapper filter.
     */
// public static final String APPLICATION_PROPERTY_FILTER_ADMIN = "security.doFilterAdmin";
    public MultipleReadWrapperFilter() {

        LOGGER.info("created");

    }

    /**
     * Called by the web container to indicate to a filter that it is being placed into service. The servlet container
     * calls the init method exactly once after instantiating the filter. The init method must complete successfully
     * before the filter is asked to do any filtering work. <br><br>
     * <p>
     * The web container cannot place the filter into service if the init method either<br> 1.Throws a ServletException
     * <br> 2.Does not return within a time period defined by the web container
     *
     * @param filterConfig Configuration for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // This Method is Empty, needed for implementation
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is
     * passed through the chain due to a client request for a resource at the end of the chain. The FilterChain passed
     * in to this method allows the Filter to pass on the request and response to the next entity in the chain.<p> A
     * typical implementation of this method would follow the following pattern:- <br> 1. Examine the request<br> 2.
     * Optionally wrap the request object with a custom implementation to filter content or headers for input filtering
     * <br> 3. Optionally wrap the response object with a custom implementation to filter content or headers for output
     * filtering <br> 4. a) <strong>Either</strong> invoke the next entity in the chain using the FilterChain object
     * (<code>chain.doFilter()</code>), <br> * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br> * 5. Directly set headers on the response
     * after invocation of the next entity in the filter chain.
     *
     * @param request  servlet request to filter
     * @param response servlet response to filter
     * @param chain    what to do next
     * @throws IOException if there's an IO problem
     * @throws IOException if there's an IO problem
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException,
            ServletException {
//        LOGGER.info("doFilter");

        // Secure Entiteit input for the restservice
    /*   if (PropertyService.getPropertyBoolean(APPLICATION_PROPERTY_FILTER_ADMIN) ||


            ((HttpServletRequest) request).getPathTranslated().startsWith("/dev") || ((HttpServletRequest) request).getPathTranslated().startsWith("/" + SimpleUser
            .TYPE_NAME)) {
             /* wrap the request in order to read the inputstream multiple times * /
            MultipleReadHttpServletRequestSanitized multiReadRequest = new MultipleReadHttpServletRequestSanitized((HttpServletRequest) request);

            chain.doFilter(multiReadRequest, response);
        } else {
      */
        // let through unfiltered for remaining ressources
        /* wrap the request in order to read the inputstream multiple times */
        MultipleReadHttpServletRequest multiReadRequest = new MultipleReadHttpServletRequest((HttpServletRequest) request);

        chain.doFilter(multiReadRequest, response);
        //}

        /* here I read the inputstream and do my thing with it; when I pass the
         * wrapped request through the filter chain, the rest of the filters, and
         * request handlers may read the cached inputstream
         */
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service. This method is only
     * called once all threads within the filter's doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the doFilter method again on this instance of the
     * filter. <br><br>
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for example, memory,
     * file handles, threads) and make sure that any persistent state is synchronized with the filter's current state
     * in
     * memory.
     */

    @Override
    public void destroy() {
        // This Method is Empty, needed for implementation
    }
}
