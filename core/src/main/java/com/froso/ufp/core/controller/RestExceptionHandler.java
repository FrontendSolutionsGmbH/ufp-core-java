package com.froso.ufp.core.controller;

import com.froso.ufp.core.domain.validation.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.response.binding.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.core.user.exception.*;
import org.apache.commons.lang.exception.*;
import org.slf4j.*;
import org.springframework.beans.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.validation.*;
import org.springframework.web.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.multipart.support.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de)leinhuis (ck@froso.de) Date: 13.11.13 Time:
 * 21:58 global application wide error handler
 */
@ControllerAdvice
class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    /**
     * The constant PROP_NAME_SECURITY_REPORTING_STACKTRACE.
     */
    public static final String PROP_NAME_SECURITY_REPORTING_STACKTRACE = "ufp.core.security.reporting.stacktrace";
    /**
     * The constant PROPERTY_SECURITY_REPORTING_STACKTRACE.
     */
    public static final String PROPERTY_SECURITY_REPORTING_STACKTRACE = PROP_NAME_SECURITY_REPORTING_STACKTRACE;
    private static final String APPLICATION_EXCEPTION_OCCURED = "Application Exception Occured:";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * Application resource not found exception response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(MainResourceNotFoundException.class)
    protected ResponseEntity<Object> applicationResourceNotFoundException(MainResourceNotFoundException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE);
        // no stacktrace needed here
        responseHandler.addError(mainException.getMessage());

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Application resource not found exception response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(UFPRuntimeException.class)
    protected ResponseEntity<Object> ufpRuntimeException(UFPRuntimeException mainException, WebRequest request) {
        LOGGER.info("Ufp Runtime exception handler", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(mainException.getResultStatus());
        // no stacktrace needed here
//        responseHandler.addError(mainException.getMessage());

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Catching of all otherwise not handled Exceptions!
     *
     * @param mainException the main exception
     * @param request       the request
     * @return response entity
     */

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException mainException, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_UNMAPPED_REQUEST);
        // no stacktrace needed here
        responseHandler.addError(mainException.getMessage());

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    private ResponseEntity<Object> createResponseEntity(ResponseHandler responseHandler, Throwable mainException, WebRequest request, HttpStatus status) {

        LOGGER.debug(APPLICATION_EXCEPTION_OCCURED, mainException);
        ResponseEntity<Object> responseEntity;

        if (PropertyServiceRepositoryImpl.getPropertyBoolean(PROP_NAME_SECURITY_REPORTING_STACKTRACE)) {
            responseHandler.addError(mainException.getMessage());
            responseHandler.addError(ExceptionUtils.getRootCauseMessage(mainException));
        }
        try {
            /*
            this try block is made for any error that might be caused due to self-calling the jackson
            mapper, beside of this, this is what this block does:

            - create application wide response handler object to collect information about the error
            - hand code http header to application/json
            - generate ResponseEntity getOutput by calling exception handler to map the exception to a spring
            response/server respon se status code

            side note: all application relevant information is contained in the json response !

             */
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-type", ResponseHandler.APPLICATION_CONTENT_TYPE_UTF8);

            if (mainException instanceof Exception) {

                responseEntity = handleExceptionInternal((Exception) mainException, responseHandler.getResponseEntity().getBody(), httpHeaders, status, request);
            } else {
                responseEntity = new ResponseEntity(responseHandler.getResponseEntity().getBody(), new HttpHeaders(), HttpStatus.OK);
            }
        } catch (Exception exception) {
            LOGGER.error("Error Occured in Error Handler ;) " + exception.getMessage(), exception);
          /*  StringBuilder stringBuffer = new StringBuilder();

            if (PropertyServiceRepositoryImpl.getPropertyBoolean(PROP_NAME_SECURITY_REPORTING_STACKTRACE)) {
                stringBuffer.append("A Fatal Error Occured \n While Handling the Error an Exception Occured\n");
                stringBuffer.append(exception.getMessage());
                stringBuffer.append("\nOriginal Message was:\n");
                stringBuffer.append(mainException.getMessage());
                stringBuffer.append("\nAnd for your pleasure originiating Stacktrace:\n");
                for (int i = 0; i < mainException.getStackTrace().length; i++) {
                    stringBuffer.append(mainException.getStackTrace()[i].toString());
                    stringBuffer.append("\n");
                }
            } */

            if (mainException instanceof Exception) {

                responseEntity = handleExceptionInternal((Exception) mainException, responseHandler.getResponseEntity().getBody(), new HttpHeaders(), HttpStatus.OK, request);
            } else {
                responseEntity = new ResponseEntity(responseHandler.getResponseEntity().getBody(), new HttpHeaders(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    /**
     * Validation exception response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<BackendResponseTemplateSingleObject<ValidationResults>> validationException(ValidationException mainException, WebRequest request) {

        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<ValidationResults>(null);
        LOGGER.info("Global Exception handler ", mainException);
        manager.setStatus(ResultStatusEnumCode.ERROR_VALIDATION);

        ValidationResults validationResults = new ValidationResults();
        for (UfpBindingResultItem item : mainException.getBindingResult().getBindingResults()) {

            ValidationResult result = new ValidationResult();
            result.setMessage(item.getMessage());
            result.setFieldName(item.getFieldName());

            validationResults.getValidationResults().add(result);
        }

        manager.addResult(validationResults);
        return manager.getResponseEntity();
    }

    /**
     * Validation exception 2 response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(ValidationException2.ValidationException.class)
    protected ResponseEntity<BackendResponseTemplateSingleObject<ValidationResults>> validationException2(ValidationException2.ValidationException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);

        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<ValidationResults>(null);
        manager.setStatus(ResultStatusEnumCode.ERROR_VALIDATION);
        ValidationResults validationResults = new ValidationResults();
        for (ObjectError error : mainException.getBindingResult().getAllErrors()) {

            // responseHandler.addResult(mainException.getBindingResult());
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                ValidationResult result = new ValidationResult();
                result.setMessage(fieldError.getDefaultMessage());
                result.setFieldName(fieldError.getField());

                validationResults.getValidationResults().add(result);

            }

        }
        manager.addResult(validationResults);
        return manager.getResponseEntity();
        //    return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle token invalid.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(UserTokenException.InvalidRole.class)
    protected ResponseEntity<Object> handleTokenInvalidRole(Exception mainException, WebRequest request) {
        LOGGER.info("Global Exception handler ", mainException);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_INVALID);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle token invalid response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(UserTokenException.InvalidToken.class)
    protected ResponseEntity<Object> handleTokenInvalid(Exception mainException, WebRequest request) {
        LOGGER.info("Global Exception handler ", mainException);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_INVALID);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle token expired response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ExceptionHandler(UserTokenException.TokenExpired.class)
    protected ResponseEntity<Object> handleTokenExpired(Exception mainException, WebRequest request) {
        LOGGER.info("Global Exception handler ", mainException);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_TOKEN_EXPIRED);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Conflict.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     * @throws IOException the io exception
     */
    @ExceptionHandler(ImageGenerationException.class)
    protected ResponseEntity<Object> handleImageGenerationException(ImageGenerationException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_IMAGE_CREATION);

        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler, mainException);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle remote host changed response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UserTokenException.RemoteHostChanged.class)
    protected ResponseEntity<Object> handleRemoteHostChanged(UserException ex, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.REMOTE_HOST_CHANGED);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    /**
     * Handle invalid argument.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleInvalidArgument(IllegalArgumentException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_JAVA_INVALID_ARGUMENT);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle invalid argument.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<Object> handleInvalidArgument(NumberFormatException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_JAVA_INVALID_ARGUMENT);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle token expired.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */

    /**
     * Handle user exception invalid auth_email.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(UserException.UserInvalidPasswordCombination.class)
    protected ResponseEntity<Object> handleUserExceptionInvalidPassword(UserException.UserInvalidPasswordCombination mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_LOGIN_USER_PASSWORD_NAME_NOT_VALID);
        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle user exception blocked.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(UserException.UserBlocked.class)
    protected ResponseEntity<Object> handleUserExceptionBlocked(UserException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_USER_IS_BLOCKED);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle user exception in active user.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(UserException.UserNotActive.class)
    protected ResponseEntity<Object> handleUserExceptionInActiveUser(UserException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_USER_REQUIRES_ACTIVATION);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle user exception duplicate user.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(UserException.DuplicateUser.class)
    protected ResponseEntity<Object> handleUserExceptionDuplicateUser(UserException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_CREATE_USER_ALREADY_EXISTANT);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle user exception non deletable due to active order response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(UserException.UserHasActiveOrderAndCanNotBeDeleted.class)
    protected ResponseEntity<Object> handleUserExceptionNonDeletableDueToActiveOrder(UserException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_USER_HAS_ACTIVE_ORDER);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Catching of all otherwise not handled Exceptions!
     *
     * @param mainException the main exception
     * @param request       the request
     * @return response entity
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.FATAL_ERROR);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler, mainException);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * utility function for filling the response object with additional exception information!
     *
     * @param responseHandler
     * @param mainException
     */
    private void fillResponseHandlerDataWithStacktraceAndStuff(IResponseHandler responseHandler, Throwable mainException) {
        LOGGER.error("Error Occured ", mainException);
        responseHandler.addMessage(
                "Exception: " + mainException.getMessage());
        if (mainException.getCause() != null) {
            responseHandler.addMessage(
                    "Cause: " + mainException.getCause());
        }
        if (ExceptionUtils.getRootCause(mainException) != null) {
            responseHandler.addMessage("Root: " + ExceptionUtils.getRootCause(mainException).getMessage());
            if (PropertyServiceRepositoryImpl.getPropertyBoolean(PROPERTY_SECURITY_REPORTING_STACKTRACE)) {
                for (int i = 0; i < ExceptionUtils.getRootCause(mainException).getStackTrace().length; i++) {
                    responseHandler.addMessage(ExceptionUtils.getRootCause(mainException).getStackTrace()[i].toString());
                }
            }
        }
        responseHandler.addMessage("Main: " + mainException);
        if (PropertyServiceRepositoryImpl.getPropertyBoolean(PROPERTY_SECURITY_REPORTING_STACKTRACE)) {
            for (int i = 0; i < mainException.getStackTrace().length; i++) {
                responseHandler.addMessage(mainException.getStackTrace()[i].toString());
            }

        }
    }

//
//    @Override
//    protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex,
//                                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//
//        LOGGER.info("Global Exception handler ", ex);
//        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
//        responseHandler2.getInfo().setStatus(SpringMappedResults.NO_SUCH_REQUEST_HANDLINGMETHOD);
//        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
//        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
//    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_PREQUEST_METHOD_NOT_SUPPORTED);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_MEDIATYPE_NOT_SUPPORTED);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_MEDIATYPE_NOT_ACCEPTABLE);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.MISSING_PATHVARIABLE);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);

        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.SERVLET_REQUEST_BINDING_EXCEPTION);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);

        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.CONVERSION_NOT_SUPPORTED);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.TYPE_MISMATCH);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_MESSAGE_NOT_WRITABLE);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.METHOD_ARGUMENT_NOT_VALID);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);

        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.MISSING_SERVLET_REQUESTPART);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.BIND_EXCEPTION);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.info("Global Exception handler ", ex);
        BackendResponseTemplate2Typed responseHandler2 = new BackendResponseTemplate2Typed();
        responseHandler2.getInfo().setStatus(SpringMappedResults.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler2.getInfo(), ex);
        return handleExceptionInternal(ex, responseHandler2, new HttpHeaders(), HttpStatus.OK, request);
    }

    /**
     * Handle exception conflict response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptionAnyOther(Exception mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.FATAL_ERROR);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler, mainException);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle exception memory response entity.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(OutOfMemoryError.class)
    protected ResponseEntity<Object> handleExceptionMemory(OutOfMemoryError mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.FATAL_ERROR);
        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler, mainException);

        LOGGER.error("--- OUT OF MEMORY HANDLER END ---");

        LOGGER.error("Out of Memory Exception occured ");
        LOGGER.error("System Memory Information:");
        if (PropertyServiceRepositoryImpl.getPropertyBoolean(PROP_NAME_SECURITY_REPORTING_STACKTRACE)) {

            LOGGER.error("Total Memory: " + Runtime.getRuntime().totalMemory() / 1024 + " MB");
            LOGGER.error("Max Memory  : " + Runtime.getRuntime().maxMemory() / 1024 + " MB");
            LOGGER.error("Free Memory : " + Runtime.getRuntime().freeMemory() / 1024 + " MB");
            LOGGER.error("Used : " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " MB");

            LOGGER.error("Exception Message : ");
            LOGGER.error(ExceptionUtils.getMessage(mainException));
            LOGGER.error("Root Cause Message: ");
            LOGGER.error(ExceptionUtils.getRootCauseMessage(mainException));
            LOGGER.error("Ful Stack trace: ");
            LOGGER.error(ExceptionUtils.getFullStackTrace(mainException));

            LOGGER.error("MainException: ");
            LOGGER.error("Exception Handler:", mainException);
            LOGGER.error("Root Cause Exception: ");
        }
        LOGGER.error("--- OUT OF MEMORY HANDLER END ---");
        // for any error we execute the garbage collection, experimental for fixing some runtime status issues
        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle hMAC verification failure.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(HMACException.class)
    protected ResponseEntity<Object> handleHMACVerificationFailure(HMACException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_HMAC_VERIFICATION_FAILURE);
        responseHandler.addMessage(mainException.getHmacCode());
        responseHandler.addMessage("Generated Code should have been:");
        responseHandler.addMessage(mainException.getHmacIn());
        responseHandler.addMessage("HMAC Info, hmac generated from:");

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }

    /**
     * Handle mongo conflict network.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
/*
    Mongo DB Exceptions
     *
    @ResponseBody
    @ExceptionHandler(MongoException.Network.class)
    protected ResponseEntity<Object> handleMongoConflictNetwork(MongoException mainException, WebRequest request) {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setInfo(ResultStatusEnumCode.ERROR_MONGODB_NETWORK);


        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }
*/
    /*
    END OF MONGO DB EXCEPTIONS
     */

    /*
    Resource Exceptions
     */

    /**
     * Handle mongo conflict cursor.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity

     @ResponseBody
     @ExceptionHandler(MongoException.CursorNotFound.class) protected ResponseEntity<Object> handleMongoConflictCursor(MongoException mainException, WebRequest request) {

     ResponseHandler responseHandler = new ResponseHandler();
     responseHandler.setInfo(ResultStatusEnumCode.ERROR_MONGODB_CURSORNOTFOUND);


     return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
     }
     */
    /**
     * Handle mongo conflict duplicate key.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    protected ResponseEntity<Object> handleMongoConflictDuplicateKey(org.springframework.dao.DuplicateKeyException mainException, WebRequest request) {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_MONGODB_DUPLICATEKEY);

        fillResponseHandlerDataWithStacktraceAndStuff(responseHandler, mainException);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);

    }

    /**
     * Handle resource exception not available.
     *
     * @param mainException the main exception
     * @param request       the request
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(ResourceException.ResourceNotAvailable.class)
    protected ResponseEntity<Object> handleResourceExceptionNotAvailable(ResourceException mainException, WebRequest request) {

        LOGGER.info("Global Exception handler ", mainException);
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE);

        return createResponseEntity(responseHandler, mainException, request, HttpStatus.OK);
    }
}

