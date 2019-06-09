package com.froso.ufp.modules.core.workflow.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.workflow.model.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + WorkflowDefinition2.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = WorkflowDefinition2.TYPE_NAME)
class AdminWorkflowDefinitionController extends BaseRepositoryController<WorkflowDefinition2> {


    @Autowired
    private WorkflowService workflowService;



    /**
     * Gets activities.
     *
     * @param userId           the user id
     * @param allRequestParams the all request params
     * @param request          the request
     * @return the activities
     */
    @ApiOperation(value = "Get Activities", notes = "Returns a list of possible Activities")

    @RequestMapping(value = "/Activities", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplate<WorkflowActivityMap>> getActivities(
            @PathVariable String userId,
            @ApiParam(hidden = true, required = false, value = "HELLO WORLD", access = "ACCESS_HULU", allowMultiple = true)
            @RequestParam Map<String, String> allRequestParams,

            HttpServletRequest request) {


        ResponseHandlerTemplate<WorkflowActivityMap> manager = new ResponseHandlerTemplate<>(request);
        manager.addResult(workflowService.getActivities());
        return manager.getResponseEntity();
    }


}
