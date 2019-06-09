package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 24.01.14 Time: 12:57 To change this template use File | Settings |
 * File Templates.
 *
 * @param <C> the type parameter
 */
public abstract class AbstractActivityBoolean<C extends WorkflowContext> extends AbstractActivity<C> implements WorkflowActivityBoolean<C> {


    /**
     * Evaluate boolean.
     *
     * @param c the c
     * @return the boolean
     * @throws WorkflowException the workflow exception
     */
    @Override
    public abstract boolean evaluate(C c) throws WorkflowException;

    @Override
    public ActivityTypeEnum getActivityType() {
        return ActivityTypeEnum.BOOLEAN;
    }
}
