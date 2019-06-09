package com.froso.ufp.modules.core.workflow.model.workflow;
/**
 * Created by ckleinhuix on 24.12.13.
 */

import java.util.*;

public abstract class AbstractWorkflowContext implements WorkflowContext {

    private final Map<String, Object> contextData = new HashMap();
    private final String name;
    private String corporateId;

    /**
     * Constructor Base workflow context.
     *
     * @param identifier the identifier
     */
    protected AbstractWorkflowContext(String identifier) {

        name = identifier;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public final String getIdentifier() {

        return name;
    }

    /**
     * Gets context data.
     *
     * @return the context data
     */
    @Override
    public final Map<String, Object> getContextData() {

        return contextData;
    }

    /**
     * Load context data.
     *
     * @param data the data
     */
    @Override
    public final void loadContextData(Map<String, Object> data) {

        doLoadContextData(data);
    }

    /**
     * Do load context data.
     *
     * @param data the data
     */
    protected abstract void doLoadContextData(Map<String, Object> data);

    /**
     * Gets corporate id.
     *
     * @return the corporate id
     */
    public String getCorporateId() {

        return corporateId;
    }

    /**
     * Sets corporate id.
     *
     * @param corporateId the corporate id
     */
    public void setCorporateId(String corporateId) {

        this.corporateId = corporateId;
    }
}
