package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.documents.*;
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

/**
 * The type Workflow instance process idle service.
 *
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.                  Manages WorkflowState Instances.
 */
public class WorkflowInstanceProcessIdleService {

    private static final String PROP_NAME_WORKFLOW_IDLE_WORKFLOW_ENABLED = "workflow.idle.enabled";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowInstanceProcessIdleService.class);
    private final AtomicBoolean processingNewWorkflow = new AtomicBoolean(Boolean.FALSE);
    @Autowired
    private WorkflowProcessorService workflowProcessorService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    @Qualifier(ThreadConfig.HIGH_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private IPropertyService propertyService;

    /**
     * process WAITING_TO_SEND suggestions
     */
    @WorkerAnnotation(name = "Workflow IDLE Worker", description = "Works through  IDLE workflows that have stopped for some reason and need to check if continuation condition is reached")

    public void checkNewWorkflows() {

        if (!propertyService.getPropertyValueBoolean(PROP_NAME_WORKFLOW_IDLE_WORKFLOW_ENABLED)) {
            return;
        }
        if (!processingNewWorkflow.get()) {
            try {
                Runnable t = new RunnableWorkflowProcessIdle();
                threadPoolTaskExecutor.execute(t);
            } catch (Exception e) {
                LOGGER.error("IDLE WORKFLOW PROCESSING ERROR", e);
            } finally {
                processingNewWorkflow.set(false);
            }
        }
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

    private class RunnableWorkflowProcessIdle implements Runnable {
        /**
         * Run void.
         */
        @Override
        public void run() {
            //To change body of implemented methods use File | Settings | File Templates.
            processingNewWorkflow.set(true);
            Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(WorkflowInstanceProcessErrorService.PROP_NAME_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP),
                    Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
            List<WorkflowInstance> workflowInstances = workflowInstanceService.findIdleWorkflows(pageable);
            Parallel.parallelFor(workflowInstances,
                    propertyService.getPropertyValueInteger(WorkflowInstanceProcessErrorService.PROP_NAME_WORKFLOW_MAXTHREADS),
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
