package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

/**
 * The type Workflow instance service.
 *
 * @author c.Kleinhuis (ck@froso.de) on 14.12.13.                  Manages WorkflowState Instances.
 */
@Service
public class WorkflowInstanceService extends AbstractRepositoryService2<WorkflowInstance> {


    /**
     * Constructor
     */
    public WorkflowInstanceService() {

        super(WorkflowInstance.TYPE_NAME);
    }

    /**
     * Create workflow instance.
     *
     * @param state        the state
     * @param uniqueFields the unique fields
     * @return the workflow instance
     */
    public WorkflowInstance createWorkflowInstance(WorkflowState state, Set<String> uniqueFields) {

        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setWorkflowName(state.getWorkflowName());
        workflowInstance.setCurrentState(state);
        workflowInstance = save(workflowInstance);
        return workflowInstance;
    }

    /**
     * Find errornous workflows list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<WorkflowInstance> findErrornousWorkflows(Pageable pageable) {
        return findByKeyValue("currentState.workflowStateEnum", WorkflowInstance.STATE_ACTIVITY_ERROR);

    }


    /**
     * Find idle workflows list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<WorkflowInstance> findIdleWorkflows(Pageable pageable) {
        return findByKeyValue("currentState.workflowStateEnum", WorkflowInstance.STATE_IDLE);

    }

    /**
     * Find new workflows list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<WorkflowInstance> findNewWorkflows(Pageable pageable) {
        return findByKeyValue("currentState.workflowStateEnum", WorkflowInstance.STATE_NEW);

    }

    /**
     * Find by workflow name and context data.
     *
     * @param workflowName     the workflow name
     * @param contextDataField the context data field
     * @param contextDataValue the context data value
     * @return the workflow instance
     */
    public WorkflowInstance findByWorkflowNameAndContextData(String workflowName, String contextDataField, String contextDataValue) {
        // Build a search map
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("workflowName", workflowName);
        stringMap.put("currentState.contextData." + contextDataField, contextDataValue);
        WorkflowInstance result = null;
        IDataObjectList resultSearch = searchPaged(stringMap);
        if (!resultSearch.getList().isEmpty()) {
            result = (WorkflowInstance) resultSearch.getList().get(0);
        }
        return result;
    }

    /**
     * Search by workflow name and context data data object list.
     *
     * @param workflowName     the workflow name
     * @param contextDataField the context data field
     * @param contextDataValue the context data value
     * @return the data object list
     */
    public IDataObjectList searchByWorkflowNameAndContextData(String workflowName, String contextDataField, String contextDataValue) {
        // Build a search map
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("workflowName", workflowName);
        stringMap.put("currentState.contextData." + contextDataField, contextDataValue);
        WorkflowInstance result = null;
        IDataObjectList resultSearch = searchPaged(stringMap);

        return resultSearch;
    }

    /**
     * Findby workflow name and user id.
     *
     * @param workflowName the workflow name
     * @param userId       the user id
     * @return the list
     */
    public List<WorkflowInstance> findbyWorkflowNameAndUserId(String workflowName, String userId) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("workflowName", workflowName);
        searchMap.put(" currentState.contextData.USER_ID", userId);

        return findByKeyValues(searchMap);
    }
}
