package com.froso.ufp.core.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

public abstract class BaseReadOnlySecuredController<T extends IDataDocument> {
    public static final String CRUD_ADMIN_REPOSITORY = "CRUD Admin Repository";
    public static final String ENDPOINT = UFPConstants.ADMIN_FULL + "/" + "User" + "/{token}";
    protected RepositoryService<T> service;

    public BaseReadOnlySecuredController(RepositoryService<T> serviceIn) {

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
            @PathVariable
                    String elementId,
            @PathVariable("token")
                    String userId,
            HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<>(request);

        T element = service.findOne(elementId);
        manager.addResult(element);

        return manager.getResponseEntity();
    }

    @ApiOperation(value = "returns a list of elements",
            notes = "This endpoint serves for filtering and searching associated resources" +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplate<T>> readElements(
            @PathVariable("token")
                    String userId,
            HttpServletRequest request) {

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);

        List<T> elements = service.search(getSingleParameterValueMapFromRequest(request));
        if (elements.size() > 0) {
            manager.addResult(elements);
        }
        return manager.getResponseEntity();
    }

}
