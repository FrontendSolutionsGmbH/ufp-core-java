/*
 * Copyright (c) 2015.3.28 . FroSo, Christian Kleinhuis (ck@froso.de)
 */

package com.froso.ufp.modules.core.workflow.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.service.*;
import com.froso.ufp.modules.core.workflow.service.workflow.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.lang3.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.12.13 Time: 10:32 To change this template use File | Settings |
 * File Templates.
 * <p>
 * woot woot, TODO: FIXME: remove code duplication ....
 */
// FIXME: UNITE THIS with REST processorcontroller IN A BASE CONTROLLER FOR IMPORTER/RESTSERVICE in GATEWAY
// WARNING: THE userid is just a placeholder, the importer is not secured and not acessed with a login token!
public class BaseWorkflowProcessorController {

    /**
     * The constant HTTP.
     */
    public static final String HTTP = "http://";
    private static final String FIELD_WORKFLOW = "workflow";
    private static final String FIELD_WORKFLOWINSTANCE = "workflowinstance";
    private static final String FIELD_CONTROLLER = "controller";
    private static final String WORKFLOW_BASE_TEMPLATE_PATH = "workflow/";
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseWorkflowProcessorController.class);
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private WorkflowFactory factory;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private ContinueWorkflowProcessingCommand continueWorkflowProcessingCommand;
    @Autowired
    private ServerService serverService;
    @Autowired
    private TemplateParserService templateParserService;

    /*
    @RequestMapping(value = "/Workflow", method = RequestMethod.GET)
    public ResponseEntity<BackendResponse> getAllWorkflows() {

        // fixme: move to admin area!
        ResponseHandler responseHandler = new ResponseHandler();
        List<Object> list = new ArrayList<>();
        list.add(factory.getDefinitions());
        responseHandler.addResult(list);
        return responseHandler.getResponseEntity();
    }


    @RequestMapping(value = "/Workflow/Activities", method = RequestMethod.GET)
    public ResponseEntity<BackendResponse> getAllActivities() {

        // fixme: move to admin area!
        ResponseHandler responseHandler = new ResponseHandler();
        List<Object> list = new ArrayList<>();
        list.add(workflowService.getActivities());
        responseHandler.addResult(list);
        return responseHandler.getResponseEntity();
    }
           */

    /**
     * Process workflow.
     *
     * @param id               the id
     * @param allRequestParams the all request params
     * @param model            the model
     * @param response         the response
     * @return the string
     */
    @RequestMapping(value = "/Workflow/{id}", method = RequestMethod.GET)

    @ApiOperation(value = "Workflow Output", notes = "Workflow external output GET method. Used internall, workflows link here to provide external output. For example the user workflow activates its account here..")

    @ResponseBody
    public String processWorkflow(
            @PathVariable String id,
            @ApiParam(hidden = true, required = false) @RequestParam Map<String, String> allRequestParams,
            @ApiParam(hidden = true, required = false) Model model,
            HttpServletResponse response) {
        ResponseHandler.setDefaultHeaders(response);

        String result = null;
        try {
            Workflow workflow = continueWorkflowProcessingCommand.processWorkflow(id, allRequestParams);
            model.addAttribute(FIELD_WORKFLOW, workflow);
            model.addAttribute(FIELD_WORKFLOWINSTANCE, workflowInstanceService.findOne(id));
            model.addAttribute(FIELD_CONTROLLER, getControllerData());
            result = "/" + WORKFLOW_BASE_TEMPLATE_PATH +
                    workflow.getWorkflowName() + "/" + workflow.getCurrentElementName();
        } catch (Exception ignored) {
            LOGGER.debug(ignored.getMessage(), ignored);
            model.addAttribute(FIELD_CONTROLLER, getControllerData());
            result = WORKFLOW_BASE_TEMPLATE_PATH + "invalid";
        }

        model.addAttribute("mailFooterImageUrl", getControllerData().get("web") + "/pages/images/mail_footer.png");
        model.addAttribute("mailHeaderImageUrl", getControllerData().get("web") + "/pages/images/mail_header.png");
        model.addAttribute("mailBackgroundImageUrl", getControllerData().get("web") + "/pages/images/background.png");


        // the result is a path to a velocity template, make everything lowercase!
        result = result.toLowerCase();


        return templateParserService.parseTemplate("templates/" + result + ".vm", model.asMap());
        // prohibit sending of data to the workflow in a get method, at best nothing is changed when processing the
        // workflow..
    }

    /**
     * the getControllerData provides general information about the url schema, and how to reference components
     *
     * @return controller data
     */
    Map<String, String> getControllerData() {

        Map<String, String> result = new HashMap<>();
        result.put("api", serverService.getApiUrl());
        result.put("workflowprocessor", "/Workflow/");
        return result;
    }

    /**
     * Process workflow post.
     *
     * @param id               the id
     * @param allRequestParams the all request params
     * @param model            the model
     * @param response         the response
     * @return the string
     */
    @RequestMapping(value = "/Workflow/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "Workflow Input", notes = "Workflow external input POST method. When a workflow requires some kind of input, e.g. from a user it is send to this endpoint.")

    @ResponseBody
    public String processWorkflowPost(@PathVariable(
            "id") String id,
                                      @ApiParam(hidden = true)
                                      @RequestParam Map<String, String> allRequestParams, Model model, HttpServletResponse
                                              response) {
        ResponseHandler.setDefaultHeaders(response);


        String result = StringUtils.EMPTY;
        try {
            Workflow workflow = continueWorkflowProcessingCommand.processWorkflow(id, allRequestParams);
            model.addAttribute(FIELD_WORKFLOW, workflow);
            model.addAttribute(FIELD_WORKFLOWINSTANCE, workflowInstanceService.findOne(id));
            model.addAttribute(FIELD_CONTROLLER, getControllerData());
            result = "/" + WORKFLOW_BASE_TEMPLATE_PATH +
                    workflow.getWorkflowName() + "/" + workflow.getCurrentElementName();


        } catch (Exception ignored) {
            model.addAttribute(FIELD_CONTROLLER, getControllerData());
            LOGGER.debug(ignored.getMessage(), ignored);
            result = WORKFLOW_BASE_TEMPLATE_PATH + "invalid";
        }

        model.addAttribute("mailFooterImageUrl", getControllerData().get("web") + "/pages/images/mail_footer.png");
        model.addAttribute("mailHeaderImageUrl", getControllerData().get("web") + "/pages/images/mail_header.png");
        model.addAttribute("mailBackgroundImageUrl", getControllerData().get("web") + "/pages/images/background.png");


        // the result is a path to a velocity template, make everything lowercase!
        result = result.toLowerCase();

        return templateParserService.parseTemplate("templates/" + result + ".vm", model.asMap());
    }
}
