package com.froso.ufp.modules.core.workflow.model.workflow.factory;

import com.froso.ufp.modules.core.workflow.model.workflow.*;

/**
 * @author c.Kleinhuis  on 15.12.13.
 *
 *         Provides essentials to create a workflow.
 */
public interface WorkflowFactoryManufactorer {

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets workflow.
     *
     * @return the workflow
     */
    WorkflowDefinition getWorkflow();

    /**
     * Gets context.
     *
     * @return the context
     */
    WorkflowContext getContext();
}
