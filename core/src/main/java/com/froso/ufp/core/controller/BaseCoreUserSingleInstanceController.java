package com.froso.ufp.core.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

public abstract class BaseCoreUserSingleInstanceController<T extends IDataDocumentWithCoreUserLink> {
    public static final String CRUD_ADMIN_REPOSITORY = "CRUD Admin Repository";
    public static final String ENDPOINT = UFPConstants.ADMIN_FULL + "/" + "User" + "/{token}";
    protected RepositoryService<T> service;

    public BaseCoreUserSingleInstanceController(RepositoryService<T> serviceIn) {
        service = serviceIn;
    }

    public static final Map<String, String> getSingleParameterValueMapFromRequest(HttpServletRequest request) {

        Map<String, String[]> allRequestParams = request.getParameterMap();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : allRequestParams.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    public String getTypeName() {
        return service.getTypeName();
    }

    @ApiOperation(value = "returns a single element associated with this account",
            notes = "This controller implements a 1:1 relationship to UFP-CoreUser instances and project defined objects" +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> readElement(
            @PathVariable
                    String token, HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<>(request);

        List<T> elements = service.findByKeyValue("coreUser.id", token);
        if (elements.size() > 0) {
            manager.addResult(service.findByKeyValue("coreUser.id", token).get(0));
        }
        return manager.getResponseEntity();
    }

    /**
     * store a json input element in the repository
     * <p>
     * if incoming object.id == null it is saved as copy!
     *
     * @param token   the token
     * @param element the element
     * @param request the request
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
    @RequestMapping(value = "",
            method = RequestMethod.POST)

    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> updateElement(
            @PathVariable("token")
                    String token,
            @RequestBody
                    T element,
            HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<>(request);

        T itemInDB = service.findByKeyValue("coreUser.id", token).get(0);
        element.setId(itemInDB.getId());
        // dont allow incoming metadata
        // todo:fixme: store lastchange data
        element.setMetaData(itemInDB.getMetaData());
        // dont allow incoming coreuser id
        element.getCoreUser().setId(token);

        manager.addResult(service.save(element));
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
