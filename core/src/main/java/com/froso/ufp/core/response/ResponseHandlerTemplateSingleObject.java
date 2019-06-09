package com.froso.ufp.core.response;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.binding.*;
import com.froso.ufp.core.response.messaging.*;
import com.froso.ufp.core.response.request.*;
import org.springframework.http.*;
import org.springframework.validation.*;

import javax.servlet.http.*;
import java.util.*;

public class ResponseHandlerTemplateSingleObject<T extends IDataObject> {

    public static final String UTF_8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_CONTENT_TYPE_UTF8 = APPLICATION_JSON + ";charset=" + UTF_8;
    private RequestHelper requestHelper;
    private BackendResponseTemplateSingleObject<T> response;
    private long startMilliSeconds;
    private MessageCollector messageCollector;

    public ResponseHandlerTemplateSingleObject() {

        init();
    }

    public ResponseHandlerTemplateSingleObject(HttpServletRequest request) {

        if (null != request) {
            requestHelper = new RequestHelper(request);
        }
        init();
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

    public long getStartMilliSeconds() {
        return startMilliSeconds;
    }

    public void setStartMilliSeconds(long startMilliSeconds) {
        this.startMilliSeconds = startMilliSeconds;
    }

    public MessageCollector getMessageCollector() {
        return messageCollector;
    }

    public void setMessageCollector(MessageCollector messageCollector) {
        this.messageCollector = messageCollector;
    }

    private void init() {

        response = new BackendResponseTemplateSingleObject<>();
        messageCollector = new MessageCollector();
        initialiseResponse();
    }

    private void initialiseResponse() {

        startMilliSeconds = System.currentTimeMillis();
    }

    @Deprecated
    private void addBindingResultMessages(BindingResult binding) {

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
        addResult((T) bindingResult);

    }

    public void addError(String message) {

        messageCollector.addMessageError(message);
    }

    public void addResult(T input) {

        if (null == input) {
            return;
        }
        response.setResult(input);
    }

    public String getApiUrl() {

        String result = "";
        if (null != requestHelper) {
            result = requestHelper.getApiUrl();
        }
        return result;
    }

    public void setStatus(ResultStatusEnumCode status) {

        response.getInfo().setStatus(status);
    }

    public ResponseEntity<BackendResponseTemplateSingleObject<T>> getResponseEntity() {

        return getResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<BackendResponseTemplateSingleObject<T>> getResponseEntity(HttpStatus status) {

        HttpHeaders headers = getAccessControllAllowOriginHeaders();
        return new ResponseEntity<>(getResponse(), headers, status);
    }

    private BackendResponseTemplateSingleObject<T> getResponse() {

        updateResponseWithDurationAndMessages();
        return response;
    }

    public void updateResponseWithDurationAndMessages() {

        response.getInfo().setDurationMilliSeconds(System.currentTimeMillis() - startMilliSeconds);
        //     response.setMessages(messageCollector.getMessages());
    }

    public void addResult(List<T> listInput) {

        // Contain the List in a List IDataObject
        //DataObjectList list = new DataObjectList(listInput);
        if (!listInput.isEmpty()) {
            response.setResult(listInput.get(0));
        }
    }

    public void addResult(IDataObjectList listInput) {

        // Contain the List in a List IDataObject
        //DataObjectList list = new DataObjectList(listInput);
//        response.addResult(listInput.getList());
    }

    public void addMessage(String message) {

        messageCollector.addMessage(message);
    }

    public void addWarning(String message) {

        messageCollector.addMessageWarning(message);
    }
}
