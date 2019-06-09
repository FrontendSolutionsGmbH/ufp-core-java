package com.froso.ufp.modules.core.requestlogging.service;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.response.filter.*;
import com.froso.ufp.modules.core.requestlogging.model.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

@Component
public class RequestLogFilter
        implements Filter {

    public static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);


    @Override
    public void destroy() {
        // ...
    }

    @Override
    public void init(FilterConfig filterConfig) {
        //
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext
                (servletContext);
        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(this);
    }

    private List<KeyValueItem> headersforRequest(HttpServletRequest request) {
        List<KeyValueItem> map = new ArrayList<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            KeyValueItem item = new KeyValueItem();
            item.setKey(key);
            item.setValue(value);
            map.add(item);
        }
        return map;
    }

    private List<KeyValueItem> headersforResponse(HttpServletResponse request) {
        List<KeyValueItem> map = new ArrayList<>();
        Collection<String> headerNames = request.getHeaderNames();
        for (String key : headerNames) {
            KeyValueItem item = new KeyValueItem();
            String value = request.getHeader(key);
            item.setKey(key);
            item.setValue(value);
            map.add(item);
        }
        return map;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = null;
// for now we only want to log api requests
        if (request instanceof HttpServletRequest) {
            httpServletRequest = (HttpServletRequest) request;
        }
//        if (httpServletRequest.getPathInfo().startsWith("/" + UFPConstants.API)
//                && !httpServletRequest.getMethod().equals("OPTIONS")) {

//            RequestLogging requestLoggingModel = new RequestLogging();
            DateTime start = DateTime.now();
//            ((HttpServletResponse) response).addHeader(HeaderNames.HEADER_X_UFP_REQUESTLOG, requestLoggingModel.getId());
      //      ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
            chain.doFilter(request, response);
            DateTime end = DateTime.now();
//            requestLoggingModel.setDurationMilliSeconds(end.getMillis() - start.getMillis());

//            if (request instanceof HttpServletRequest) {
//                requestLoggingModel.getRequestData().setHeaders(headersforRequest(httpServletRequest));
//            }
              if (httpServletRequest != null) {
                  LOGGER.info("LOG {}ms {} {} --> {}",end.getMillis() - start.getMillis(),httpServletRequest.getMethod(),httpServletRequest.getRequestURI(),((HttpServletResponse) response).getStatus());
              }

//            requestLoggingService.save(requestLoggingModel);
         //   response.getOutputStream().write(responseWrapper.getDataStream());
//        } else {
//            // normal chain filtering without logging otherwise
//            chain.doFilter(request, response);
//
//        }
    }
}
