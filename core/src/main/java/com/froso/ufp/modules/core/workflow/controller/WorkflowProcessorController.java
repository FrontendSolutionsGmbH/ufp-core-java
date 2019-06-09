package com.froso.ufp.modules.core.workflow.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.modules.core.workflow.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.12.13 Time: 10:32 To change this template use File | Settings |
 * File Templates.
 */
@Controller
@RequestMapping(UFPConstants.API)
@Api(description = "Workflow Interaction", tags = "Workflow")
class WorkflowProcessorController extends BaseWorkflowProcessorController {

    @Autowired
    private WorkflowFactory2 workflowFactory2;


    /**
     * Instantiates a new Workflow processor controller.
     *
     * @param requestDefinitionService the request definition service
     * @param id                       the id
     * @return the parsed workflow
     * @Autowired public WorkflowProcessorController(RequestDefinitionService requestDefinitionService) {
     * requestDefinitionService.registerTokenFreePath("/Workflow/ParsedDefinition");
     * <p>
     * <p>
     * }
     * <p>
     * *
     * Gets parsed workflow.
     * @RequestMapping(value = "/Workflow/ParsedDefinition/{id}", method = RequestMethod.GET)
     * @ResponseBody public ResponseEntity<BackendResponseTemplateSingleObject<WorkflowDefinition>> getParsedWorkflow(@PathVariable String id) {
     * <p>
     * // fixme: move to admin area!
     * <p>
     * <p>
     * ResponseHandlerTemplateSingleObject<WorkflowDefinition> responseHandler = new ResponseHandlerTemplateSingleObject<WorkflowDefinition>();
     * <p>
     * responseHandler.addResult(workflowFactory2.createWorkflow(id));
     * <p>
     * return responseHandler.getResponseEntity();
     * }
     */


    @RequestMapping(value = "/Workflow/Test/{id}", method = RequestMethod.GET)

    @ApiOperation(value = "For Development, execute workflow by id", notes = "This endpoint is just for developing purpose, it instanciates a given workflow by its DefinitionID.")

    @ResponseBody
    public String testWorkflow(
            @PathVariable String id,
            @ApiParam(hidden = true, required = false) @RequestParam Map<String, String> allRequestParams,
            HttpServletResponse response) {


        return "HELLO WORLF";
        // prohibit sending of data to the workflow in a get method, at best nothing is changed when processing the
        // workflow..
    }
}

