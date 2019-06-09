package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Created by ckleinhuix on 15.12.13.
 */
@Service
public class WorkflowFactory2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowFactory2.class);

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;

    @Autowired
    private WorkflowService workflowService;

    /**
     * Constructor Workflow factory.
     */
    public WorkflowFactory2() {

    }

    /**
     * Create workflow.
     *
     * @param workflowDefinitionId the workflow definition id
     * @return the workflow definition
     */
    public WorkflowDefinition createWorkflow(String workflowDefinitionId) {
        WorkflowDefinition result = new WorkflowDefinition();

        try {
            WorkflowDefinition2 workflowDefinition2 = workflowDefinitionService.findOne(workflowDefinitionId);

            /**
             *
             * constructing a useable workflowdefinition to be used by the workflow engine, all elements needed have to be prepared
             * here are 3 steps:
             *
             * 1. create activity instance with the defined property values for that actions as defined in the workflowdefinition2
             * 2. instanciate workflow elements (sequence, ifelse for now) abd assign the appropriate actions to them
             * 3. Wire the workflow element instances together to form a graph and set start element in result definition
             *
             */
            Map<String, WorkflowActivity> activities = new HashMap<>();
            Map<String, WorkflowStepType> activitytypes = new HashMap<>();

            // constructTyping workflow actions

            for (Map.Entry<String, WorkflowDefinitionStep> entry : workflowDefinition2.getActivities().entrySet()) {
                WorkflowDefinitionStep step = entry.getValue();

                // Construct activity from service
                WorkflowActivity activity = workflowService.constructActivityByName(step.getStepTypeClassName(), step.getProperties());
                activitytypes.put(entry.getKey(), step.getStepReturnType());
                activities.put(entry.getKey(), activity);
            }

            // After having the activities instanciated, create the element containers
            Map<String, WorkflowElement> workflowElements = new HashMap<>();

            for (Map.Entry<String, WorkflowDefinitionStepRef> entryStepRef : workflowDefinition2.getStepRefs().entrySet()) {
                WorkflowDefinitionStepRef step = entryStepRef.getValue();
                WorkflowActivity activity = activities.get(step.getActivityRefName());
                switch (activitytypes.get(step.getActivityRefName())) {

                    case BRANCH_BOOL:
                        // Just create workflow element here, afterwards assign the follow up elements
                        WorkflowElementIfElse workflowElementIfElse = new WorkflowElementIfElse(entryStepRef.getKey());
                        workflowElementIfElse.setActivity(activity);
                        workflowElements.put(entryStepRef.getKey(), workflowElementIfElse);
                        break;
                    case CONTINUE_ON_TRUE:
                        // Just create workflow element here, afterwards assign the follow up elements
                        WorkflowElementIfContinueThreaded elem = new WorkflowElementIfContinueThreaded(entryStepRef.getKey());
                        elem.setActivity(activity);
                        workflowElements.put(entryStepRef.getKey(), elem);
                        break;
                    case CONTINUE:
                        WorkflowElementSequence workflowElementSequence = new WorkflowElementSequence(entryStepRef.getKey());
                        workflowElementSequence.setActivity(activity);
                        workflowElements.put(entryStepRef.getKey(), workflowElementSequence);
                        break;
                    default:

                }
            }

            // last step is to wire the workflow elements together (which was not possible before they where created
            for (Map.Entry<String, WorkflowDefinitionStepRef> entryStepRef : workflowDefinition2.getStepRefs().entrySet()) {
                WorkflowElement element = workflowElements.get(entryStepRef.getKey());

                for (Map.Entry<WorkflowStepConstants, String> followUp : entryStepRef.getValue().getFollowUps().entrySet()) {
                    element.addChildElement(followUp.getKey().toString(), workflowElements.get(followUp.getValue()));

                }

            }

            result.setRoot(workflowElements.get("start"));
        } catch (Exception e) {

            LOGGER.error("WorkflowFactory2 createWorkflow Exception ", e);

        }
        //   result.setRoot(createWorkflowElement(workflowDefinition2.getStart()));
        return result;

    }

}
