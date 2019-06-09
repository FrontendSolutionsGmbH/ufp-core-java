package com.froso.ufp.modules.core.workflow.service.workflow;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.12.13 Time: 16:06 To change this template use File | Settings |
 * File Templates.
 * <p>
 * The SimpleUser Workflow Context might be used by any workflow that deals with users, it provides various services
 * for
 * sending data to the coreUser, it contains a coreUser field, and the userservice itself for storing coreUser data persistently in
 * the database
 */
public class WorkflowContextImpl implements WorkflowContext {

    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "UserWorkflowContext";
    /**
     * The constant CONTEXTDATA_USERID.
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * Constructor CoreUser workflow context.
     */
    public WorkflowContextImpl() {

    }

    public Map<String, Object> getContextData() {
        return data;
    }


    public void loadContextData(Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            getContextData().put(entry.getKey(), entry.getValue());
        }
    }

}

