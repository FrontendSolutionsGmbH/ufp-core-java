package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element output.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         This Element outputs data and handles input data.
 */
public class WorkflowElementOutput<C extends WorkflowContext> extends AbstractWorkflowElement<C> {


    /**
     * Constructor Workflow element output.
     *
     * @param name the name
     * @param next the next
     */
    protected WorkflowElementOutput(String name,
                                    WorkflowElement next) {

        super(name);
        setContinuous(false);
        addChildElement(WorkflowStepConstants.STEP_NEXT.toString(), next);
    }

    /**
     * Check is valid.
     *
     * @param c the c
     * @return the boolean
     */
    @Override
    protected boolean checkIsValid(C c) {

        return true;
    }

    /**
     * Process workflow element.
     *
     * @param context the context
     * @return the workflow element
     */
    @Override
    public WorkflowElement process(C context) {

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        WorkflowElement result = getChildElement(WorkflowStepConstants.STEP_NEXT.toString());
        setFinishedDate(DateTime.now());
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
}
