package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element if else.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         Element that branches by True/False Equation to follow-up Element.
 */
public class WorkflowElementIfElse<C extends WorkflowContext> extends AbstractWorkflowElement<C> {

    private WorkflowActivityBoolean activity = null;


    /**
     * Instantiates a new Workflow element if else.
     *
     * @param name the name
     */
    public WorkflowElementIfElse(String name) {
        super(name);
    }


    /**
     * Constructor Workflow element if else.
     *
     * @param name         the name
     * @param activity     the activity
     * @param elementTrue  the element true
     * @param elementFalse the element false
     */
    public WorkflowElementIfElse(String name,
                                 WorkflowActivityBoolean<C> activity,
                                 WorkflowElement<C> elementTrue,
                                 WorkflowElement<C> elementFalse) {

        super(name);
        addChildElement(WorkflowStepConstants.STEP_TRUE.toString(), elementTrue);
        addChildElement(WorkflowStepConstants.STEP_FALSE.toString(), elementFalse);
        this.activity = activity;
    }

    protected void doSetActivity(WorkflowActivity activityIn) throws WorkflowException {
        if (activityIn instanceof WorkflowActivityBoolean) {
            this.activity = (WorkflowActivityBoolean) activityIn;
        } else {

            throw new WorkflowException("WorkflowElementIfElse: Activity has to be of type Boolean ");

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
     * Process workflow element.
     *
     * @param context the context
     * @return the workflow element
     * @throws WorkflowException the workflow exception
     */
    @Override
    public WorkflowElement process(C context) throws WorkflowException {

        WorkflowElement result;
        if (activity.evaluate(context)) {
            setFinishedDate(DateTime.now());
            result = this.getChildElement(WorkflowStepConstants.STEP_TRUE.toString());
        } else {
            setFinishedDate(DateTime.now());
            result = this.getChildElement(WorkflowStepConstants.STEP_FALSE.toString());
        }
        return result;
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
    protected void doProcessInput(C context,
                                  Map<String, String> theInput) {
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

