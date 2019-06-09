package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.email.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.script.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * The type Workflow instance service.
 *
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.                  Manages WorkflowState Instances.
 */
@Service
public class WorkflowDefinitionService extends AbstractClientRefService<WorkflowDefinition2> {

    /**
     * The constant WORKFLOW_ACTIVITY_START.
     */
    public static final String WORKFLOW_ACTIVITY_START = "Start";
    /**
     * The constant WORKFLOW_ACTIVITY_EMAIL1.
     */
    public static final String WORKFLOW_ACTIVITY_EMAIL1 = "SendEmail1";
    /**
     * The constant WORKFLOW_ACTIVITY_EMAIL2.
     */
    public static final String WORKFLOW_ACTIVITY_EMAIL2 = "SendEmail2";

    /**
     * for testing purposes, get valid template id
     */
    @Autowired
    private TemplateService templateService;

    @Override
    protected void fillDefaultObject(WorkflowDefinition2 object) {

        fillExampleWorkflow(object);


    }


    private void fillExampleWorkflow(WorkflowDefinition2 object) {
        List<FileTemplate> templates = templateService.findAll();


        WorkflowDefinitionStep workflowDefinitionStepStoreNonce = new WorkflowDefinitionStep();
        workflowDefinitionStepStoreNonce.setStepName("Step " + WorkflowActivityJavaScript.NAME);
        workflowDefinitionStepStoreNonce.setStepTypeClassName(WorkflowActivityJavaScript.NAME);
        workflowDefinitionStepStoreNonce.setStepReturnType(WorkflowStepType.BRANCH_BOOL);
        workflowDefinitionStepStoreNonce.getProperties().setProp(WorkflowActivityJavaScript.PROP_SCRIPT_CODE, "function main(){context.clickatellsmsprovider='HELLO WORLD'; return true;}");

        WorkflowDefinitionStep workflowDefinitionStepSendWelcomeEmail = new WorkflowDefinitionStep();
        workflowDefinitionStepSendWelcomeEmail.setStepName("Step " + ActivitySendEmail.NAME);
        workflowDefinitionStepSendWelcomeEmail.setStepTypeClassName(ActivitySendEmail.NAME);
        workflowDefinitionStepSendWelcomeEmail.getProperties().setProp(ActivitySendEmail.PROP_EMAIL_SUBJECT, "WORKFLOW TRUE Object Created ");
        workflowDefinitionStepSendWelcomeEmail.getProperties().setProp(ActivitySendEmail.PROP_FROM, "ck@froso.de");
        workflowDefinitionStepSendWelcomeEmail.getProperties().setProp(ActivitySendEmail.PROP_TO, "ck@froso.de");

        workflowDefinitionStepSendWelcomeEmail.getProperties().setProp(DefaultProps.Template.PROP_TEMPLATE_LINK_ID, templates.get(0).getId());
        workflowDefinitionStepSendWelcomeEmail.getProperties().setProp(DefaultProps.Template.PROP_TEMPLATE_ENTRY, "email.vm");
        workflowDefinitionStepSendWelcomeEmail.setStepReturnType(WorkflowStepType.CONTINUE);

        WorkflowDefinitionStep workflowDefinitionStepCheckNonce = new WorkflowDefinitionStep();
        workflowDefinitionStepCheckNonce.setStepName("Step " + ActivitySendEmail.NAME);
        workflowDefinitionStepCheckNonce.setStepTypeClassName(ActivitySendEmail.NAME);
        workflowDefinitionStepCheckNonce.getProperties().setProp(ActivitySendEmail.PROP_EMAIL_SUBJECT, "WORKFLOW FALSE Object Created ");
        workflowDefinitionStepCheckNonce.getProperties().setProp(ActivitySendEmail.PROP_FROM, "ck@froso.de");
        workflowDefinitionStepCheckNonce.getProperties().setProp(ActivitySendEmail.PROP_TO, "ck@froso.de");

        workflowDefinitionStepCheckNonce.getProperties().setProp(DefaultProps.Template.PROP_TEMPLATE_LINK_ID, templates.get(0).getId());
        workflowDefinitionStepCheckNonce.getProperties().setProp(DefaultProps.Template.PROP_TEMPLATE_ENTRY, "test.vm");
        workflowDefinitionStepCheckNonce.getProperties().setProp(DefaultProps.Template.PROP_TEMPLATE_ENTRY_CONTAINER, "email.vm");
        workflowDefinitionStepCheckNonce.setStepReturnType(WorkflowStepType.CONTINUE);


        // workflowDefinitionStep1.getResultSteps().put(WorkflowStepConstants.STEP_TRUE, workflowDefinitionStep2);
        // workflowDefinitionStep1.getResultSteps().put(WorkflowStepConstants.STEP_FALSE, workflowDefinitionStep3);


        WorkflowDefinitionStepRef ref2 = new WorkflowDefinitionStepRef(WORKFLOW_ACTIVITY_EMAIL1);
        ref2.getFollowUps().put(WorkflowStepConstants.STEP_NEXT, "email1");


        WorkflowDefinitionStepRef ref3 = new WorkflowDefinitionStepRef(WORKFLOW_ACTIVITY_EMAIL2);
        ref3.getFollowUps().put(WorkflowStepConstants.STEP_NEXT, "email1");


        WorkflowDefinitionStepRef ref = new WorkflowDefinitionStepRef();
        ref.setActivityRefName(WORKFLOW_ACTIVITY_START);
        ref.getFollowUps().put(WorkflowStepConstants.STEP_NEXT, "email1");

        object.getStepRefs().put("start", ref);
        object.getStepRefs().put("email1", ref2);
        object.getStepRefs().put("email2", ref3);


        object.getActivities().put(WORKFLOW_ACTIVITY_START, workflowDefinitionStepStoreNonce);
        object.getActivities().put(WORKFLOW_ACTIVITY_EMAIL1, workflowDefinitionStepSendWelcomeEmail);
        object.getActivities().put(WORKFLOW_ACTIVITY_EMAIL2, workflowDefinitionStepCheckNonce);
    }

    /**
     * Find errornous workflows list.
     *
     * @param pageable the pageable
     * @return the list
     */
    /**
     * Find by workflow name and context data.
     *
     * @param workflowName     the workflow name
     * @param contextDataField the context data field
     * @param contextDataValue the context data value
     * @return the workflow instance
     */
    public WorkflowDefinition2 findByWorkflowNameAndContextData(String workflowName, String contextDataField, String contextDataValue) {
        // Build a search map
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("workflowName", workflowName);
        stringMap.put("currentState.contextData." + contextDataField, contextDataValue);
        WorkflowDefinition2 result = null;
        IDataObjectList resultSearch = searchPaged(stringMap);
        if (!resultSearch.getList().isEmpty()) {
            result = (WorkflowDefinition2) resultSearch.getList().get(0);
        }
        return result;
    }

    /**
     * Search by workflow name and context data data object list.
     *
     * @param workflowName     the workflow name
     * @param contextDataField the context data field
     * @param contextDataValue the context data value
     * @return the data object list
     */
    public IDataObjectList searchByWorkflowNameAndContextData(String workflowName, String contextDataField, String contextDataValue) {
        // Build a search map
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("workflowName", workflowName);
        stringMap.put("currentState.contextData." + contextDataField, contextDataValue);
        WorkflowDefinition result = null;
        IDataObjectList resultSearch = searchPaged(stringMap);

        return resultSearch;
    }

    /**
     * Findby workflow name and user id.
     *
     * @param workflowName the workflow name
     * @param userId       the user id
     * @return the list
     */
    public List<WorkflowDefinition2> findbyWorkflowNameAndUserId(String workflowName, String userId) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("workflowName", workflowName);
        searchMap.put(" currentState.contextData.USER_ID", userId);

        return findByKeyValues(searchMap);
    }
}
