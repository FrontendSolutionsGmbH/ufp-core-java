package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element sequence.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         Element which continues with follow-up elemnt when finished. One succsesive element that shall be executed         after this element.
 */
public class WorkflowElementSequence<C extends WorkflowContext> extends AbstractWorkflowElement<C> {

    /**
     * The constant NAME_NEXT.
     */
    /**
     * The Activity.
     */
    protected WorkflowActivityVoid activity = null;

    /**
     * Constructor Workflow element sequence.
     *
     * @param name the name
     */
    public WorkflowElementSequence(String name) {

        super(name);
    }

    /**
     * Instantiates a new Workflow element sequence.
     *
     * @param name     the name
     * @param activity the activity
     * @param next     the next
     */
    public WorkflowElementSequence(String name, WorkflowActivityVoid<C> activity, WorkflowElement<C> next) {

        super(name);
        addChildElement(WorkflowStepConstants.STEP_NEXT.toString(), next);
        this.activity = activity;
    }

    protected void doSetActivity(WorkflowActivity activityIn) throws WorkflowException {
        if (activityIn instanceof WorkflowActivityVoid) {
            this.activity = (WorkflowActivityVoid) activityIn;
        } else {

            throw new WorkflowException("WorkflowElementSequence: Activity has to be of type Void ");

        }
    }

    /**
     * Check is valid.
     *
     * @param c the c
     * @return the boolean
     */
    @Override
    protected boolean checkIsValid(C c) {

        return activity.isValid(c);
    }

    /**
     * Check is idle.
     *
     * @param c the c
     * @return the boolean
     */
    // Template Method to be overriden by subclasses
    @Override
    public boolean checkIsIdle(C c) {

        return activity.isIdle(c);
    }

    /**
     * Process workflow element.
     *
     * @param context the context
     * @return the workflow element
     * @throws WorkflowException the workflow exception
     */
    @Override
    public WorkflowElement process(C context) throws WorkflowException {

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        activity.evaluate(context);
        if (activity.isIdle(context)) {
            return this;
        }
        setFinishedDate(DateTime.now());
        return this.getChildElement(WorkflowStepConstants.STEP_NEXT.toString());
    }

    /**
     * Do output.
     *
     * @param context the context
     * @return the map
     */
    @Override
    protected Map<String, Object> doOutput(C context) {

        return new HashMap<>();
    }

    /**
     * Do process input.
     *
     * @param context  the context
     * @param theInput the the input
     */
    @Override
    protected void doProcessInput(C context, Map<String, String> theInput) {
        // do nothing, needed for implementing method
    }

    /**
     * Gets state name on error.
     *
     * @return the state name on error
     */
    @Override
    public String getStateNameOnError() {

        return activity.getErrorStateName();
    }

    /**
     * Sets state name on error.
     *
     * @param input the input
     */
    @Override
    public void setStateNameOnError(String input) {

        activity.setErrorStateName(input);
    }
}
