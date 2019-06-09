package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element if continue.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         Element that branches by True/False Equation to follow-up Element.
 */
public class WorkflowElementIfContinue<C extends WorkflowContext> extends AbstractWorkflowElement<C> {
    private static final String NAME_NEXT = "NEXT";
    private final WorkflowActivityBoolean activity;

    /**
     * Constructor Workflow element if continue.
     *
     * @param name        the name
     * @param activity    the activity
     * @param elementNext the element next
     */
    public WorkflowElementIfContinue(String name, WorkflowActivityBoolean<C> activity, WorkflowElement<C> elementNext) {

        super(name);
        addChildElement(NAME_NEXT, elementNext);
        this.activity = activity;
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
    public WorkflowElement<C> process(C context) throws WorkflowException {

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        WorkflowElement result;
        if (activity.evaluate(context)) {
            setFinishedDate(DateTime.now());
            result = this.getChildElement(NAME_NEXT);
        } else {
            // return this on false
            result = this;
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

