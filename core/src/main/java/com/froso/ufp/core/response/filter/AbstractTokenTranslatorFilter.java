package com.froso.ufp.core.response.filter;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.session.interfaces.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.securitylog.interfaces.*;
import com.froso.ufp.modules.optional.securitylog.model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.http.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
public abstract class AbstractTokenTranslatorFilter
        implements Filter {
    /**
     * The constant PROPERTY_SECURITY_USER_VALIDDURATION.
     */
    public static final String PROPERTY_SECURITY_USER_VALIDDURATION = "ufp.core.security.user.validduration";
    /**
     * The constant ASSIGN_DEFAULT.
     */
    public static final String ASSIGN_DEFAULT = "AssignDefault";
    /**
     * The constant RESSOURCE_ADMIN_ASSIGN_DEFAULT.
     */
    public static final String RESSOURCE_ADMIN_ASSIGN_DEFAULT = "/" + UFPConstants.API + "/CoreUser/Command/" + ASSIGN_DEFAULT;
    /**
     * The constant RESSOURCE_USER_PASSWORD_RESET.
     */
    public static final String RESSOURCE_USER_PASSWORD_RESET = "/" + UFPConstants.API + "/CoreUser/Command/PasswordReset";
    /**
     * The constant RESSOURCE_USER.
     */
    public static final String RESSOURCE_USER = "/" + UFPConstants.API + "/CoreUser";
    /**
     * The constant RESSOURCE_USER_SESSION.
     */
    public static final String RESSOURCE_USER_SESSION = "/" + UFPConstants.API + "/CoreUser/Session";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTokenTranslatorFilter.class);


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ISessionService sessionService;
    @Autowired
    private ICoreUserService entiteitService;
    @Autowired(required = false)
    private ISecurityLogService securityLogService;

    @Autowired
    private RequestDefinitionService requestDefinitionService;

    @Autowired
    private IPropertyService propertyService;

    /**
     * Constructor Token translator filter.
     */
    AbstractTokenTranslatorFilter() {

    }

    /**
     * Map to list list.
     *
     * @param parametermap the parametermap
     * @return the list
     */
    public static List<KeyValueItem> mapToList(Map<String, String[]> parametermap) {

        List<KeyValueItem> stringMapArrays = new ArrayList<>();
        for (Map.Entry<String, String[]> param : parametermap.entrySet()) {
            KeyValueItem stringMapArray = new KeyValueItem();
            stringMapArray.setKey(param.getKey());
            stringMapArray.setValue(param.getValue().toString());
            stringMapArrays.add(stringMapArray);
        }
        return stringMapArrays;

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

        LOGGER.debug("Translating Token" + getMainRessource());

/**
 * TODO: FIXME: REMOVE THIS HORRIBLE TOKEN HANDLINE WITH A MORE RIGID CENTRALISED APPROACH!
 */
        if (request instanceof MultipleReadHttpServletRequest || request instanceof SanitizeWrapper) {
            HttpServletRequest httprequest = (HttpServletRequest) request;
            if (httprequest.getServletPath().startsWith(RESSOURCE_ADMIN_ASSIGN_DEFAULT)) {
                // Let through:

                LOGGER.debug("Path is " + RESSOURCE_ADMIN_ASSIGN_DEFAULT + " no token required ...");
                chain.doFilter(request, response);
                return;
            }
            //String temp = httprequest.getServletPath();
            if (httprequest.getServletPath().startsWith(RESSOURCE_USER_PASSWORD_RESET)) {
                LOGGER.debug("Path is " + RESSOURCE_USER_PASSWORD_RESET + " no token required ...");
                // Let through:
                chain.doFilter(request, response);
                return;
            }
            if (RESSOURCE_USER.equals(httprequest.getServletPath())) {
                LOGGER.debug("Path is " + RESSOURCE_USER + " no token required ...");
                // Let through:
                chain.doFilter(request, response);
                return;
            }
            if ((RESSOURCE_USER + "/Auth").equals(httprequest.getServletPath())) {
                LOGGER.debug("Path is " + RESSOURCE_USER + "/Auth no token required ...");
                // Let through:
                chain.doFilter(request, response);
                return;
            }
            if (httprequest.getServletPath().startsWith(RESSOURCE_USER + "/Command")) {
                LOGGER.debug("Path is " + RESSOURCE_USER + "/Command no token required ...");
                // Let through:
                chain.doFilter(request, response);
                return;
            }
            if (httprequest.getServletPath().startsWith(RESSOURCE_USER_SESSION) && ((HttpServletRequest) request).getMethod().equals("POST")) {
                LOGGER.debug("Path is " + RESSOURCE_USER_SESSION + "/Command no token required ...");
                // Let through:
                chain.doFilter(request, response);
                return;
            }

            if ("OPTIONS".equals(httprequest.getMethod())) {
                LOGGER.debug("Path is OPTIONS  no token required ...");
                // Let through:
                chain.doFilter(request, response);

                return;
            }
        }

        if (requestDefinitionService.isPathTokenFree(((HttpServletRequest) request).getServletPath())) {
            // Let through:

            LOGGER.debug("Path is registereed in tokenfree pathes - let through ...");
            chain.doFilter(request, response);
            return;
        }
        String pathID = ((HttpServletRequest) request).getServletPath();
        String[] pathIds = pathID.split("/");


        try {
            Boolean usedHeader = false;
            String encryptedStuff = null;


            if (3 < pathIds.length && pathIds[2].equals(getMainRessource())) {
                usedHeader = false;
                encryptedStuff = pathIds[3];


            }


            if (((HttpServletRequest) request).getHeader(HeaderNames.HEADER_X_UFP_TOKEN) != null) {
                // get token from header if available
                usedHeader = true;
                encryptedStuff = ((HttpServletRequest) request).getHeader(HeaderNames.HEADER_X_UFP_TOKEN);


            }

            Session session = sessionService.findByToken(encryptedStuff);
            if (session == null) {
                throw new UserTokenException("Token not found!");
            }

            ICoreUser coreUser = (ICoreUser) entiteitService.findOneBrute(session.getUserLink().getId());
            if (coreUser == null) {
                throw new UserTokenException("CoreUser not found!");

            }

            StringBuilder urlStringBuilder = new StringBuilder();
            urlStringBuilder.append("/");
            urlStringBuilder.append(UFPConstants.API);
            urlStringBuilder.append("/");
            urlStringBuilder.append(getMainRessource());
            // Get the coreUser-id
            urlStringBuilder.append("/");
            String userID = coreUser.getId();
            urlStringBuilder.append(userID);
            if (usedHeader) {

                if (2 < pathIds.length) {
                    for (int i = 3; i < pathIds.length; i++) {
                        if (!encryptedStuff.equals(pathIds[i])) {
                            // do nothing or throw error if token is defined twice in request the header wins always
                            urlStringBuilder.append("/");
                            urlStringBuilder.append(pathIds[i]);
                        }
                    }
                }
            } else {
                if (3 < pathIds.length) {
                    for (int i = 4; i < pathIds.length; i++) {
                        urlStringBuilder.append("/");
                        urlStringBuilder.append(pathIds[i]);
                    }
                }
            }
            // And finally perform a role check ( preliminary according to encrypted role in token!  )


            validateUserRole(coreUser.getId(), coreUser.getRole().toString(), (HttpServletRequest) request);
            request.getRequestDispatcher(urlStringBuilder.toString()).forward(request, response);
            // refactor if needed, but return right now ( do not send dofilter and forward at the
            // same time!
            return;
        } catch (UserTokenException.InvalidRole ignored) {
            LOGGER.debug(ignored.getMessage(), ignored);

/*
            throw new UFPRuntimeException(ResultStatusEnumCode.ERROR_TOKEN_INVALID_ROLE, ignored);
 */
            // Build response here!
            ResponseHandler handler = new ResponseHandler();
            handler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_INVALID_ROLE);
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            String jsonString = objectMapper.writeValueAsString(handler.getResponseEntity().getBody());
            response.getWriter().write(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
            return;

        } catch (UserTokenException.TokenExpired ignored) {


            /*
            throw new UFPRuntimeException(ResultStatusEnumCode.ERROR_TOKEN_INVALID, ignored);
        */
            LOGGER.debug(ignored.getMessage(), ignored);
            // Build response here!
            ResponseHandler handler = new ResponseHandler();
            handler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_INVALID);
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            String jsonString = objectMapper.writeValueAsString(handler.getResponseEntity().getBody());
            response.getWriter().write(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
            return;

        } catch (Exception ignored) {
            LOGGER.debug(ignored.getMessage(), ignored);

            //  throw new UFPRuntimeException(ResultStatusEnumCode.ERROR_TOKEN_INVALID, ignored);
            // Build response here!
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

    /**
     * Gets main ressource.
     *
     * @return the main ressource
     */
    abstract String getMainRessource();

    /**
     * Validate user role.
     *
     * @param userID  the user id
     * @param role    the role
     * @param request the request
     */
    protected void validateUserRole(String userID, String role, HttpServletRequest request) {

        doValidateUserRole(role);
        // perform here a logging if role is admin....
        if (UserRoleEnum.ROLE_ADMIN.toString().equals(role) && request.getMethod().equals("POST") || request.getMethod().equals("DELETE") || request.getMethod()
                .equals("PUT")) {

            SecurityLog log = new SecurityLog();
            log.setRequestParameters(request.getParameterMap());
            log.setLogType(SecurityLog.LOG_TYPE_ADMIN_WRITE);
            log.setRemoteHost(request.getRemoteHost());
            log.setUserID(userID);
            // log request

            RequestLog requestLog = new RequestLog();
            requestLog.setMethod(request.getMethod());


            //   Map<String, String[]> parametermap = request.getParameterMap();
       /*         List<StringMapArray> stringMapArrays = new ArrayList<>();
                for (Map.Entry<String, String[]> param : parametermap.entrySet()) {
                    StringMapArray stringMapArray = new StringMapArray();
                    stringMapArray.setName(param.getKey());
                    stringMapArray.getValues().addAll(Arrays.asList(param.getValue()));
                    stringMapArrays.add(stringMapArray);
                }

                requestLog.setParameters(stringMapArrays);
                */
            requestLog.setUrl(request.getRequestURI());
            Request request1 = new Request();

            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(request.getInputStream(), writer, "UTF-8");
            } catch (Exception e) {
                writer.append("Problem with writing body - " + e.getMessage());
                LOGGER.error("Problem with writing body -  ", e);

            }
            request1.setBody(writer.toString());
            requestLog.setRequest(request1);
            log.getRequestLog().add(requestLog);

            if (null != securityLogService) {
                securityLogService.save(log);
            }
        }
    }


    private void verifyTime(String input) {
        // Verify Date
        // Read the stored time
        DateTime date = Encryptor.encryptionDateStringToDateTime(input);
        // Default 1 Hour...
        Period period;
        try {
            period = new Period().withMillis(propertyService.getPropertyValueInteger(PROPERTY_SECURITY_USER_VALIDDURATION));
        } catch (Exception ignored) {
            // Something went wrong parsing the date, fatal criteria!
            LOGGER.debug(ignored.getMessage(), ignored);
            throw new UserTokenException.InvalidToken();
        }
        if (date.plus(period).isBefore(DateTime.now())) {
            throw new UserTokenException.TokenExpired();
        }
    }

    /**
     * Do validate user role.
     *
     * @param role the role
     */
    protected abstract void doValidateUserRole(String role);

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

    /**
     * Validate user role.
     *
     * @param request the request
     */
    protected abstract void logAccess(HttpServletRequest request);
}
