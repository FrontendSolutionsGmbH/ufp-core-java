package com.froso.ufp.core.response.filter;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv1.model.*;
import com.froso.ufp.modules.optional.media.model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.http.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 25.03.14 Time: 12:25 To change this template use File | Settings | File
 * Templates.
 */
public class HMACFilter
        implements Filter {
    /**
     * The constant HMAC_PROPERTY.
     */
    public static final String HMAC_PROPERTY = "s_hmac";
    /**
     * The constant HMAC_TIMESTAMP.
     */
    public static final String HMAC_TIMESTAMP = "s_timestamp";
    /**
     * The constant HMAC_BODY.
     */
    public static final String HMAC_BODY = "s_body";
    /**
     * The constant PROP_NAME_SECRET1.
     */
    public static final String PROP_NAME_SECRET1 = "ufp.core.security.hmac.secret1";
    /**
     * The constant PROP_NAME_SECRET2.
     */
    public static final String PROP_NAME_SECRET2 = "ufp.core.security.hmac.secret2";
    /**
     * The constant PROP_NAME_SECRET3.
     */
    public static final String PROP_NAME_SECRET3 = "ufp.core.security.hmac.secret3";
    /**
     * The constant PROP_NAME_SECRET4.
     */
    public static final String PROP_NAME_SECRET4 = "ufp.core.security.hmac.secret4";
    /**
     * The constant PROP_NAME_SECRET5.
     */
    public static final String PROP_NAME_SECRET5 = "ufp.core.security.hmac.secret5";
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(HMACFilter.class);
    public static final String PROP_NAME_SECURITY_HMAC_MODE = "ufp.core.security.hmac.mode";
    /**
     * The constant PROP_NAME_SECURITY_HMAC_MODE.
     */
    private static final String UTF_8 = "UTF-8";
    //  private static final String SECRET_KEY = "23BEFC0576FF82936AFF688296521F92203F23D202AF214F1F801C6299259A7F";
    // private static final String SECRET_KEY2 = "23BEFC0576FF82936AFF688296521F92203F23D202AF214F1F801C6299259A7F";
    private String apihost;

    @Autowired
    private IPropertyService propertyService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Called by the web container to indicate to a filter that it is being placed into service. The servlet container
     * calls the init method exactly once after instantiating the filter. The init method must complete successfully
     * before the filter is asked to do any filtering work. <br><br>
     *
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
     * @param requestIn the request in
     * @param response  servlet response to filter
     * @param chain     what to do next
     * @throws IOException if there's an IO problem
     * @throws IOException if there's an IO problem
     */
    @Override
    public void doFilter(ServletRequest requestIn, ServletResponse response, FilterChain chain) throws
            IOException,
            ServletException {

        LOGGER.info("doFilter");
        if ("OFF".equals(propertyService.getPropertyValue(PROP_NAME_SECURITY_HMAC_MODE))) {
            // If switched off, process without hmac validation
            chain.doFilter(requestIn, response);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) requestIn;
        // Initialise with request param (although it should be in the body)
        String h_macValueInput = request.getParameter(HMAC_PROPERTY);
        if (h_macValueInput == null) {
            h_macValueInput = request.getHeader(HeaderNames.HEADER_X_RETAIL_HMAC);
        }
        LOGGER.debug(request.getPathInfo());
        //    LOGGER.info(request.getContextPath());
        // Allow
        // /Workflow
        // /Admin
        //
        if ("/".equals(request.getPathInfo())) {
            // Root allow as well
            chain.doFilter(request, response);
            return;
        }

        //multipart requests are excluded
        if (request.getPathInfo().startsWith("/Admin") && request.getPathInfo().contains(Media.TYPE_NAME) && request.getPathInfo().endsWith("upload")) {
            chain.doFilter(request, response);
            return;
        }
        //up and download of admin tables
        if (request.getPathInfo().startsWith("/Admin") && request.getPathInfo().endsWith("/import")) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/notification")) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/Workflow")) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/v2/api-docs")) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/" + TemplateV1.TYPE_NAME)) {
            chain.doFilter(request, response);
            return;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        // Sort the input params
        SortedMap<String, String[]> paramsSorted = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            paramsSorted.put(entry.getKey(), entry.getValue());
        }
        // And insert all the headers to the sortedmap as well:
        // Remove the s_hmac param
        paramsSorted.remove(HMAC_PROPERTY);
        // Include the headers in the generation as well
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            LOGGER.debug("Header Field " + key + " = " + value);
           /* if (HeaderNames.HEADER_X_UFP_TOKEN.equals(key)) {
                // TEMPORARY COMMENTED OUT; SESSION COUNTER IS SUBMITTED NOW

                SimpleSession session = sessionService.findByToken(value);
                if (session != null) {
                    // create request counter virtual entry
                    String[] values = new String[1];
                    values[0] = session.getUsed().toString();
                    paramsSorted.put(HeaderNames.HEADER_X_UFP_REQUEST_INDEX, values);
                } else {
                    throw new HMACException("Session not existant", "", "");
                }

            }    */

            if (HeaderNames.HEADER_X_UFP_TOKEN.equals(key) ||
                    HeaderNames.HEADER_X_UFP_REQUEST_INDEX.equals(key) ||
                    HeaderNames.HEADER_X_RETAIL_TIMESTAMP.equals(key)) {
                String[] values = new String[1];
                values[0] = value;
                paramsSorted.put(key, values);
            }
            if (HMAC_PROPERTY.equals(key)) {
                // Store hmac from value
                h_macValueInput = value;
            }
            if (HMAC_TIMESTAMP.equals(key)) {
                // Store hmac from value
                String[] values = new String[1];
                values[0] = value;
                paramsSorted.put(key, values);
            }
        }
        String body;
        // And finally IF there is a body element, common it in the hmac code as well
        if (HttpMethod.PUT.toString().equalsIgnoreCase(request.getMethod()) || HttpMethod.POST.toString().equalsIgnoreCase(request.getMethod())) {
            if (0 < request.getContentLength()) {
                StringWriter writer = new StringWriter();
                InputStream requestInputStream = request.getInputStream();
                IOUtils.copy(requestInputStream, writer, UTF_8);
                body = writer.toString();
                if (null != body && !"".equals(body)) {
                    String[] valuesBody = new String[1];
                    valuesBody[0] = body;
                    paramsSorted.put(HMAC_BODY, valuesBody);
                }
            }
        }
        Iterator<Map.Entry<String, String[]>> entryIterator = paramsSorted.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        // Now lets create a huge string out of it to feed it into the hmac
        while (entryIterator.hasNext()) {
            Map.Entry<String, String[]> entry = entryIterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            // WARNING: we just ignore array notated input params! because the app does not work right now with array
            // request params.... or does not use it anywhere
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value[0]);
            if (entryIterator.hasNext()) {
                stringBuilder.append("&");
            }
        }
        if (null == apihost) {
            apihost = propertyService.getPropertyValue(ServerService.PROP_NAME_APPLICATION_SERVER_API);
        }
        String hmacinput = apihost + request.getPathInfo();
        if (!"".equals(stringBuilder.toString())) {
            hmacinput += "?" + stringBuilder.toString();
        }
        String hmacvariableindex = request.getHeader("x-retail-hmac-index");
        if (hmacvariableindex == null) {
            hmacvariableindex = "0";
        }
        // Ok, now we should have to fully qualified request param string
        LOGGER.debug("Hmac Param String is " + hmacinput);
        String calculatedHMAC;
        switch (hmacvariableindex) {
            case "2": {
                calculatedHMAC = HMACUtil.computeHmac(hmacinput, propertyService.getPropertyValue(PROP_NAME_SECRET2));
                break;
            }
            case "3": {
                calculatedHMAC = HMACUtil.computeHmac(hmacinput, propertyService.getPropertyValue(PROP_NAME_SECRET3));
                break;
            }
            case "4": {
                calculatedHMAC = HMACUtil.computeHmac(hmacinput, propertyService.getPropertyValue(PROP_NAME_SECRET4));
                break;
            }
            case "5": {
                calculatedHMAC = HMACUtil.computeHmac(hmacinput, propertyService.getPropertyValue(PROP_NAME_SECRET5));
                break;
            }
            default: {
                // Default hmac
                calculatedHMAC = HMACUtil.computeHmac(hmacinput, propertyService.getPropertyValue(PROP_NAME_SECRET1));
            }
        }
        LOGGER.debug("HMAC: " + calculatedHMAC);
        /*LOGGER.debug("http://dev.froso.de:8100/api/StartPage?s_timestamp=2014-03-25T18:08:29.278+0100");
        LOGGER.debug(
                HMACUtil.computeHmac(
                        "http://92.50.83.218:8100/api/StartPage?s_timestamp=2014-03-25T18:08:29.278+0100", SECRET_KEY
                )
        );

        LOGGER.debug("-------------->");
        LOGGER.debug(HMACUtil.computeHmac(hmacinput, SECRET_KEY));
        LOGGER.debug("<-------------");
        LOGGER.debug("------Received HMAC Value----->");
        LOGGER.debug(request.getParameter(HMAC_PROPERTY));
*/
        if (calculatedHMAC.equals(h_macValueInput)) {
            LOGGER.debug("-++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("HMAC GLEICH!");
            LOGGER.debug("-++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        } else {
            LOGGER.debug("-++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOGGER.debug("OH NO HMACS UNGLEICH! HACKING ATTEMP!!");
            LOGGER.debug("-++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            if (true || "SOFT".equals(propertyService.getPropertyValue(PROP_NAME_SECURITY_HMAC_MODE))) {
                //throw new HMACException("HMAC Validation Failure", hmacinput, calculatedHMAC);
                // BUILD OUTPUT;
                // Build response here!
                ResponseHandler handler = new ResponseHandler();
                handler.setStatus(ResultStatusEnumCode.ERROR_HMAC_VERIFICATION_FAILURE);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                handler.addMessage(calculatedHMAC);
                handler.addMessage("Generated Code should have been:");
                handler.addMessage(hmacinput);
                handler.addMessage("HMAC Info, hmac generated from:");
                String jsonString = objectMapper.writeValueAsString(handler.getResponseEntity().getBody());
                response.getWriter().write(jsonString);
                response.getWriter().flush();
                response.getWriter().close();
                // And return (without filtering...)
                return;
            } else {
                //throw new HMACException("HMAC Validation Failure", hmacinput, calculatedHMAC);
                // BUILD OUTPUT;
                // Build response here!
                ResponseHandler handler = new ResponseHandler();
                handler.setStatus(ResultStatusEnumCode.ERROR_HMAC_VERIFICATION_FAILURE);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                String jsonString = objectMapper.writeValueAsString(handler.getResponseEntity().getBody());
                response.getWriter().write(jsonString);
                response.getWriter().flush();
                response.getWriter().close();
                // And return (without filtering...)
                return;
            }
        }
        chain.doFilter(requestIn, response);
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service. This method is only
     * called once all threads within the filter's doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the doFilter method again on this instance of the
     * filter. <br><br>
     *
     * This method gives the filter an opportunity to clean up any resources that are being held (for example, memory,
     * file handles, threads) and make sure that any persistent state is synchronized with the filter's current state
     * in
     * memory.
     */

    @Override
    public void destroy() {

    }
}
