package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.workflow.model.trigger.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * The type Workflow instance service.
 *
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.                  Manages WorkflowState Instances.
 */
@Service
public class WorkflowTriggerService extends AbstractClientRefService<WorkflowTrigger> {


    /**
     * The Workflow definition service.
     */
    @Autowired
    WorkflowDefinitionService workflowDefinitionService;


    /**
     * Fill default object.
     *
     * @param object the object
     */
    @Override
    protected void fillDefaultObject(WorkflowTrigger object) {

        object.getWorkflowDefinitionLink().setId(workflowDefinitionService.findAll().get(0).getId());

        CRUDTrigger crudTrigger = new CRUDTrigger();
        crudTrigger.setEvent(DataDocumentCRUDEvent.CREATE);

        object.setTrigger(crudTrigger);

    }

}
