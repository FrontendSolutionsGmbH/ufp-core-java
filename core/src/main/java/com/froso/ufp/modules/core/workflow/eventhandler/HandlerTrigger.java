package com.froso.ufp.modules.core.workflow.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.core.workflow.model.trigger.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import com.froso.ufp.modules.core.workflow.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 */
@Component
public class HandlerTrigger implements ApplicationListener<DataDocumentCreateEvent> {

    @Autowired
    private WorkflowFactory2 workflowFactory2;
    @Autowired
    private WorkflowFactory workflowFactory;
    @Autowired
    private WorkflowTriggerService workflowTriggerService;

    @Autowired
    private WorkflowProcessorService workflowProcessorService;
    @Autowired
    private WorkflowInstanceProcessNewService workflowInstanceProcessNewService;

    /**
     * On application event.
     *
     * @param event the event
     */
    public void onApplicationEvent(DataDocumentCreateEvent event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here

        //     LOGGER.info("Data saved " + event.getDataDocument().toString());

        // Find trigger that matches event
        if (!(event.getDataDocument() instanceof WorkflowInstance)) {
            List<WorkflowTrigger> triggers = workflowTriggerService.findByKeyValue("trigger.event", DataDocumentCRUDEvent.CREATE.toString());
            for (WorkflowTrigger trigger : triggers) {

                WorkflowDefinition definition = workflowFactory2.createWorkflow(trigger.getWorkflowDefinitionLink().getId());
                Workflow workflow = workflowFactory.createWorkflow(definition);

                Map<String, Object> dataMap = new HashMap<>();
                //  dataMap.put(event.getDataDocument().getMetaData().getType(), event.getDataDocument());

                workflow.getContext().getContextData().put("data", dataMap);

                WorkflowInstance workflowInstance = workflowInstanceProcessNewService
                        .createWorkflowInstance(workflow.getWorkflowState(), workflow.getDefinition().getUniqueElements(), trigger.getWorkflowDefinitionLink().getId());

                workflowProcessorService.processWorkflow(workflowInstance);

            }
        }
    }


}


