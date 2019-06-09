package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import java.util.*;

/**
 * Created by ckleinhuix on 24.01.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowDefinitionStepRef {


    private String activityRefName;
    private Map<WorkflowStepConstants, String> followUps = new HashMap<>();


    /**
     * Instantiates a new Workflow definition step ref.
     */
    public WorkflowDefinitionStepRef() {

    }

    /**
     * Instantiates a new Workflow definition step ref.
     *
     * @param refName the ref name
     */
    public WorkflowDefinitionStepRef(String refName) {
        activityRefName = refName;
    }

    /**
     * Gets activity ref name.
     *
     * @return the activity ref name
     */
    public String getActivityRefName() {
        return activityRefName;
    }

    /**
     * Sets activity ref name.
     *
     * @param activityRefName the activity ref name
     */
    public void setActivityRefName(String activityRefName) {
        this.activityRefName = activityRefName;
    }

    /**
     * Gets follow ups.
     *
     * @return the follow ups
     */
    public Map<WorkflowStepConstants, String> getFollowUps() {
        return followUps;
    }

    /**
     * Sets follow ups.
     *
     * @param followUps the follow ups
     */
    public void setFollowUps(Map<WorkflowStepConstants, String> followUps) {
        this.followUps = followUps;
    }
}
