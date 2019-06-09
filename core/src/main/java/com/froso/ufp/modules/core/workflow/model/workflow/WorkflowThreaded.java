package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.listener.*;

/**
 * Created by ckleinhuix on 23.03.2015.
 */
public class WorkflowThreaded extends Workflow implements ImportListenerUserInterface {

    private ImportListenerInterface importListenerInterface;

    /**
     * Handle element before step.
     *
     * @param element the element
     */
    @Override
    protected void handleElementBeforeStep(WorkflowElement element) {

        if (element instanceof ImportListenerUserInterface) {
            ((ImportListenerUserInterface) element).setListener(getListener());
        }
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    @Override
    public ImportListenerInterface getListener() {

        if (importListenerInterface == null) {
            // create listener on the fly with current workflow name
            importListenerInterface = new DefaultImportListenerImpl("WorkflowThreaded" + getWorkflowName());
        }
        return importListenerInterface;
    }

    /**
     * Sets listener.
     *
     * @param listenerNew the listener new
     */
    @Override
    public void setListener(ImportListenerInterface listenerNew) {

        importListenerInterface = listenerNew;
    }
}
