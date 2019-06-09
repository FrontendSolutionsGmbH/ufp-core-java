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
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.*;

/**
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.
 *
 *         Manages WorkflowState Instances.
 */
public class WorkflowInstanceProcessErrorService {

    public static final String PROP_NAME_WORKFLOW_MAXTHREADS = "workflow.maxthreads";
    public static final String PROP_NAME_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP = "workflow" + ".maxEntriesToProcessInOneStep";
    private static final String PROP_NAME_WORKFLOW_ERROR_WORKFLOW_ENABLED = "workflow.error.enabled";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowInstanceProcessErrorService.class);
    @Autowired
    private WorkflowProcessorService workflowProcessorService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    private AtomicBoolean processingErrorWorkflow = new AtomicBoolean(false);
    @Autowired
    @Qualifier(ThreadConfig.HIGH_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private IPropertyService propertyService;
    /**
     * Constructor
     *
     * @param workflowInstanceRepository Repository to work with, has to be of same type as this service
     * @param operations                 the operations
     */

    /**
     * check errornous workflows and try to continue
     */

    @WorkerAnnotation(name = "Workflow Error Worker", description = "Works through  Errornous workflows trying to complete them up to a certain max retry limit")
    @Async
    private void checkErrornousWorkflows() {

        if (!propertyService.getPropertyValueBoolean(PROP_NAME_WORKFLOW_ERROR_WORKFLOW_ENABLED)) {
            return;
        }
        if (!processingErrorWorkflow.get()) {
            processingErrorWorkflow.set(true);
            Runnable t = new RunnableWorkflowProcessError();
            threadPoolTaskExecutor.execute(t);
        }
    }

    private class RunnableWorkflowProcessError implements Runnable {
        /**
         * Run void.
         */
        @Override
        public void run() {
            //To change body of implemented methods use File | Settings | File Templates.
            Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(PROP_NAME_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP), Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
            List<WorkflowInstance> workflowInstances = workflowInstanceService.findErrornousWorkflows(pageable);
            Parallel.parallelFor(workflowInstances, propertyService.getPropertyValueInteger(PROP_NAME_WORKFLOW_MAXTHREADS), "ErrornousWorkflows", new ParallelStep() {
                @Override
                public boolean execute(int index, List<?> list) {

                    WorkflowInstance instance = (WorkflowInstance) list.get(index);

                    workflowProcessorService.checkCancellationWorkflow(instance);


                    return true;
                }
            });
            processingErrorWorkflow.set(false);
        }
    }
}
