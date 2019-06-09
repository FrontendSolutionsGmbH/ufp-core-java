package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.model.workflow.factory.*;
import com.froso.ufp.modules.core.workflow.service.workflow.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.12.13.
 */
@Service
public class WorkflowFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowFactory.class);

    @Autowired
    private WorkflowInstanceService workflowInstanceService;


    private Map<String, WorkflowFactoryManufactorer> manufactorers = new HashMap<>();

    /**
     * Constructor Workflow factory.
     */
    public WorkflowFactory() {

        initialise();
    }

    /**
     * Register manufactorer.
     *
     * @param name         the name
     * @param manufactorer the manufactorer
     */
    public void registerManufactorer(String name, WorkflowFactoryManufactorer manufactorer) {

    }


    /**
     * prepare available workflows here and store them in the map
     */
    private void initialise() {
        // TODO: USE CLASS NAMES FOR DEFINING THE MAP ... :(

    }

    /**
     * Gets definitions.
     *
     * @return the definitions
     */
    public Map<String, WorkflowFactoryManufactorer> getDefinitions() {

        return manufactorers;
    }

    /**
     * Create workflow workflow.
     *
     * @param definition the definition
     * @return the workflow
     */
    public Workflow createWorkflow(WorkflowDefinition definition) {

        Workflow result = new Workflow();
        result.setDefinition(definition);
        WorkflowContext context = new WorkflowContextImpl();
        //TODO: Workflow erst ausführen bevor aktion gespeichert wird
        result.setContext(context);
        return result;
    }

    /**
     * Gets workflow by name.
     *
     * @param workflowName the workflow name
     * @return the workflow by name
     */
    public WorkflowDefinition getWorkflowByName(String workflowName) {
        // FIKXME: DONT REINITIALISE EVERYTIME; BUT AVOID RE-USING EXITING INSTANCES!
        initialise();
        return manufactorers.get(workflowName).getWorkflow();
    }

    /**
     * Factor workflow context.
     *
     * @param workflowName the workflow name
     * @return the workflow context
     */
    public WorkflowContext factorWorkflowContext(String workflowName) {

        WorkflowContext result = new WorkflowContextImpl();


        return result;
    }

       /*
    private void checkIfWorkflowElementExistsAlready(final Workflow result) {

        Map<String, String> searchmap = new HashMap<>();
        searchmap.put("workflowName", result.getDefinition().getName());
        for (String curKey : result.getDefinition().getUniqueElements()) {
            Object currentValue = result.getContext().getContextData().get(curKey);
            searchmap.put("currentState.contextData." + curKey, currentValue.toString());
        }
        // now check if workflow exists and if yes,  stop workflow
        List<WorkflowInstance> instancesrunning = workflowInstanceService.search(searchmap);
        LOGGER.info("Found active Workflows:" + instancesrunning.size());
        for (WorkflowInstance instance : instancesrunning) {
            if (!((instance.getWorkflowState().equals(WorkflowStateEnum.STOPPED)) ||
                    (instance.getWorkflowState().equals(WorkflowStateEnum.CANCELLED_UNIQUE)) ||
                    (instance.getWorkflowState().equals(WorkflowStateEnum.CANCELLED_ERROR)) ||
                    (instance.getWorkflowState().equals(WorkflowStateEnum.ACTIVITY_ERROR)) ||
                    (instance.getWorkflowState().equals(WorkflowStateEnum.FINISHED)))) {
                WorkflowState state = instance.getCurrentState();

                state.setWorkflowStateEnum(WorkflowStateEnum.CANCELLED_UNIQUE);
                instance.setCurrentState(state);
                workflowInstanceService.save(instance);
            }
        }
    }    */
}
