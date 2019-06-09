package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * Created by ckleinhuix on 12.01.2016.
 *
 * @param <C> the type parameter
 */
public interface Activity<C extends WorkflowContext> {
    /**
     * Gets props.
     *
     * @return the props
     */
    PropsManager getProps();

    /**
     * Sets props.
     *
     * @param props the props
     */
    void setProps(PropsManager props);

    /**
     * Gets activity type.
     *
     * @return the activity type
     */
    ActivityTypeEnum getActivityType();

    /**
     * Is valid boolean.
     *
     * @param c the c
     * @return the boolean
     */
// Default implementation of isValid, just return true
    boolean isValid(C c);

    /**
     * Is idle boolean.
     *
     * @param c the c
     * @return the boolean
     */
// the isIdle implementation to signalize if the workflow shall be in its idle mode
    boolean isIdle(C c);

    /**
     * Gets error state name.
     *
     * @return the error state name
     */
    String getErrorStateName();

    /**
     * Sets error state name.
     *
     * @param newName the new name
     */
    void setErrorStateName(String newName);
}
