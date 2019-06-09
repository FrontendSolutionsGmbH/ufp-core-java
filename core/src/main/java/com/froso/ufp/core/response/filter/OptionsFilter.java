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
 * The type Options filter.
 */
@Component
@Order(300)
public class OptionsFilter extends OncePerRequestFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(OptionsFilter.class);


    @Autowired
    private IPropertyService propertyService;
    /**
     * Do filter internal.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws javax.servlet.ServletException the servlet exception
     * @throws javax.servlet.ServletException the servlet exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        LOGGER.info("doFilter");
        if ((null != request.getHeader("Access-Control-Request-Method")) && "OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Origin", propertyService.getPropertyValue(ServerService.CORS_SETTING_USER));
            response.setHeader("Access-Control-Allow-Headers",
                    "X-Requested-With,Origin,Content-Type, Accept," +
                            HeaderNames.HEADER_X_UFP_VERSION + "," +
                            HeaderNames.HEADER_X_UFP_CLIENT + "," +
                            HeaderNames.HEADER_X_UFP_REQUEST_INDEX + "," +
                            HeaderNames.HEADER_X_UFP_TOKEN + "," +
                            HeaderNames.HEADER_X_RETAIL_TIMESTAMP + "," +
                            HeaderNames.HEADER_X_RETAIL_HMAC);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
