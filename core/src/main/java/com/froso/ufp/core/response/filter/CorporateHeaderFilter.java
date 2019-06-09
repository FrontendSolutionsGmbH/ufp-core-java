package com.froso.ufp.core.response.filter;

import com.froso.ufp.modules.core.templatesv1.model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.web.filter.*;

//@Component
public class CorporateHeaderFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporateHeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("doFilterInternal");
        //                 allow certain pathes to access without corporateid set
        if ("/".equals(request.getPathInfo())) {
            // Root allow as well
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/Admin")) {
            //
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/dev")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getPathInfo().startsWith("/" + TemplateV1.TYPE_NAME)) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
