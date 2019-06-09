package com.froso.ufp.modules.optional.userresourcefilter.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.userresourcefilter.model.*;
import com.froso.ufp.modules.optional.userresourcefilter.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

//@Controller
@RequestMapping(UFPAuthenticateConstants.SESSION_PATH + "/" + com.froso.ufp.modules.optional.userresourcefilter.model.UserResourceFilterModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = com.froso.ufp.modules.optional.userresourcefilter.model.UserResourceFilterModel.TYPE_NAME)
public class UserResourceFilterController {

    private final UserResourceFilterService userResourceFilterService;

    @Autowired
    public UserResourceFilterController(UserResourceFilterService userResourceFilterService) {

        this.userResourceFilterService = userResourceFilterService;

    }

    /**
     * Gets all Elements from the repository
     *
     * @param userId       the user id
     * @param resourceName the resource name
     * @param request      the request
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "get elements filtered",
            notes = "This method returns all elements in this collection.\n" +
                    "By providing TYPE's properties in the request the result is filtered by that rules, e.g.  \n" +
                    com.froso.ufp.core.response.SwaggerDocSnippets.RESPONSE_START_HELP +
                    com.froso.ufp.core.response.SwaggerDocSnippets.FILTER_RULE +
                    com.froso.ufp.core.response.SwaggerDocSnippets.RESPONSE_END_HELP +
                    com.froso.ufp.core.response.SwaggerDocSnippets.RESPONSE_START +
                    com.froso.ufp.core.response.SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    com.froso.ufp.core.response.SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    com.froso.ufp.core.response.SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "/{resourceName}",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate<UserResourceFilterModel>> readElements(
            @PathVariable("token")
                    String userId, @PathVariable()
                    String resourceName, HttpServletRequest request) {
        Map<String, String> allRequestParams = BaseRepositoryController.getSingleParameterValueMapFromRequest(request);

        allRequestParams.put("resourceName", resourceName);
        allRequestParams.put("coreUser._id", userId);
        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
        IDataObjectList<UserResourceFilterModel> searchresult = userResourceFilterService.searchPaged(allRequestParams);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * Read elements response entity.
     *
     * @param userId       the user id
     * @param resourceName the resource name
     * @param model        the model
     * @param request      the request
     * @return the response entity
     */
    @ApiOperation(value = "create new resourcefilter",
            notes = "with this method a new resource filter is stored"
    )
    @RequestMapping(value = "/{resourceName}",
            method = RequestMethod.POST)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate<UserResourceFilterModel>> createElement(
            @PathVariable("token")
                    String userId, @PathVariable()
                    String resourceName,
            @RequestBody UserResourceFilterModel model,

            HttpServletRequest request) {
        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
        model.setResourceName(resourceName);
        model.getRelevants().add(new DataDocumentLink<>(userId));
        userResourceFilterService.save(model);
        return manager.getResponseEntity();
    }

    /**
     * Delete element response entity.
     *
     * @param userId       the user id
     * @param resourceName the resource name
     * @param filterId     the filter id
     * @param request      the request
     * @return the response entity
     */
    @ApiOperation(value = "delete a filter"
    )
    @RequestMapping(value = "/{resourceName}/{filterId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate<UserResourceFilterModel>> deleteElement(
            @PathVariable("token")
                    String userId, @PathVariable()
                    String resourceName, @PathVariable()
                    String filterId, HttpServletRequest request) {

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);

        UserResourceFilterModel model = userResourceFilterService.findOne(filterId);

        if (
                userId.equals(model.getCoreUser().getId()) &&
                        resourceName.equals(model.getResourceName())

        ) {

            userResourceFilterService.delete(model);
        } else {

            throw new UFPRuntimeException("Not owner of filter");

        }

        return manager.getResponseEntity();
    }

}
