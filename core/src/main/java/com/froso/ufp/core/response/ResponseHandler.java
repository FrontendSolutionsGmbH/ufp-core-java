package com.froso.ufp.core.response;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.response.binding.*;
import com.froso.ufp.core.response.messaging.*;
import com.froso.ufp.core.response.request.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.http.*;
import org.springframework.validation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:58
 * <p>
 * Singleton Response Manager, accessible by all objects throughout the application, it collects changed data or at
 * least the main data that is requested
 * <p>
 * <p>
 */
public class ResponseHandler implements IResponseHandler {

    /**
     * The constant UTF_8.
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * The constant APPLICATION_JSON.
     */
    public static final String APPLICATION_JSON = "application/json";
    /**
     * The constant APPLICATION_XML.
     */
    public static final String APPLICATION_XML = "application/xml";
    /**
     * The constant APPLICATION_CONTENT_TYPE_UTF8.
     */
    public static final String APPLICATION_CONTENT_TYPE_UTF8 = APPLICATION_JSON + ";charset=" + UTF_8;
    private RequestHelper requestHelper;
    private BackendResponse response;
    private long startMilliSeconds;
    private MessageCollector messageCollector;

    /**
     * Constructor, initialising the response object
     */
    public ResponseHandler() {

        init();
    }

    /**
     * Constructor Response handler.
     *
     * @param request the request
     */
    public ResponseHandler(HttpServletRequest request) {

        if (null != request) {
            requestHelper = new RequestHelper(request);
        }
        init();
    }

    /**
     * for any ressource that is not using trhe responsehandler object, this method helps to set our headers
     *
     * @param response the response
     */
    public static void setDefaultHeaders(HttpServletResponse response) {

        // WARNING: DRY violation, close to eachother, but still a violation
        //   response.addHeader("Content-Type", "application/json;charset=UTF-8");
        // disallow browser caching

    }

    /**
     * Gets access controll allow origin headers.
     *
     * @return the access controll allow origin headers
     */
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

    private void init() {

        response = new BackendResponse();
        messageCollector = new MessageCollector();
        initialiseResponse();
    }

    private void initialiseResponse() {

        startMilliSeconds = System.currentTimeMillis();
    }

    /**
     * transforms a binding result to be used as result object *fixme: move to another location
     *
     * @param binding the binding
     */
    public void addBindingResultMessages(BindingResult binding) {

        List<ObjectError> errors = binding.getAllErrors();
        UfpBindingResult bindingResult = new UfpBindingResult();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                UfpBindingResultItem bindingResultItem = new UfpBindingResultItem();
                bindingResultItem.setFieldName(fieldError.getField());
                bindingResultItem.setMessage(fieldError.getDefaultMessage());
                bindingResultItem.setRejectedValue(fieldError.getRejectedValue());
                bindingResult.getBindingResults().add(bindingResultItem);
            } else {
                // Output Object Errors as error message
                addError(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        addResult(bindingResult);
    }

    /**
     * Add error.
     *
     * @param message the message
     */
    public void addError(String message) {

        messageCollector.addMessageError(message);
    }

    /**
     * any possible result that shall be transported to the client is collected here, the main idea is to provide a
     * method for providing more than just a single response object!
     *
     * @param input the input
     */
    public void addResult(Object input) {

        if (null == input) {
            return;
        }
        response.addResult(input);
    }

    /**
     * Gets api url.
     *
     * @return the api url
     */
    public String getApiUrl() {

        String result = "";
        if (null != requestHelper) {
            result = requestHelper.getApiUrl();
        }
        return result;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(IResultStatusEnumCode status) {

        response.getInfo().setStatus(status);
    }

    /**
     * Gets response entity.
     *
     * @return the response entity
     */
    public ResponseEntity getResponseEntity() {

        return getResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets response entity.
     *
     * @param status the status
     * @return the response entity
     */
    public ResponseEntity getResponseEntity(HttpStatus status) {

        HttpHeaders headers = getAccessControllAllowOriginHeaders();
        return new ResponseEntity<>(getResponse(), headers, status);
    }

    /**
     * here a response object is returned
     *
     * @return
     */
    private BackendResponse getResponse() {

        updateResponseWithDurationAndMessages();
        return response;
    }

    /**
     * Update response with duration and messages.
     */
    public void updateResponseWithDurationAndMessages() {

        response.getInfo().setDurationMilliSeconds(System.currentTimeMillis() - startMilliSeconds);
        response.getInfo().setMessages(messageCollector.getMessages());
    }

    /**
     * any possible result that shall be transported to the client is collected here, the main idea is to provide a
     * method for providing more than just a single response object!
     *
     * @param listInput a list that gets contained in a DataObjectList Element
     */
    public void addResult(List<?> listInput) {

        // Contain the List in a List IDataObject
        IDataObjectList list = new DataObjectList(listInput);
        response.addResult(list);
    }


    /**
     * Add message.
     *
     * @param message the message
     */
    @Override
    public void addMessage(String message) {

        messageCollector.addMessage(message);
    }

    /**
     * Add warning.
     *
     * @param message the message
     */
    public void addWarning(String message) {

        messageCollector.addMessageWarning(message);
    }
}
