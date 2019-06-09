package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;

/**
 * The type Workflow.
 *
 * @author c.Kleinhuis on 15.12.13.         <p>         An Workflow Processor. The Workflow consists of the context and a definition.
 */
public class Workflow {
    /**
     * The Element that is currently processed by this workflow
     */
    private boolean solitary;
    private WorkflowElement elementBefore;
    private WorkflowElement currentElement;
    /**
     * the workflow context data
     */
    private WorkflowContext context;
    /**
     * the workflow definition
     */
    private WorkflowDefinition definition;
    private WorkflowStateEnum state;
    private Map<String, String> output;

    private Integer straightStepCount = 0;
    private WorkflowState lastState;

    /**
     * constructor
     */
    public Workflow() {

        state = WorkflowStateEnum.NEW;
    }

    /**
     * Gets threading enabled.
     *
     * @return the threading enabled
     */
    public Boolean getThreadingEnabled() {
        return getDefinition().getThreadingEnabled();
    }

    /**
     * Gets straight step count.
     *
     * @return the straight step count
     */
    public Integer getStraightStepCount() {

        return straightStepCount;
    }

    /**
     * Sets straight step count.
     *
     * @param straightStepCount the straight step count
     */
    public void setStraightStepCount(final Integer straightStepCount) {

        this.straightStepCount = straightStepCount;
    }

    /**
     * Is finished.
     *
     * @return the boolean
     */
    public boolean isFinished() {

        return (null == currentElement) || currentElement.isFinal();
    }

    /**
     * Gets workflow name.
     *
     * @return the workflow name
     */
    public String getWorkflowName() {

        return definition.getName();
    }

    /**
     * Is idle.
     *
     * @return the boolean
     */
    public boolean isIdle() {

        if (currentElement == null) {
            return false;
        }
        return currentElement.checkIsIdle(getContext());
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public WorkflowContext getContext() {

        return context;
    }

    /**
     * Sets context.
     *
     * @param context the context
     */
    public void setContext(WorkflowContext context) {

        this.context = context;
    }

    /**
     * performs a call of al subsequent workflowelements.valid() method and returns false if any of them returns false!
     *
     * @return boolean boolean
     */
    public boolean isValid() {

        return definition.getRoot().isValid(context);
    }

    /**
     * Start cancellation.
     */
    public void startCancellation() {

        currentElement = definition.getCancel();
    }

    /**
     * Is continuous.
     *
     * @return the boolean
     */
    public boolean isContinuous() {

        return (null != currentElement) && (currentElement.isContinuous()) && (WorkflowStateEnum.ACTIVITY_ERROR != state);
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public WorkflowStateEnum getState() {

        return state;
    }

    /**
     * Gets definition.
     *
     * @return the definition
     */
    public WorkflowDefinition getDefinition() {

        return definition;
    }

    /**
     * Sets definition.
     *
     * @param definition the definition
     */
    public void setDefinition(WorkflowDefinition definition) {

        this.definition = definition;
        this.currentElement = definition.getRoot();
    }

    /**
     * Gets last workflow state.
     *
     * @return the last workflow state
     */
    public WorkflowState getLastWorkflowState() {

        WorkflowState result = new WorkflowState();
        result.setWorkflowStateEnum(state);
        // return a copy of the context data
        for (Map.Entry<String, Object> entry : context.getContextData().entrySet()) {
            result.getContextData().put(entry.getKey(), entry.getValue());
        }
        //result.setContextData(context.getContextData());
        result.setWorkflowName(definition.getName());
        // if currentelement is null, the workflow has ended!
        if (null != elementBefore) {
            result.setActivityName(elementBefore.getName());
            result.setFinishedDate(elementBefore.getFinishedDate());
            result.setCreationDate(elementBefore.getStartDate());
        }
        return result;
    }

    /**
     * Gets last state.
     *
     * @return the last state
     */
    public WorkflowState getLastState() {

        return lastState;
    }

    /**
     * Sets last state.
     *
     * @param lastState the last state
     */
    public void setLastState(final WorkflowState lastState) {

        this.lastState = lastState;
    }

    /**
     * Gets workflow state.
     *
     * @return the workflow state
     */
    public WorkflowState getWorkflowState() {

        WorkflowState result = new WorkflowState();
        // return a copy of the context data
        for (Map.Entry<String, Object> entry : context.getContextData().entrySet()) {
            result.getContextData().put(entry.getKey(), entry.getValue());
        }
        //result.setContextData(context.getContextData());
        result.setWorkflowName(definition.getName());
        // if currentelement is null, the workflow has ended!
        if (null != currentElement) {
            result.setActivityName(currentElement.getName());
            if (currentElement.getStartDate() != null) {
                result.setCreationDate(currentElement.getStartDate());
            }
            result.setFinishedDate(currentElement.getFinishedDate());
        }
        return result;
    }

    /**
     * Load from state.
     *
     * @param state the state
     */
    public void loadFromState(WorkflowState state) {

        context.loadContextData(state.getContextData());
        // then we need to determine the current activity in the workflow
        currentElement = definition.getRoot().findWorkflowElementByName(state.getActivityName());
        if (null != currentElement) {
            output = currentElement.getOutput(context);
        }
    }

    /**
     * Gets current element name.
     *
     * @return the current element name
     */
    public String getCurrentElementName() {

        String result = "finished";
        if (state != WorkflowStateEnum.FINISHED) {
            if (null != currentElement) {
                result = currentElement.getName();
            }
        }
        return result;
    }

    /**
     * Gets error state name from current activity.
     *
     * @return the error state name from current activity
     */
    public String getErrorStateNameFromCurrentActivity() {

        String result = AbstractActivity.DEFAULT_ERROR_STATE_NAME;
        if (null != currentElement) {
            result = currentElement.getStateNameOnError();
        }
        return result;
    }

    /**
     * Sets current element.
     *
     * @param currentElement the current element
     */
    public void setCurrentElement(AbstractWorkflowElement currentElement) {

        this.currentElement = currentElement;
    }

    /**
     * Process one step.
     *
     * @throws Exception the exception
     */
    public void processOneStep() throws Exception {

        elementBefore = currentElement;
        handleElementBeforeStep(currentElement);
        doProcessOneStep();
        handleElementAfterStep(currentElement);
    }

    /**
     * Handle element before step.
     *
     * @param element the element
     */
    protected void handleElementBeforeStep(WorkflowElement element) {
        // template method
    }

    private void doProcessOneStep() throws Exception {

        if ((WorkflowStateEnum.ACTIVITY_ERROR != this.state) && (null != currentElement)) {
            state = WorkflowStateEnum.ACTIVITY_BEGIN;
            currentElement = currentElement.process(context);
            state = WorkflowStateEnum.ACTIVITY_END;
            if (null == currentElement) {
                state = WorkflowStateEnum.FINISHED;
            } else {
                output = currentElement.getOutput(context);
                if (currentElement.checkIsIdle(context)) {
                    state = WorkflowStateEnum.IDLE;
                }
            }
            // save step before ... :/
            saveStateAfterProcessing();
        }
    }

    /**
     * Handle element after step.
     *
     * @param element the element
     */
    protected void handleElementAfterStep(WorkflowElement element) {
        // template method
    }

    private void saveStateAfterProcessing() {

        lastState = new WorkflowState();
        lastState.setWorkflowStateEnum(state);
        // return a copy of the context data
        for (Map.Entry<String, Object> entry : context.getContextData().entrySet()) {
            lastState.getContextData().put(entry.getKey(), entry.getValue());
        }
        //result.setContextData(context.getContextData());
        lastState.setWorkflowName(definition.getName());
        // if currentelement is null, the workflow has ended!
        if (null != elementBefore) {
            lastState.setActivityName(elementBefore.getName());
        }
    }

    /**
     * Is solitary.
     *
     * @return the boolean
     */
    public boolean isSolitary() {

        return solitary;
    }

    /**
     * Sets solitary.
     *
     * @param solitary the solitary
     */
    public void setSolitary(final boolean solitary) {

        this.solitary = solitary;
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public Map<String, String> getOutput() {

        return output;
    }

    /**
     * Sets input.
     *
     * @param data the data
     */
    public void setInput(Map<String, String> data) {

        if (null != currentElement) {
            currentElement.processInput(context, data);
        }
    }
}
