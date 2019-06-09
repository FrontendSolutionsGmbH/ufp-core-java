package com.froso.ufp.core.response;

import javax.servlet.*;
import java.io.*;

/**
 * Created by alex on 04.02.14.
 */
class CharacterEncodingFilterAlex implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // required for impl
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // required for impl
    }
}
