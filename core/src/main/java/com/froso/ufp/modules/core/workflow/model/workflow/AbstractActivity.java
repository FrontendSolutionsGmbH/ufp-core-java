package com.froso.ufp.modules.core.workflow.model.workflow;

import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 24.01.14 Time: 12:57 To change this template use File | Settings |
 * File Templates.
 *
 * @param <C> the type parameter
 */
abstract public class AbstractActivity<C extends WorkflowContext> implements Activity<C> {
    /**
     * The constant DEFAULT_ERROR_STATE_NAME.
     */
    public static final String DEFAULT_ERROR_STATE_NAME = "defaultError";
    private String errorStateName = DEFAULT_ERROR_STATE_NAME;
    private PropsManager props = new PropsManager();

    /**
     * Instantiates a new Abstract activity.
     */
    public AbstractActivity() {
        initialise();
    }


    /**
     * Initialise.
     */
    protected void initialise() {
    }

    abstract public ActivityTypeEnum getActivityType();

    /**
     * Gets props.
     *
     * @return the props
     */
    @Override
    public PropsManager getProps() {
        return props;
    }

    /**
     * Sets props.
     *
     * @param props the props
     */
    @Override
    public void setProps(PropsManager props) {
        this.props = props;
    }

    /**
     * Is valid.
     *
     * @param c the c
     * @return the boolean
     */
// Default implementation of isValid, just return true
    @Override
    public boolean isValid(C c) {

        return true;
    }

    /**
     * Is idle.
     *
     * @param c the c
     * @return the boolean
     */
// the isIdle implementation to signalize if the workflow shall be in its idle mode
    @Override
    public boolean isIdle(C c) {

        return false;
    }

    /**
     * Gets error state name.
     *
     * @return the name of the state the workflow should go into if an error happened
     */
    @Override
    public String getErrorStateName() {

        return errorStateName;
    }

    /**
     * Sets error state name.
     *
     * @param newName the new name
     */
    @Override
    public void setErrorStateName(String newName) {

        errorStateName = newName;
    }
}
