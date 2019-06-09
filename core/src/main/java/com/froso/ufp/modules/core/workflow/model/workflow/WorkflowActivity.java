package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * A General Action to be executed  in a workflow.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis
 */
public interface WorkflowActivity<C extends WorkflowContext> {


    /**
     * Gets props.
     *
     * @return the props
     */
    PropsManager getProps();

    /**
     * This method is used for signalising that the current activity is valid ;) validity is decided by activities
     * themselves, either due to errors, original use case is to find timeouted workflows, like registration,
     * activation, auth_email forgots...
     *
     * @param c the c
     * @return the boolean
     */
    boolean isValid(C c);

    /**
     * the isIdle() method determines if the whole workflow is in an idle state, this is not queried for
     * the whole workflow but just for the current workflow-step
     *
     * @param c the c
     * @return the boolean
     */
    boolean isIdle(C c);

    /**
     * Gets error state name.
     *
     * @return the name of the state the workflow should go into if an error happened
     */
    String getErrorStateName();

    /**
     * Sets error state name.
     *
     * @param errorStateName the error state name
     */
    void setErrorStateName(String errorStateName);
}
