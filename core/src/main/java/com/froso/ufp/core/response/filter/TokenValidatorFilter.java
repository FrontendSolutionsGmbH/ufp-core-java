package com.froso.ufp.core.response.filter;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.session.interfaces.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.user.exception.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.core.annotation.*;
import org.springframework.http.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
@Order(400)
public class TokenValidatorFilter
        implements Filter {
    /**
     * The constant CHECK_REQUEST_COUNTS.
     */
    public static final Boolean CHECK_REQUEST_COUNTS = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidatorFilter.class);
    @Autowired
    private RequestDefinitionService requestDefinitionService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ISessionService sessionService;
    @Autowired
    private IPropertyService propertyService;

    /**
     * Constructor Token translator filter.
     */
    public TokenValidatorFilter() {

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

        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext
                (servletContext);

        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();

        autowireCapableBeanFactory.autowireBean(this);

    }

    private void printHeader(Enumeration<?> e) {
        while (e.hasMoreElements()) {
            LOGGER.info(e.nextElement().toString());
        }
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
     * @throws java.io.IOException if there's an IO problem
     * @throws java.io.IOException if there's an IO problem
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException,
            ServletException {

//        LOGGER.info("doFilter");
//         LOGGER.debug("validating token ...");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest reqi = ((HttpServletRequest) request);
//            LOGGER.info("validating token ..." + reqi.getServletPath());
//            LOGGER.info("validating token ..." + reqi.getMethod());
//            LOGGER.info("validating token ..." + reqi.getRequestURI());
//            LOGGER.info("validating token ..." + reqi.getRequestURL());
//            LOGGER.info("validating token ..." + reqi.getRemoteUser());
//            LOGGER.info("validating token ..." + reqi.getServletPath());
//            LOGGER.info("validating token ..." + reqi.getQueryString());
//            LOGGER.info("validating token ..." + reqi.getPathInfo());
//            LOGGER.info("validating token ..." + reqi.getPathTranslated());
//            printHeader(reqi.getHeaderNames());
//            LOGGER.info("validating token ..." + reqi.getHeaderNames().toString());


//            LOGGER.info("validating token ..." + reqi.getServerPort());
            try {
                if (requestDefinitionService.isPathTokenFree(((HttpServletRequest) request).getServletPath())) {

                    LOGGER.debug("Path is token free");

                } else if (!((HttpServletRequest) request).getMethod().equals("OPTIONS")) {

                    if (((HttpServletRequest) request).getHeader(HeaderNames.HEADER_X_UFP_TOKEN) != null) {


                        LOGGER.debug("using token from header ...");
                        String token = ((HttpServletRequest) request).getHeader(HeaderNames.HEADER_X_UFP_TOKEN);

                        checkToken(token, (HttpServletRequest) request);

                    } else {
//                        LOGGER.debug("using token from path ...");
                        // check if token is in url *warning* deprecated
                        String pathID = ((HttpServletRequest) request).getServletPath();
                        String[] pathIds = pathID.split("/");
//                        LOGGER.debug("using token from path ...", pathIds);
                        String encryptedStuff;

                        // special swagger ui exclusion
                        if ((pathIds.length >= 4) && (!requestDefinitionService.isPathTokenFree(pathID))) {
                            encryptedStuff = pathIds[3];

//                            LOGGER.debug("using token from path ..." + encryptedStuff);
                            checkToken(encryptedStuff, (HttpServletRequest) request);
                        }


                    }
                }
            } catch (Exception ignored) {

                LOGGER.error("problem validating token {}", ignored);
                LOGGER.debug(ignored.getMessage(), ignored);
                // Build response here!
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
                ResponseHandler handler = new ResponseHandler();
                handler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_INVALID);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                String jsonString = objectMapper.writeValueAsString(handler.getResponseEntity().getBody());
                response.getWriter().write(jsonString);
                response.getWriter().flush();
                response.getWriter().close();
                return;

            }
        }
        chain.doFilter(request, response);
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

    private void checkToken(String token, HttpServletRequest request) {


        LOGGER.debug("checking token ..." + token);
        // The token needs to be in the session table
        Session session = sessionService.findByToken(token);

        if (session == null) {
            LOGGER.error("Token nout found: " + token);
            throw new UserTokenException.InvalidToken();

        }

        LOGGER.debug("token session found ..." + session.getId());
        if (CHECK_REQUEST_COUNTS) {
            // Get input request couint
            String inputRequestCounter = request.getHeader(HeaderNames.HEADER_X_UFP_REQUEST_INDEX);
            // Convert it to Integer
            if ((null == inputRequestCounter)) {
                throw new UserTokenException("Request Index not set");
            }
            Integer inputRequestCount = Integer.parseInt(inputRequestCounter);


            LOGGER.debug("---------------------------- CHECKING TOKEN --------------------------------------------");


            LOGGER.debug(request.getPathTranslated());
            LOGGER.debug("Incoming request count is: " + inputRequestCount);
            LOGGER.debug("Used Session Request Indices " + session.getUsedRequestIndices());

            LOGGER.debug("---------------------------- CHECKING TOKEN END --------------------------------------------");


            if (Math.abs(inputRequestCount - session.getLastUsedIndex()) < 50) {


            } else {
                session.setActive(false);
                sessionService.save(session);
                LOGGER.info("Out of allowed request index range..." + session.getId());
                throw new UserTokenException("Out of allowed request index range");

            }

            if (inputRequestCount > session.getLastUsedIndex()) {
                session.setLastUsedIndex(inputRequestCount);
            }

            if (session.getUsedRequestIndices().contains(inputRequestCount)) {

                session.setActive(false);
                sessionService.save(session);

                LOGGER.info("index already used .." + session.getId());
                throw new UserTokenException("Index already used");

            } else {

                session.getUsedRequestIndices().add(inputRequestCount);

            }
        }

        if (!session.getActive()) {

            LOGGER.info("token no longer active .." + session.getId());
            throw new UserTokenException.InvalidToken();

        }
        // check remote hosts, they may not change duríng a session
    /*    if (!session.getRemoteHost().equals(request.getRemoteAddr())) {
            session.setActive(false);
            sessionService.save(session);
            throw new UserTokenException.RemoteHostChanged();

        }
        */
        if (DateTime.now().isAfter(session.getEndsOn())) {
            LOGGER.debug("token timed out now is.." + DateTime.now().toString());
            LOGGER.debug("token timed timeout is .." + session.getEndsOn().toString());
            LOGGER.info("token timed out .." + session.getId());
            session.setActive(false);
            sessionService.save(session);

            throw new UserTokenException.TokenExpired();


        }
        session.setEndsOn(DateTime.now().plusMillis(propertyService.getPropertyValueInteger(Session.PROP_NAME_SESSION_DURATION)));
        session.setUsed(session.getUsed() + 1);

        sessionService.save(session);
        LOGGER.debug("token valid!.." + session.getId());

        ///////// session handl
    }

}
