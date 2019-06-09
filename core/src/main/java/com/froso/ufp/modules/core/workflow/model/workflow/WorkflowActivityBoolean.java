package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * The interface Workflow activity boolean.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis <p>         A True/False Branching Activity. Defines an Activity that may return true/false which is used by branching         workflows.
 */
public interface WorkflowActivityBoolean<C extends WorkflowContext> extends WorkflowActivity<C> {


    /**
     * Evaluate boolean.
     *
     * @param workflowContext the workflow context
     * @return the boolean
     * @throws WorkflowException the workflow exception
     */
    boolean evaluate(C workflowContext) throws WorkflowException;
}
