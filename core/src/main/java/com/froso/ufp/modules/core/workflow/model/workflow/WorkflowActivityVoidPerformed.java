package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * @author c.Kleinhuis (ck@froso.de)
 *
 *         A void activity just performs some actions. No decision is done, used for sequential workflows.
 */

public interface WorkflowActivityVoidPerformed<C extends WorkflowContext> extends WorkflowActivityVoid<C> {

    /**
     * Actual Action Method for this activity, it has a void return value.
     *
     * @param workflowContext The belonging contextz
     * @throws @link WorkflowException
     */
    boolean performed(C workflowContext) throws WorkflowException;
}
