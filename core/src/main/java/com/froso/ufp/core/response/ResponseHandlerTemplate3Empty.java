package com.froso.ufp.core.response;

import com.froso.ufp.core.response.messaging.*;
import com.froso.ufp.core.response.request.*;
import javax.servlet.http.*;
import org.springframework.http.*;

public class ResponseHandlerTemplate3Empty<Y extends IResponseStatusTyped> {

    public static final String UTF_8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_CONTENT_TYPE_UTF8 = APPLICATION_JSON + ";charset=" + UTF_8;
    private RequestHelper requestHelper;
    private BackendResponseTemplate3Empty<Y> response;
    private long startMilliSeconds;
    private MessageCollector messageCollector;


    public ResponseHandlerTemplate3Empty(Y status) {

        init(status);
    }

    public ResponseHandlerTemplate3Empty(HttpServletRequest request, Y status) {

        if (null != request) {
            requestHelper = new RequestHelper(request);
        }
        init(status);
    }

    public static void setDefaultHeaders(HttpServletResponse response) {

        // WARNING: DRY violation, close to eachother, but still a violation
        //   response.addHeader("Content-Type", "application/json;charset=UTF-8");
        // disallow browser caching

    }

    public static HttpHeaders getAccessControllAllowOriginHeaders() {
        // WARNING HEADER ARE SET IN FILTERS!)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_CONTENT_TYPE_UTF8);
        // disallow browser caching
        //    headers.add("X-Frame-Options", "DENY");
        //   headers.add("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        //  headers.add("Pragma", "no-cache"); // HTTP 1.0
        // headers.add("Expires", "0"); // Proxies

        return headers;
    }

    private void init(Y status) {

        response = new BackendResponseTemplate3Empty<Y>(status);
        messageCollector = new MessageCollector();
        initialiseResponse();
    }

    private void initialiseResponse() {

        startMilliSeconds = System.currentTimeMillis();
    }


    public void addError(String message) {

        messageCollector.addMessageError(message);
    }

    public String getApiUrl() {

        String result = "";
        if (null != requestHelper) {
            result = requestHelper.getApiUrl();
        }
        return result;
    }

    public ResponseEntity<BackendResponseTemplate3Empty<Y>> getResponseEntity() {

        return getResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<BackendResponseTemplate3Empty<Y>> getResponseEntity(HttpStatus status) {

        HttpHeaders headers = getAccessControllAllowOriginHeaders();
        return new ResponseEntity<>(getResponse(), headers, status);
    }

    private BackendResponseTemplate3Empty<Y> getResponse() {

        updateResponseWithDurationAndMessages();
        return response;
    }

    public void updateResponseWithDurationAndMessages() {

        response.getInfo().setDurationMilliSeconds(System.currentTimeMillis() - startMilliSeconds);
        //     response.setMessages(messageCollector.getMessages());
    }

    public void addMessage(String message) {

        messageCollector.addMessage(message);
    }

    public void addWarning(String message) {

        messageCollector.addMessageWarning(message);
    }
}
