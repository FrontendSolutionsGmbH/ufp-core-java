package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * The interface Workflow activity void.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         A void activity just performs some actions. No decision is done, used for sequential workflows.
 */
public interface WorkflowActivityVoid<C extends WorkflowContext> extends WorkflowActivity<C> {
    /**
     * The constant STEP_NEXT.
     */

    /**
     * Actual Action Method for this activity, it has a void return value.
     *
     * @param workflowContext The belonging contextz
     * @throws WorkflowException the workflow exception
     */
    void evaluate(C workflowContext) throws WorkflowException;
}
