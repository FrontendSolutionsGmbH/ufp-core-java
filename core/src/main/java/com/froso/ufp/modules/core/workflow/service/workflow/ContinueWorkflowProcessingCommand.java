package com.froso.ufp.modules.core.workflow.service.workflow;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import com.froso.ufp.modules.core.workflow.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.12.13 Time: 11:47 To change this template use File | Settings |
 * File
 * Templates.
 */
@Component
public class ContinueWorkflowProcessingCommand {

    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private WorkflowProcessorService workflowProcessorService;

    /**
     * Process workflow.
     *
     * @param workflowStateId the workflow state id
     * @param data            the data
     * @return the workflow
     */
    public Workflow processWorkflow(String workflowStateId,
                                    Map<String, String> data) {

        WorkflowInstance workflowInstance = workflowInstanceService.findOne(workflowStateId);
        return workflowProcessorService.processWorkflow(workflowInstance, data);
    }
}
