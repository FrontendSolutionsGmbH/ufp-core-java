package com.froso.ufp.modules.core.workflow.service.workflow.threading;

import com.froso.ufp.modules.core.workflow.model.listener.*;

/**
 * Created by ckleinhuix on 22.03.2015.
 */
public abstract class AbstractWorkflowRunnable implements Runnable, ImportListenerUserInterface {
    /**
     * The Listener.
     */
    protected ImportListenerInterface listener;
    private boolean finished;

    /**
     * Constructor Abstract workflow runnable.
     */
    public AbstractWorkflowRunnable() {

        listener = new DefaultImportListenerImpl("WorkflowRunnable");
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public ImportListenerInterface getListener() {

        return listener;
    }

    /**
     * Sets listener.
     *
     * @param listener the listener
     */
    public void setListener(final ImportListenerInterface listener) {

        this.listener = listener;
    }

    /**
     * Run void.
     */
    public final void run() {

        setFinished(false);
        doRun();
        setFinished(true);
    }

    /**
     * Run void.
     */
    abstract protected void doRun();

    /**
     * Is finished.
     *
     * @return the boolean
     */
    public boolean isFinished() {

        return finished;
    }

    /**
     * Sets finished.
     *
     * @param finished the finished
     */
    public void setFinished(final boolean finished) {

        this.finished = finished;
    }
}
