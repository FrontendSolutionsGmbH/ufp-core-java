package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.workflow.model.*;
import java.util.*;
import org.apache.commons.lang3.builder.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 14.12.13.
 */
public class WorkflowState {

    private Integer retries = 0;
    private String activityName;
    private DateTime creationDate = DateTime.now();
    private DateTime finishedDate = null;
    private Map<String, Object> contextData = new HashMap<>();
    private String workflowName;
    private String corporateId;
    private WorkflowStateEnum workflowStateEnum = WorkflowStateEnum.NEW;

    private String errorMessage;

    /**
     * Gets workflow state enum.
     *
     * @return the workflow state enum
     */
    public WorkflowStateEnum getWorkflowStateEnum() {

        return workflowStateEnum;
    }

    public void setWorkflowStateEnum(WorkflowStateEnum workflowStateEnum) {

        this.workflowStateEnum = workflowStateEnum;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCreationDate() {

        return creationDate;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCreationDate(DateTime creationDate) {

        this.creationDate = creationDate;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getFinishedDate() {

        return finishedDate;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setFinishedDate(final DateTime finishedDate) {

        this.finishedDate = finishedDate;
    }

    public Integer getRetries() {

        return retries;
    }

    public void setRetries(Integer retries) {

        this.retries = retries;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public String getWorkflowName() {

        return workflowName;
    }

    public void setWorkflowName(String workflowName) {

        this.workflowName = workflowName;
    }

    public void loadContextData(WorkflowState state) {
        // make sure not to destroy the list if we are copying from our selves...
        Map<String, Object> contextDataNew = new HashMap<>();
        for (Map.Entry<String, Object> entry : state.getContextData().entrySet()) {
            contextDataNew.put(entry.getKey(), entry.getValue());
        }
        contextData.clear();
        contextData.putAll(contextDataNew);
    }

    public Map<String, Object> getContextData() {

        return contextData;
    }

    public void setContextData(Map<String, Object> contextData) {

        this.contextData = contextData;
    }

    public String getCorporateId() {

        return corporateId;
    }

    public void setCorporateId(String corporateId) {

        this.corporateId = corporateId;
    }

    /* fixme: implement properly!
    public int hashCode() {

        int result = workflowName.hashCode() * NumberConstants.PRIME_FOR_HASH_FUNCTION;
        if (null != activityName) {
            result += activityName.hashCode();
        }
        return result;
    }
    */
    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(AbstractDataDocument.HASH_CODE_ODD_NUMBER_START, AbstractDataDocument.HASH_CODE_ODD_NUMBER_MULTIPLIER).
                append(activityName).
                append(creationDate).
                append(finishedDate).
                append(workflowName).
                appendSuper(super.hashCode()).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        WorkflowState rhs = (WorkflowState) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj)).
                        append(activityName, rhs.activityName).
                        append(creationDate, rhs.creationDate).
                        append(finishedDate, rhs.finishedDate).
                        append(workflowName, rhs.workflowName).
                        isEquals();
    }


    /**
     * Gets activity name.
     *
     * @return the activity name
     */
    public String getActivityName() {

        return activityName;
    }

    /**
     * Sets activity name.
     *
     * @param activityName the activity name
     */
    public void setActivityName(String activityName) {

        this.activityName = activityName;
    }
}
