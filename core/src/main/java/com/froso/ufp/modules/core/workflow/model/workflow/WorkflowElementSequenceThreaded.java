package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.listener.*;
import org.joda.time.*;

/**
 * The type Workflow element sequence threaded.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)                  Element which continues with follow-up elemnt when finished. One succsesive element that shall be executed         after this element.
 */
public class WorkflowElementSequenceThreaded<C extends WorkflowContext> extends WorkflowElementSequence<C> implements ImportListenerUserInterface {
    /**
     * The constant PROP_NAME_LISTENER.
     */
    public static final String PROP_NAME_LISTENER = "listener";

    private ImportListenerInterface currentImportListener;

    /**
     * Constructor Workflow element sequence threaded.
     *
     * @param name     the name
     * @param activity the activity
     * @param next     the next
     */
    public WorkflowElementSequenceThreaded(String name,
                                           WorkflowActivityVoid<C> activity,
                                           WorkflowElement<C> next) {

        super(name, activity, next);
        currentImportListener = new DefaultImportListenerImpl(name);
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
        if (activity instanceof ImportListenerUserInterface) {
            //        currentImportListener.addChildListener(((ImportListenerUserInterface) activity).getListener());
            // suppress insterting an own listener,    by setting child objects listener to our own
            ((ImportListenerUserInterface) activity).setListener(currentImportListener);
        }

        currentImportListener.reportInfo(getName() + " Started ");
        WorkflowElement<C> result = super.process(context);
        currentImportListener.reportInfo(getName() + " Finished");
        currentImportListener.reportInfo("Next is then: " + result.getName());
        setFinishedDate(DateTime.now());
        return result;
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
