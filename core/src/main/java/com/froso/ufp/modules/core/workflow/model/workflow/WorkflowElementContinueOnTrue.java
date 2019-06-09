package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 20.02.14 Time: 11:13 To change this template use File | Settings | File
 * Templates.
 *
 * @param <C> the type parameter
 */
public class WorkflowElementContinueOnTrue<C extends WorkflowContext> extends AbstractWorkflowElement<C> {

    private static final String NAME_NEXT = "NEXT";
    private final WorkflowActivityBoolean activity;

    /**
     * Constructor Workflow element continue on true.
     *
     * @param name        the name
     * @param activity    the activity
     * @param elementNext the element next
     */
    public WorkflowElementContinueOnTrue(String name,
                                         WorkflowActivityBoolean<C> activity,
                                         WorkflowElement<C> elementNext) {

        super(name);
        this.addChildElement(NAME_NEXT, elementNext);
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
    public WorkflowElement process(C context) throws WorkflowException {

        WorkflowElement result;
        if (activity.evaluate(context)) {
            result = this.getChildElement(NAME_NEXT);
        } else {
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
