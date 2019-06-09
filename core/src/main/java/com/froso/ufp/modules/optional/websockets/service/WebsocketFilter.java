package com.froso.ufp.modules.optional.websockets.service;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.codec.digest.*;
import org.slf4j.*;
import org.springframework.beans.factory.config.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

@Document(collection = QueueEmail.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)

//@Component   whasts diz? obviously wrong pckage, disallow autodiscovery
public class WebsocketFilter
        implements Filter {


    public static final Logger LOGGER = LoggerFactory.getLogger(WebsocketFilter.class);

//    public static final String SECURITY_PREFIX = ")]}',\n";


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
//
//    private List<KeyValueItem> headersforRequest(HttpServletRequest request) {
//        List<KeyValueItem> map = new ArrayList<>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            KeyValueItem item = new KeyValueItem();
//            item.setKey(key);
//            item.setValue(value);
//            map.add(item);
//        }
//        return map;
//    }
//
//    private List<KeyValueItem> headersforResponse(HttpServletResponse request) {
//        List<KeyValueItem> map = new ArrayList<>();
//        Collection<String> headerNames = request.getHeaderNames();
//        for (String key : headerNames) {
//            KeyValueItem item = new KeyValueItem();
//            String value = request.getHeader(key);
//            item.setKey(key);
//            item.setValue(value);
//            map.add(item);
//        }
//        return map;
//    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        DateTime start = DateTime.now();
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
//        DateTime end = DateTime.now();
        HttpServletRequest httpServletRequest = null;
//        String responseContentOriginal = new String(responseWrapper.getDataStream(), StandardCharsets.UTF_8);
//        String responseContent = responseContentOriginal;

        ((HttpServletResponse) response).setStatus(101);
        String magicNumber = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        String wbSocketKey = httpServletRequest.getHeader("Sec-WebSocket-Key");
        byte[] temp1 = DigestUtils.sha1(wbSocketKey + magicNumber);
        String result = new String(org.apache.commons.net.util.Base64.encodeBase64(temp1));
        ((HttpServletResponse) response).addHeader("Upgrade", "websocket");
        ((HttpServletResponse) response).addHeader("Connection", "Upgrade");
        ((HttpServletResponse) response).addHeader("Sec-WebSocket-Accept", result);
        response.getOutputStream().write(responseWrapper.getDataStream());
    }
}
