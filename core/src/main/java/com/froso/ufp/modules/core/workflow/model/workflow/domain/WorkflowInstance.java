package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.workflow.model.*;
import com.froso.ufp.modules.core.workflow.model.listener.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import java.util.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 20.12.13.
 */
@Document(collection = WorkflowInstance.TYPE_NAME)

@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("WORKFLOW")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("workflowName")
                }))

)
public class WorkflowInstance extends ClientReference implements ImportListenerUserInterface {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "WorkflowInstance";
    /**
     * The State activity error.
     */
    public static final String STATE_ACTIVITY_ERROR = "ACTIVITY_ERROR";
    /**
     * The State idle.
     */
    public static final String STATE_IDLE = "IDLE";
    /**
     * The State new.
     */
    public static final String STATE_NEW = "WAITING_TO_SEND";
    private List<WorkflowState> history = new ArrayList<>();
    private WorkflowState currentState;
    private String workflowName;
    private Boolean running = false;
    private Boolean finished = Boolean.FALSE;
    private String workflowDefinitionId;
    private WorkflowDefinition2 snapshotDefinition;
    @Transient
    private ImportListenerInterface listener;
    private Integer retryCount = 0;

    /**
     * Constructor Workflow instance.
     */
    public WorkflowInstance() {

        super(TYPE_NAME);
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public static String getTypeName() {

        return TYPE_NAME;
    }

    /**
     * Gets snapshot definition.
     *
     * @return the snapshot definition
     */
    public WorkflowDefinition2 getSnapshotDefinition() {
        return snapshotDefinition;
    }

    /**
     * Sets snapshot definition.
     *
     * @param snapshotDefinition the snapshot definition
     */
    public void setSnapshotDefinition(WorkflowDefinition2 snapshotDefinition) {
        this.snapshotDefinition = snapshotDefinition;
    }

    /**
     * Gets workflow definition id.
     *
     * @return the workflow definition id
     */
    public String getWorkflowDefinitionId() {
        return workflowDefinitionId;
    }

    /**
     * Sets workflow definition id.
     *
     * @param workflowDefinitionId the workflow definition id
     */
    public void setWorkflowDefinitionId(String workflowDefinitionId) {
        this.workflowDefinitionId = workflowDefinitionId;
    }

    /**
     * Gets retry count.
     *
     * @return the retry count
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * Sets retry count.
     *
     * @param retryCount the retry count
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * Gets finished.
     *
     * @return the finished
     */
    public Boolean getFinished() {
        return finished;
    }

    /**
     * Sets finished.
     *
     * @param finished the finished
     */
    public void setFinished(final Boolean finished) {

        this.finished = finished;
    }

    /**
     * Gets history.
     *
     * @return the history
     */
    public List<WorkflowState> getHistory() {

        return history;
    }

    /**
     * Sets history.
     *
     * @param history the history
     */
    public void setHistory(List<WorkflowState> history) {

        this.history = history;
    }

    /**
     * Gets workflow name.
     *
     * @return the workflow name
     */
    public String getWorkflowName() {

        return workflowName;
    }

    /**
     * Sets workflow name.
     *
     * @param workflowName the workflow name
     */
    public void setWorkflowName(String workflowName) {

        this.workflowName = workflowName;
    }

    /**
     * Gets workflow state.
     *
     * @return the workflow state
     */
    public WorkflowStateEnum getWorkflowState() {

        if (getCurrentState() == null) {
            return WorkflowStateEnum.NEW;
        }
        WorkflowStateEnum result = getCurrentState().getWorkflowStateEnum();
        if (result == null) {
            result = WorkflowStateEnum.NEW;
        }
        return result;
    }

    /**
     * Sets workflow state.
     *
     * @param workflowState the workflow state
     */
    public void setWorkflowState(WorkflowStateEnum workflowState) {

        if (getCurrentState() == null) {
            return;
        }
        getCurrentState().setWorkflowStateEnum(workflowState);
    }

    /**
     * Gets current state.
     *
     * @return the current state
     */
    public WorkflowState getCurrentState() {

        return currentState;
    }

    /**
     * Sets current state.
     *
     * @param newCurrentState the new current state
     */
    public void setCurrentState(WorkflowState newCurrentState) {
        // When we change the current state, it is added to the history as well
        if (currentState != null) {
            if (currentState.getActivityName() != null) {
                if (!currentState.getActivityName().equals(newCurrentState.getActivityName())) {
                    // just add if new...

                    history.add(currentState);
                    //     currentState.setFinishedDate(DateTime.now());

                }
            }
        }
        this.currentState = newCurrentState;
    }

    /**
     * Gets running.
     *
     * @return the running
     */
    public Boolean getRunning() {

        return running;
    }

    /**
     * Sets running.
     *
     * @param running the running
     */
    public void setRunning(Boolean running) {

        this.running = running;
    }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public Boolean isRunning() {

        return running;
    }

    /**
     * Is finished boolean.
     *
     * @return the boolean
     */
    public Boolean isFinished() {

        return finished;
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public ImportListenerInterface getListener() {
        if (listener == null) {
            listener = new DefaultImportListenerImpl();
            //  throw new RuntimeException("No Listener assigned");
        }

        return listener;
    }

    /**
     * Sets listener.
     *
     * @param listener the listener
     */
    public void setListener(final ImportListenerInterface listener) {

        this.listener = listener;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {

        return "WorkflowInstance:" + workflowName + " Current State:" + currentState;
    }
}
