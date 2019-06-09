package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.workflow.model.*;
import com.froso.ufp.modules.core.workflow.model.listener.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import com.froso.ufp.modules.core.workflow.service.workflow.threading.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.exception.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 12.12.13.
 */
@Service
public class WorkflowProcessorService {
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowProcessorService.class);

    @Autowired
    private WorkflowFactory2 workflowFactory2;

    @Autowired
    private IPropertyService propertyService;
    ;
    @Autowired
    @Qualifier(ThreadConfig.HIGH_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor taskExecutorHigh;
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor taskExecutorMid;
    @Autowired
    @Qualifier(ThreadConfig.LOW_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor taskExecutorLo;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private WorkflowFactory workflowFactory;
    @Autowired
    private EmailSenderService emailSenderService;

    private Map<String, Map<String, WorkflowThreadInfo>> threads = new HashMap<>();

    /**
     * Gets running threads.
     *
     * @return the running threads
     */
    public Map<String, Map<String, WorkflowThreadInfo>> getRunningThreads() {

        return threads;
    }

    /**
     * Gets thread info.
     *
     * @return the thread info
     */
    public List<ThreadInfo> getThreadInfo() {

        List<ThreadInfo> result = new ArrayList<>();
        ThreadInfo result1 = new ThreadInfo();
        result1.setActiveCount(taskExecutorHigh.getActiveCount());
        result1.setPoolSize(taskExecutorHigh.getPoolSize());
        result1.setMaxPoolSize(taskExecutorHigh.getMaxPoolSize());
        result1.setThreadNamePrefix(taskExecutorHigh.getThreadNamePrefix());
        result.add(result1);
        ThreadInfo result2 = new ThreadInfo();
        result2.setActiveCount(taskExecutorMid.getActiveCount());
        result2.setPoolSize(taskExecutorMid.getPoolSize());
        result2.setMaxPoolSize(taskExecutorMid.getMaxPoolSize());
        result2.setThreadNamePrefix(taskExecutorMid.getThreadNamePrefix());
        result.add(result2);
        ThreadInfo result3 = new ThreadInfo();
        result3.setActiveCount(taskExecutorLo.getActiveCount());
        result3.setPoolSize(taskExecutorLo.getPoolSize());
        result3.setMaxPoolSize(taskExecutorLo.getMaxPoolSize());
        result3.setThreadNamePrefix(taskExecutorLo.getThreadNamePrefix());
        result.add(result3);
        return result;
    }

    /**
     * Stop workflow.
     *
     * @param workflowId the workflow id
     */
    public void stopWorkflow(String workflowId) {

        if (findRunning(workflowId) != null) {
            findRunning(workflowId).getWorkflowRunnable().getListener().requestStop();
        }
    }

    /**
     * Find running.
     *
     * @param workflowId the workflow id
     * @return the workflow thread info
     */
    public WorkflowThreadInfo findRunning(String workflowId) {

        for (Map<String, WorkflowThreadInfo> entry : threads.values()) {
            for (WorkflowThreadInfo info : entry.values()) {
                if (info.getInstanceID().equals(workflowId)) {
                    return info;
                }
            }
        }

        return null;
    }

    /**
     * Find running or alive workflow thread info.
     *
     * @param workflowId the workflow id
     * @return the workflow thread info
     */
    public WorkflowThreadInfo findRunningOrAlive(String workflowId) {

        // if nothing found create empty info result on the fly...
        WorkflowThreadInfo result = findRunning(workflowId);
        if (result == null) {

            result = new WorkflowThreadInfo();
            final WorkflowInstance workflowInstance = workflowInstanceService.findOne(workflowId);
            if (workflowInstance != null) {
                result.setWorkflowInstance(workflowInstance);
            }
            result.setInstanceID(workflowId);
        }
        return result;
    }

    /**
     * Stop thread group.
     *
     * @param workflowName the workflow name
     */
    public void stopThreadGroup(String workflowName) {
        // mark all threads to stop,then we have to wait until they finish totally...
        if (threads.get(workflowName) != null) {
            for (WorkflowThreadInfo entry : threads.get(workflowName).values()) {
                entry.getWorkflowRunnable().getListener().requestStop();
            }
        }
    }

    /**
     * this method is intended to restore a workflow from its stored state in the database the workflow is retrieved
     * through the factory, and re-initialised through the saved state the workflow's next activity is then called ...
     *
     * @param workflowInstance the workflow instance
     * @return the workflow
     */
    public Workflow processWorkflow(WorkflowInstance workflowInstance) {

        Workflow workflow = constructWorkflow(workflowInstance, null);
        doTheWorkflowProcessingThreaded(workflow, workflowInstance);
        return workflow;
    }

    /**
     * Construct workflow.
     *
     * @param workflowInstance the workflow instance
     * @param data             the data
     * @return the workflow
     */
    Workflow constructWorkflow(WorkflowInstance workflowInstance, Map<String, String> data) {

        Workflow workflow = new WorkflowThreaded();

        // Retrieve the workflow Definition
        //  workflow.setDefinition(workflowFactory.getWorkflowByName(workflowInstance.getWorkflowName()));


        WorkflowDefinition workflowDefinition = workflowFactory2.createWorkflow(workflowInstance.getWorkflowDefinitionId());
        workflow.setDefinition(workflowDefinition);

        // and retrieve a valid context for that workflow
        workflow.setContext(workflowFactory.factorWorkflowContext(workflowInstance.getWorkflowName()));
        workflow.loadFromState(workflowInstance.getCurrentState());
        // Set the Workflow-ID to the context ...
        workflow.getContext().getContextData().put(AbstractWorkflowElement.WORKFLOW_ID, workflowInstance.getId());
        // Ok, workflow is prepared, now process the current element
        if (null != data) {
            workflow.setInput(data);
        }
        return workflow;
    }

    private void doTheWorkflowProcessingThreaded(final Workflow workflow, final WorkflowInstance workflowInstance) {
        // Threaded workflow processing handling
        checkThreadsForFinishedEntries();
        // check if workflow id is actually running, if yes just return ... and do nothing ...
        if (findRunning(workflowInstance.getId()) != null) {
            return;
        }
        AbstractWorkflowRunnable runnable = new AbstractWorkflowRunnable() {
            @Override
            public void doRun() {
                // set the instance listener to the workflow
                this.setListener(workflowInstance.getListener());
                // register listener
                if (workflow instanceof ImportListenerUserInterface) {
                    // omit children output and put it directly in our stream
                    ((ImportListenerUserInterface) workflow).setListener(workflowInstance.getListener());
                }
                doTheWorkflowProcessingSingleThreaded(workflow, workflowInstance);
                removeFromRunning(workflowInstance.getId());
            }
        };
        // Thread thread = new Thread(runnable);
        WorkflowThreadInfo workflowThreadInfo = new WorkflowThreadInfo();
        workflowThreadInfo.setInstanceID(workflowInstance.getId());
        workflowThreadInfo.setWorkflowRunnable(runnable);
        workflowThreadInfo.setWorkflowInstance(workflowInstance);
        workflowThreadInfo.setWorkflowInstance(workflowInstance);
        Map<String, WorkflowThreadInfo> workflowThreads = threads.get(workflow.getWorkflowName());
        if (workflowThreads == null) {
            workflowThreads = new HashMap<>();
        }
        workflowThreads.put(workflowInstance.getId(), workflowThreadInfo);
        threads.put(workflow.getWorkflowName(), workflowThreads);
        taskExecutorHigh.execute(runnable);
    }

    private void checkThreadsForFinishedEntries() {

        for (Map.Entry<String, Map<String, WorkflowThreadInfo>> entry : threads.entrySet()) {
            Map<String, WorkflowThreadInfo> toRemove = new HashMap<>();
            for (Map.Entry<String, WorkflowThreadInfo> groupEntry : entry.getValue().entrySet()) {
                if (groupEntry.getValue().getWorkflowRunnable().isFinished()) {
                    LOGGER.info("Removing thread ... " + entry.getKey() + StringUtils.EMPTY + groupEntry.getKey());
                    toRemove.put(groupEntry.getKey(), groupEntry.getValue());
                }
            }
            for (Map.Entry<String, WorkflowThreadInfo> stringThreadMap : toRemove.entrySet()) {
                // removing...
                entry.getValue().remove(stringThreadMap.getValue());
            }
        }
    }

    private void doTheWorkflowProcessingSingleThreaded(Workflow workflow, WorkflowInstance workflowInstance) {
        // Threaded workflow processing handling
        if (!workflow.isValid()) {
            // Take cancellation branch!
            workflow.startCancellation();
        }
        try {
            // One step is undergone without checking, for continuation a stopped process
            // before calling processone step the current step

            // save state in instance BEFORE entering processonestep

            // enter one step
            workflow.processOneStep();
            WorkflowState lastState = workflow.getLastWorkflowState();
            // add the current last-state to workflowinstance
            workflowInstance.setCurrentState(lastState);
            // get new state from workflow
            WorkflowState newState = workflow.getWorkflowState();
            // now the current state is the state AFTER the current   step
            workflowInstance.setCurrentState(newState);
            // save after every step
            workflowInstance.setRunning(true);
            // load current context in previous saved step
            workflowInstanceService.save(workflowInstance);
            // We go one Step further, and store the state afterwards before processing the next step
            while (workflow.isContinuous()) {
                // Store workflow state BEFORE entering the process AND after!
                // then process one step
                workflow.processOneStep();
                lastState = workflow.getLastWorkflowState();
                // add the current last-state to workflowinstance
                workflowInstance.setCurrentState(lastState);
                // get new state
                newState = workflow.getWorkflowState();
                // check if state is a new element

                // thread checking
                boolean stopped = false;
                if (workflow instanceof ImportListenerUserInterface) {
                    if (!((ImportListenerUserInterface) workflow).getListener().shouldContinue()) {
                        stopped = true;
                    }
                } else if (workflow instanceof ImportListenerInterface) {
                    if (!((ImportListenerInterface) workflow).shouldContinue()) {
                        stopped = true;
                    }
                }
                if (stopped) {
                    newState.setWorkflowStateEnum(WorkflowStateEnum.STOPPED);
                }

                workflowInstance.setCurrentState(newState);
                // save after every step
                workflowInstance.setRunning(true);
                workflowInstanceService.save(workflowInstance);
                if (workflow.isIdle() || stopped) {
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionUtils.getRootCause(e);
            workflowInstance.setRetryCount(workflowInstance.getRetryCount() + 1);
            LOGGER.error("Error at Workflow Processing" + e.getMessage(), e);
            LOGGER.error("Root Cause:" + ExceptionUtils.getRootCauseMessage(e), e);
            setErrorState(workflow, workflowInstance);
            workflowInstance.getCurrentState().setErrorMessage(e.getMessage());
            Map<String, Object> themap = new HashMap<>();
            themap.put("exception", e);
            themap.put("rootExceptiopn", ExceptionUtils.getRootCause(e));
            themap.put("workflowInstance", workflowInstance);
            emailSenderService.sendHTMLVelocityTemplateEmailDirect(propertyService.getPropertyValue(QueueWorkerEmailSender.PROP_NAME_SENDMAIL_ADMINEMAIL), "WORKFLOW-ERROR " + workflowInstance.getWorkflowName(), "/templates/workflow/workflowerror.vm", themap);
        }
        LOGGER.trace("Workflow Element  is:" + workflow.getCurrentElementName());
        if (workflow.isFinished()) {

            workflowInstance.setFinished(true);
            workflowInstance.setWorkflowState(WorkflowStateEnum.FINISHED);
            checkThreadsForFinishedEntries();
        }

        // store
        workflowInstance.setRunning(false);
        workflowInstanceService.save(workflowInstance);
    }

    private void removeFromRunning(String workflowId) {

        for (Map<String, WorkflowThreadInfo> entry : threads.values()) {

            WorkflowThreadInfo found = null;
            for (WorkflowThreadInfo info : entry.values()) {
                if (info.getInstanceID().equals(workflowId)) {

                    found = info;
                    break;
                }
            }
            if (found != null) {

                //    found.getWorkflowRunnable().getListener().requestStop();

                entry.remove(workflowId);
            }
        }
    }

    private void setErrorState(Workflow workflow, WorkflowInstance workflowInstance) {
        // Mark Error State
        workflowInstance.getCurrentState().setWorkflowStateEnum(WorkflowStateEnum.ACTIVITY_ERROR);
        // Increase Error Counter (for whole workflow!
        workflowInstance.getCurrentState().setRetries(workflowInstance.getCurrentState().getRetries() + 1);
        if (workflowInstance.getCurrentState().getRetries() > 10) {
            // If error limit is reached...
            workflowInstance.getCurrentState().setWorkflowStateEnum(WorkflowStateEnum.CANCELLED_ERROR);
        }
        // if non-default error handler exists, use that as next state!
        if (!AbstractActivity.DEFAULT_ERROR_STATE_NAME.equals(workflow.getErrorStateNameFromCurrentActivity())) {
            // Transform to new state upon next run!
            workflowInstance.getCurrentState().setActivityName(workflow.getErrorStateNameFromCurrentActivity());
        }
    }

    /**
     * Check cancellation workflow.
     *
     * @param workflowInstance the workflow instance
     */
    public void checkCancellationWorkflow(WorkflowInstance workflowInstance) {

        if (workflowInstance.getRetryCount() > propertyService.getPropertyValueInteger(QueueWorkerEmailSender.PROP_NAME_MAX_RETRY_COUNT)) {

            // cancel the workflow...
            workflowInstance.setWorkflowState(WorkflowStateEnum.CANCELLED_ERROR);
            workflowInstanceService.save(workflowInstance);

        } else {
            Workflow workflow = null;
            try {
                workflow = constructWorkflow(workflowInstance, null);
            } catch (Exception e) {

                LOGGER.error("Workflow Construction Exception: " + e.getMessage(), e);
                workflowInstance.setRetryCount(workflowInstance.getRetryCount() + 1);
                workflowInstance.getListener().reportError("Workflow Initialisation Exception");
                workflowInstance.getListener().reportException(e);

                workflowInstanceService.save(workflowInstance);
                return;
            }
            try {

                if (!workflow.isValid()) {
                    // Take cancellation branch!
                    workflow.startCancellation();
                    if (workflow.getThreadingEnabled()) {
                        // Warning calling the threaded method means to leave this method directly
                        doTheWorkflowProcessingThreaded(workflow, workflowInstance);
                    } else {
                        doTheWorkflowProcessingSingleThreaded(workflow, workflowInstance);

                    }
                } else {
                    if (workflow.getThreadingEnabled()) {

                        doTheWorkflowProcessingThreaded(workflow, workflowInstance);
                    } else {
                        doTheWorkflowProcessingSingleThreaded(workflow, workflowInstance);

                    }
                }
            } catch (Exception ex) {

                LOGGER.debug(ex.getMessage(), ex);
            }
        }
    }

    /**
     * calls the cancellation branch of a workflow...
     *
     * @param workflowInstance the workflow instance
     */
    public void chancelWorkflow(WorkflowInstance workflowInstance) {

        Workflow workflow = constructWorkflow(workflowInstance, null);
        // Take cancellation branch!
        workflow.startCancellation();
        doTheWorkflowProcessingThreaded(workflow, workflowInstance);
    }

    /**
     * Process workflow.
     *
     * @param workflowInstance the workflow instance
     * @param data             the data
     * @return the workflow
     */
    public Workflow processWorkflow(WorkflowInstance workflowInstance, Map<String, String> data) {

        Workflow workflow = constructWorkflow(workflowInstance, data);
        if (workflow.getThreadingEnabled()) {
            doTheWorkflowProcessingThreaded(workflow, workflowInstance);
        } else {
            doTheWorkflowProcessingSingleThreaded(workflow, workflowInstance);

        }
        return workflow;
    }
}
