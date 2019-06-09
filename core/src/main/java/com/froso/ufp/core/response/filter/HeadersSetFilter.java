package com.froso.ufp.core.response.filter;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
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
 * The type Headers set filter.
 */
@Component
@Order(-200)
public class HeadersSetFilter
        extends OncePerRequestFilter {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(HeadersSetFilter.class);

    @Autowired
    private IPropertyService propertyService;

    /**
     * Do filter internal.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws ServletException the servlet exception
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

//        LOGGER.info("doFilter");
//        LOGGER.info("TRANSLATED PATH_REGISTER IS " + request.getPathTranslated());


        String path = request.getPathTranslated();
        if (null == path) {

            path = request.getRequestURI();

        }


        response.setHeader("Access-Control-Allow-Origin", propertyService.getPropertyValue(ServerService.CORS_SETTING_USER));
        if (request.isSecure()) {
            // add strict if https request
            response.setHeader(HeaderNames.HEADER_STRICT_TRANSPORT, "max-age=31536000; includeSubDomains; preload");
        }

        response.setHeader(HeaderNames.HEADER_EXPOSE_HEADERS, HeaderNames.HEADER_X_RETAIL_HMAC);

        //todo: fixme: make it optional response.setHeader("X-Frame-Options", "DENY");

        // special check for ie8   on export database table download
        if (!((request.getPathTranslated() != null) && (request.getPathTranslated().contains("export"))
                && (request.getHeader("user-agent").contains("MSIE 8.0")))
        ) {


            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        }
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Expires", "0"); // Proxies
        filterChain.doFilter(request, response);

    }
}
