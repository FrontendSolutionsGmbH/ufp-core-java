package com.froso.ufp.modules.core.workflow.service.workflow.threading;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * Created by ckleinhuix on 22.03.2015.
 */

public class WorkflowThreadInfo implements IDataObject {

    private String instanceID;
    private AbstractWorkflowRunnable workflowRunnable;
    private WorkflowInstance workflowInstance;

    /**
     * Gets instance iD.
     *
     * @return the instance iD
     */
    public String getInstanceID() {

        return instanceID;
    }

    /**
     * Sets instance iD.
     *
     * @param instanceID the instance iD
     */
    public void setInstanceID(final String instanceID) {

        this.instanceID = instanceID;
    }

    /**
     * Gets workflow runnable.
     *
     * @return the workflow runnable
     */
    public AbstractWorkflowRunnable getWorkflowRunnable() {

        return workflowRunnable;
    }

    /**
     * Sets workflow runnable.
     *
     * @param workflowRunnable the workflow runnable
     */
    public void setWorkflowRunnable(final AbstractWorkflowRunnable workflowRunnable) {

        this.workflowRunnable = workflowRunnable;
    }

    public WorkflowInstance getWorkflowInstance() {

        return workflowInstance;
    }

    public void setWorkflowInstance(final WorkflowInstance workflowInstance) {

        this.workflowInstance = workflowInstance;
    }
}
