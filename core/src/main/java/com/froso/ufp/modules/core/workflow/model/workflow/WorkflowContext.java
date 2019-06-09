package com.froso.ufp.modules.core.workflow.model.workflow;

import java.util.*;

/**
 * The interface Workflow context.
 *
 * @author c.Kleinhuis (ck@froso.de)         <p>         Each workflow needs a context to work on, contexts may contain everything. simple data or complex services,         additionally a context posses a contextdata element where workflow data might be stored
 */
public interface WorkflowContext {


    /**
     * Gets context data.
     *
     * @return the context data
     */
    Map<String, Object> getContextData();

    /**
     * Load context data.
     *
     * @param data the data
     */
    void loadContextData(Map<String, Object> data);

}
