package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 24.01.14 Time: 12:57 To change this template use File | Settings |
 * File Templates.
 *
 * @param <C> the type parameter
 */
public abstract class AbstractActivityVoid<C extends WorkflowContext> extends AbstractActivity<C> implements WorkflowActivityVoid<C> {


    /**
     * Evaluate void.
     *
     * @param c the c
     */
    @Override
    public abstract void evaluate(C c) throws WorkflowException;


    @Override
    public ActivityTypeEnum getActivityType() {
        return ActivityTypeEnum.VOID;
    }
}
