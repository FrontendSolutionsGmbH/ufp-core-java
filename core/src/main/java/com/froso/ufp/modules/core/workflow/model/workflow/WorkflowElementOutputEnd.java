package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element output end.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)                  Defines a Element which might output data, but does not contain a follow up element. Used to output content         to the user upon workflow end
 */
public class WorkflowElementOutputEnd<C> extends AbstractWorkflowElement<C> {

    /**
     * Constructor Workflow element output end.
     *
     * @param name the name
     */
    public WorkflowElementOutputEnd(String name) {

        super(name);
        setContinuous(false);
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
    public AbstractWorkflowElement process(C context) {
        // Create Infinite Loop staying in this state/element

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        setFinishedDate(DateTime.now());
        return this;
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
