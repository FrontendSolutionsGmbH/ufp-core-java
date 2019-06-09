package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import org.joda.time.*;

/**
 * @author c.Kleinhuis (ck@froso.de)
 *         <p>
 *         The EndElement does not define a follow up element. After this element has been evaluated the workflow ends
 *         because no follow up element is available
 */

public class WorkflowElementEnd<C extends WorkflowContext> extends AbstractWorkflowElement<C> {

    private final WorkflowActivityVoid activity;

    /**
     * Constructor Workflow element end.
     *
     * @param name     the name
     * @param activity the activity
     */
    public WorkflowElementEnd(String name,
                              WorkflowActivityVoid<C> activity) {

        super(name);
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

        boolean result = true;
        if (null != activity) {
            result = activity.isValid(c);
        }
        return result;
    }

    /**
     * Process workflow element.
     *
     * @param context the context
     * @return the workflow element
     * @throws WorkflowException the workflow exception
     */
    @Override
    @JsonManagedReference
    public AbstractWorkflowElement<C> process(C context) throws WorkflowException {

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        if (null != activity) {
            activity.evaluate(context);
        }
        // End of the workflow is indicated by a null return...
        return null;
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
