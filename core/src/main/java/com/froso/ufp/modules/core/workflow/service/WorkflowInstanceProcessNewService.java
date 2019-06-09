package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.*;

/**
 * The type Workflow instance process new service.
 *
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.                  Manages WorkflowState Instances.
 */
@Service
public class WorkflowInstanceProcessNewService {

    private static final String PROP_NAME_WORKFLOW_NEW_WORKFLOW_ENABLED = "workflow.new.enabled";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowInstanceProcessNewService.class);
    private final AtomicBoolean processingNewWorkflow = new AtomicBoolean(Boolean.FALSE);
    @Autowired
    private WorkflowProcessorService workflowProcessorService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    @Qualifier(ThreadConfig.HIGH_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private IPropertyService propertyService;


    /**
     * Create workflow instance.
     *
     * @param state        the state
     * @param uniqueFields the unique fields
     * @return the workflow instance
     */
    public WorkflowInstance createWorkflowInstance(WorkflowState state,
                                                   Set<String> uniqueFields) {

        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setWorkflowName(state.getWorkflowName());
        workflowInstance.setCurrentState(state);
        workflowInstance = workflowInstanceService.save(workflowInstance);
        return workflowInstance;
    }

    /**
     * Create workflow instance workflow instance.
     *
     * @param state                the state
     * @param uniqueFields         the unique fields
     * @param workflowDefinitionId the workflow definition id
     * @return the workflow instance
     */
    public WorkflowInstance createWorkflowInstance(WorkflowState state,
                                                   Set<String> uniqueFields, String workflowDefinitionId) {

        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setWorkflowDefinitionId(workflowDefinitionId);
        workflowInstance.setWorkflowName(state.getWorkflowName());
        workflowInstance.setCurrentState(state);
        workflowInstance = workflowInstanceService.save(workflowInstance);
        return workflowInstance;
    }

    /**
     * process WAITING_TO_SEND suggestions
     */
    @WorkerAnnotation(name = "Workflow New Worker", description = "Works through  new workflows")
    public void checkNewWorkflows() {

        if (!propertyService.getPropertyValueBoolean(PROP_NAME_WORKFLOW_NEW_WORKFLOW_ENABLED)) {
            return;
        }
        if (!processingNewWorkflow.get()) {
            try {
                Runnable t = new RunnableWorkflowProcessNew();
                taskExecutor.execute(t);
            } catch (Exception e) {
                LOGGER.error("WAITING_TO_SEND WORKFLOW PROCESSING ERROR", e);
            } finally {
                processingNewWorkflow.set(false);
            }
        }
    }

    /**
     * Find by workflow name and context data.
     *
     * @param workflowName     the workflow name
     * @param contextDataField the context data field
     * @param contextDataValue the context data value
     * @return the workflow instance
     */
    public WorkflowInstance findByWorkflowNameAndContextData(String workflowName,
                                                             String contextDataField,
                                                             String contextDataValue) {
        // Build a search map
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("workflowName", workflowName);
        stringMap.put("currentState.contextData." + contextDataField, contextDataValue);
        WorkflowInstance result = null;
        IDataObjectList resultSearch = workflowInstanceService.searchPaged(stringMap);
        if (!resultSearch.getList().isEmpty()) {
            result = (WorkflowInstance) resultSearch.getList().get(0);
        }
        return result;
    }

    /**
     * Findby workflow name and user id.
     *
     * @param workflowName the workflow name
     * @param userId       the user id
     * @return the list
     */
    public List<WorkflowInstance> findbyWorkflowNameAndUserId(String workflowName,
                                                              String userId) {

        return workflowInstanceService.findbyWorkflowNameAndUserId(workflowName, userId);
    }

    private class RunnableWorkflowProcessNew implements Runnable {
        /**
         * Run void.
         */
        @Override
        public void run() {
            //To change body of implemented methods use File | Settings | File Templates.
            processingNewWorkflow.set(true);
            Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(WorkflowInstanceProcessErrorService.PROP_NAME_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP),
                    Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
            List<WorkflowInstance> workflowInstances = workflowInstanceService.findNewWorkflows(pageable);
            Parallel.parallelFor(workflowInstances, propertyService.getPropertyValueInteger(WorkflowInstanceProcessErrorService.PROP_NAME_WORKFLOW_MAXTHREADS),
                    "NewWorkflowsProcessor", new ParallelStep() {
                        @Override
                        public boolean execute(int index,
                                               List<?> list) {

                            WorkflowInstance instance = (WorkflowInstance) list.get(index);
                            LOGGER.debug("Processing:" + index);
                            workflowProcessorService.checkCancellationWorkflow(instance);
                            return true;
                        }
                    });
            processingNewWorkflow.set(false);
        }
    }
}
