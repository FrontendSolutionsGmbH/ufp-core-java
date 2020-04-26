package com.froso.ufp.core.controller;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.service.operations.*;
import com.froso.ufp.modules.core.session.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.mapreduce.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

public abstract class BaseFilterRepositoryController<T extends IDataDocument> {
    public static final String CRUD_ADMIN_REPOSITORY = "CRUD Admin Repository";
    public static final String ENDPOINT = UFPConstants.ADMIN_FULL + "/{token}";
    public static final String GROUP_ID = "groupingKeyFunction";
    private static final String DEFAULT_NEW = "newDefault";
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFilterRepositoryController.class);
    @Autowired
    protected RepositoryService<T> service;
    @Autowired
    private ObjectMapper objectMapper;
    private SessionService sessionService;

    /**
     * favoring constructor autowiring/dependency injection over field injection now, following style guide of spring
     */
    @Deprecated
    public BaseFilterRepositoryController() {
        LOGGER.debug("Using deprecated constructor, please use constructor based autowiring");
    }

    /**
     * use this for creation, the service and a objectmapper for loading/converting transfer data type to pojos
     *
     * @param service
     * @param objectMapper
     */
    public BaseFilterRepositoryController(RepositoryService<T> service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    /**
     * Gets single parameter value map from request.
     *
     * @param request the request
     * @return the single parameter value map from request
     */
    private static Map<String, String> getSingleParameterValueMapFromRequest(HttpServletRequest request) {
        Map<String, String[]> allRequestParams = request.getParameterMap();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : allRequestParams.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    /**
     * Gets single parameter value map object from request.
     *
     * @param request the request
     * @return the single parameter value map object from request
     */
    public static final Map<String, Object> getSingleParameterValueMapObjectFromRequest(HttpServletRequest request) {
        Map<String, String[]> allRequestParams = request.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : allRequestParams.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    private static Map<String, String> mergeMapButKeepFilter(Map<String, String> map1, Map<String, String> map2) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            result.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return result;
    }

    protected T enrichDefault(T data, String userId) {

        return data;

    }

    public abstract Map<String, String> filter(String userId);

    @Autowired(required = false)
    public void setDependencySessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }
//
//    /**
//     * Perform distinct response entity.
//     *
//     * @param userId  the user id
//     * @param request the request
//     * @return the response entity
//     */
//    @ApiOperation(value = "perform distinct operation",
//            notes = "<p>This method performs a $distinct mongo operation on resource </p> ")
//    @RequestMapping(value = "/distinct",
//            method = RequestMethod.GET)
//    @ResponseBody
//    public final ResponseEntity<BackendResponseTemplate<T>> performDistinct(
//            @PathVariable("token")
//                    String userId, HttpServletRequest request) {
//        Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
//        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
//        IDataObjectList<T> searchresult = service.searchPaged(allRequestParams);
//        manager.addResult(searchresult);
//        return manager.getResponseEntity();
//    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return service.getTypeName();
    }

    /**
     * Perform group 2 response entity.
     *
     * @param userId   the user id
     * @param property the property
     * @param method   the method
     * @param request  the request
     * @return the response entity
     */
    @ApiOperation(value = "perform group operation",
            notes = "<p>This endpoint performs a grouping query, all query filter parameters are valid, the 3 api method parameters explained</p><br>" +
                    "$method = the grouping operation, only COUNT may ommit the propertyValue field, for all other SUM, MIN, MAX, AVG a propertyValue field is used<br>" +
                    "$property = the property by which to group by, date fields accept suffixes: .$day .$month .$year to group by date/month/year<br>" +
                    "$propertyValue = the property to perform the grouping method on, for count this value is replaced by the constant 1, it has to be a numeric field<br>")
    @RequestMapping(value = "/group",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate<IDataDocument>> performGroup2(
            @PathVariable("token")
                    String userId,
            @RequestParam("$property")
                    String property,
            @RequestParam(value = "$propertyValue", required = false)
                    String propertyValue,
            @RequestParam("$method")
                    GroupByMethods method,

            HttpServletRequest request) {
        Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);

        allRequestParams.remove("$property");
        allRequestParams.remove("$propertyValue");
        allRequestParams.remove("$method");

        ResponseHandlerTemplate2SingleObject manager = new ResponseHandlerTemplate2SingleObject<>(request);
        GroupByResults searchresult = service.performGroup2(property, propertyValue, method, allRequestParams);
        if (searchresult != null) {
            //GroupByResult groupByResult = new GroupByResult();

            //groupByResult.setResult(searchresult);

            DataObjectMap mapResult = new DataObjectMap();
            mapResult.putAll(searchresult.getRawResults());
            manager.addResult(mapResult);
            manager.setStatus(ResultStatusEnumCode.OK);
        }
        return manager.getResponseEntity();
    }

    /**
     * Gets all Elements from the repository
     *
     * @param userId  the user id
     * @param request the request
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "get elements filtered",
            notes = "This method returns all elements in this collection.\n" +
                    "By providing TYPE's properties in the request the result is filtered by that rules, e.g.  \n" +
                    SwaggerDocSnippets.RESPONSE_START_HELP +
                    SwaggerDocSnippets.FILTER_RULE +
                    SwaggerDocSnippets.RESPONSE_END_HELP +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplate<T>> readElements(
            @PathVariable("token")
                    String userId, HttpServletRequest request) {
        Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);

        Map<String, String> merged = mergeMapButKeepFilter(allRequestParams, filter(userId));
//        allRequestParams.putAll(filter(userId));

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
        IDataObjectList<T> searchresult = service.searchPaged(merged);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * get a single element from the repository
     *
     * @param userId    the token
     * @param elementId the element id
     * @param request   the request
     * @return element element
     * @throws Exception the exception
     */
    @ApiOperation(value = "returns a single element",
            notes = "Returns the current element from the database." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "/{elementId}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> readElement(
            @PathVariable("token")
                    String userId,
            @PathVariable("elementId")
                    String elementId, HttpServletRequest request) {
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<>(request);
        if (DEFAULT_NEW.equals(elementId)) {

            T newDefault = service.createNewDefault();
            newDefault = enrichDefault(newDefault, userId);
            newDefault.getMetaData().setCreatorUserLink(new DataDocumentLink<>(userId));
            fillDefaultObject(newDefault);
            manager.addResult(newDefault);
        } else {
            // Get Element
            T simpleObject = service.findOne(elementId);
            try {
                // Put Transformed JsonMap Output into result
                manager.addResult(simpleObject);
            } catch (Exception e) {
                throw new ResourceException.ResourceNotAvailable("Problem with Resource ", service.getTypeName(), elementId, e);
            }
        }
        return manager.getResponseEntity();
    }

    /**
     * Gets user id from token.
     *
     * @param token the token
     * @return the user id from token
     */
    private String getUserIDFromToken(String token) {
        String result = token;
        if (null != sessionService) {
            result = sessionService.getUserIdForToken(token);
        }
        return result;
    }

    /**
     * store a json input element in the repository
     * <p>
     * if incoming object.id == null it is saved as copy!
     *
     * @param userId       the user id
     * @param elementId    the element id
     * @param dataInParsed the data in parsed
     * @param request      the request
     * @return response entity
     * @throws Exception the exception
     */
    @ApiOperation(value = "updates an element",
            notes = "Stores the send data in database for a single object." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_UPDATE_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "/{elementId}",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> updateElement(
            @PathVariable("token")
                    String userId,
            @PathVariable("elementId")
                    String elementId,
            @RequestBody(required = true)
                    T dataInParsed,
            HttpServletRequest request) {
        LOGGER.info("Saving Element " + service.getTypeName() + " id: " + elementId);
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<>(request);
        // check for default object
        if (DEFAULT_NEW.equals(elementId)) {
            // only set id when not there
            if (DEFAULT_NEW.equals(dataInParsed.getId()) || (dataInParsed.getId() == null)) {
                dataInParsed.setId(AbstractDocument.generateID());
            }
        } else {
            // do not allow setting of new object id !!!
            // dataInParsed.setId(elementId);

            // if operation was a change id operation, remove old element

            if (!elementId.equals(dataInParsed.getId()) && service.isExistant(dataInParsed.getId())) {
                // WARNING: CHANGING OF ID IS TRIVIALISED HERE
                // PERHAps THROW WARNING< TO AT LEAST FORCE 1 ATTEMPTS
                // PERHAPS MARKING THE ELMENT AS DELETED .... ?
                throw new UFPRuntimeException("Element already existant, cant create copy");
            }
        }

        if (DEFAULT_NEW.equals(elementId)) {
            // default creation handling
            //T newElement = service.createNewDefault();

            // check if id is already existant

            if (service.isExistant(dataInParsed.getId())) {
                throw new UFPRuntimeException(ResultStatusEnumCode.ERROR_MONGODB_DUPLICATEKEY);
            }

            dataInParsed.getMetaData().getCreatorUserLink().setId(getUserIDFromToken(userId));
            fillDefaultObject(dataInParsed);
            dataInParsed.getMetaData().getCreatorUserLink().setId(getUserIDFromToken(userId));
            dataInParsed = service.save(dataInParsed);
            manager.addResult(dataInParsed);
        } else {
            // copy handling, a copy is created when the "id" field of the body is empty
            T item = null;
            // Save Updated object
            // Enrich parsed object with non-automatically parsed data that belongs to object as well
            dataInParsed.getMetaData().getLastEditorUserLink().setId(userId);
            item = service.save(dataInParsed);
            manager.addResult(item);

        }

        // if operation was a change id operation, remove old element
        if (!elementId.equals(dataInParsed.getId()) && !elementId.equals(DEFAULT_NEW)) {
            // WARNING: CHANGING OF ID IS TRIVIALISED HERE
            // PERHAps THROW WARNING< TO AT LEAST FORCE 1 ATTEMPTS
            // PERHAPS MARKING THE ELMENT AS DELETED .... ?
            service.delete(service.findOne(elementId));
        }

        return manager.getResponseEntity();
    }

    /**
     * Delete element.
     *
     * @param userId    the user id
     * @param elementId the element id
     * @param request   the request
     * @return the response entity
     * @throws Exception the exception
     */
    @ApiOperation(value = "deletes a single element",
            notes = "Deletes a single elements of TYPE. All events associated to that tasks are executed." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_DELETE_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "/{elementId}",
            method = RequestMethod.DELETE)
    public final ResponseEntity<BackendResponseTemplateEmpty> deleteElement(
            @PathVariable(
                    "token")
                    String userId,
            @PathVariable(
                    "elementId")
                    String elementId, HttpServletRequest request) {
        ResponseHandlerTemplateEmpty manager = new ResponseHandlerTemplateEmpty(request);
        // Enrich parsed object with non-automatically parsed data that belongs to object as well
        T element = service.findOneBrute(elementId);
        service.delete(element);
        return manager.getResponseEntity();
    }

    /**
     * post on the resource itself creates a new object and returns it ;)ab
     *
     * @param userId  the user id
     * @param newItem the new item
     * @param request the request
     * @return response entity
     */
    @ApiOperation(value = "creates a new default element",
            notes = "Creates a new Element with Default values.<br/> Body may contain Objet data to be used when creating this object, otherwise a default object is created and returned.<br/>The result of this method is the new created object with a valid ObjectId" +
                    SwaggerDocSnippets.ERROR_NO_CREATE_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> createElement(
            @PathVariable("token")
                    String userId,
            @RequestBody(required = false)
                    T newItem,
            HttpServletRequest request) {
        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<>(request);
        T element = null;
        if (newItem == null) {
            element = service.createNewDefault();
        } else {
            element = newItem;
        }
        if (element != null) {
            element.getMetaData().getCreatorUserLink().setId(getUserIDFromToken(userId));
            service.save(element);
        }
        manager.addResult(element);
        return manager.getResponseEntity();
    }

    /**
     * Fill default object.
     * REMARK: this is identical to the core filldefault object in the service, controllers may add additional information that may
     * not be available in the core, or representing view special settings (when core is completely sourced out some day...)
     *
     * @param object the object
     */
    protected void fillDefaultObject(T object) {
        // template method to create a new object of type T
    }

}
