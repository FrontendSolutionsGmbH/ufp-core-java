package com.froso.ufp.core.response;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.messaging.*;
import com.froso.ufp.core.response.request.*;
import org.springframework.http.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:58
 * <p>
 * Singleton Response Manager, accessible by all objects throughout the application, it collects changed data or at
 * least the main data that is requested
 * <p>
 * <p>
 *
 * @param <T> the type parameter
 * @param <Y> the type parameter
 */
public class ResponseHandlerTemplate3<T extends IDataObject, Y extends IResponseStatusTyped> {

    public static final String UTF_8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_CONTENT_TYPE_UTF8 = APPLICATION_JSON + ";charset=" + UTF_8;
    private RequestHelper requestHelper;
    private BackendResponseTemplate3<T, Y> response;
    private long startMilliSeconds;
    private MessageCollector messageCollector;


    public ResponseHandlerTemplate3(Y status) {

        init(status);
    }

    public ResponseHandlerTemplate3(HttpServletRequest request, Y status) {

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

    public void addResult(T input) {

        if (null != input) {
            response.addResult(input);
        }
    }

    private void init(Y status) {

        response = new BackendResponseTemplate3<>(status);
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

    public ResponseEntity<BackendResponseTemplate3<T, Y>> getResponseEntity() {

        return getResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<BackendResponseTemplate3<T, Y>> getResponseEntity(HttpStatus status) {

        HttpHeaders headers = getAccessControllAllowOriginHeaders();
        return new ResponseEntity<>(getResponse(), headers, status);
    }

    private BackendResponseTemplate3<T, Y> getResponse() {

        updateResponseWithDurationAndMessages();
        return response;
    }

    public void updateResponseWithDurationAndMessages() {

        if (null != response.getInfo()) {
            response.getInfo().setDurationMilliSeconds(System.currentTimeMillis() - startMilliSeconds);
        }
        //     response.setMessages(messageCollector.getMessages());
    }

    public void addMessage(String message) {

        messageCollector.addMessage(message);
    }

    public void addWarning(String message) {

        messageCollector.addMessageWarning(message);
    }

    public void addResult(List<T> listInput) {

        // Contain the List in a List IDataObject
        //DataObjectList list = new DataObjectList(listInput);
        response.addAll(listInput);
    }

    public void addResult(DataObjectList<T> listInput) {

        // Contain the List in a List IDataObject
        //DataObjectList list = new DataObjectList(listInput);
        response.addResult(listInput);
    }
}
