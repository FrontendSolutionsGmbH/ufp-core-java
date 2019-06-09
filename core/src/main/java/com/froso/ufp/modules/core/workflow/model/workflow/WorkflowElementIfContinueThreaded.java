package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.listener.*;
import java.util.*;
import org.joda.time.*;

/**
 * The type Workflow element if continue threaded.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         Element that branches by True/False Equation to follow-up Element.
 */
public class WorkflowElementIfContinueThreaded<C extends WorkflowContext> extends AbstractWorkflowElement<C> implements ImportListenerUserInterface {
    private static final String NAME_NEXT = "next";
    private final WorkflowActivityBoolean activity = null;

    private ImportListenerInterface currentImportListener;

    /**
     * Constructor Workflow element if continue.
     *
     * @param name        the name
     * @param activity    the activity
     * @param elementNext the element next
     */
    public WorkflowElementIfContinueThreaded(String name) {

        super(name);
        //  addChildElement(NAME_NEXT, elementNext);
        //   this.activity = activity;
        currentImportListener = new DefaultImportListenerImpl(name);
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

        if (getStartDate() == null) {
            setStartDate(DateTime.now());
        }
        if (activity instanceof ImportListenerUserInterface) {
            // currentImportListener.addChildListener(((ImportListenerUserInterface) activity).getListener());
            // suppress insterting an own listener,    by setting child objects listener to our own
            ((ImportListenerUserInterface) activity).setListener(currentImportListener);
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
    protected void doProcessInput(C context,
                                  Map<String, String> theInput) {
        // do nothing, needed for implementing method
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    @Override
    public ImportListenerInterface getListener() {

        return currentImportListener;
    }

    /**
     * Sets listener.
     *
     * @param listenerNew the listener new
     */
    @Override
    public void setListener(ImportListenerInterface listenerNew) {

        currentImportListener = listenerNew;
    }
}

