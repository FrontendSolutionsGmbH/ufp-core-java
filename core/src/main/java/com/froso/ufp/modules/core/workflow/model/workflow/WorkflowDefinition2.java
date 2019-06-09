package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * The type Workflow definition.
 *
 * @author c.Kleinhuis (ck@froso.de)                  A workflow is defined by a workflowName and a Root element. The root element is the entry point into a workflow!
 */
@Document(collection = WorkflowDefinition.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("WORKFLOW")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name")
                }))

)
public class WorkflowDefinition2 extends ClientReferenceWithName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "WorkflowDefinition2";
    private String workflowName = "default";
    // solitary flags mark that a single and one only instance of a workflow type may run at the same time
    private boolean solitary;
    private Map<String, WorkflowDefinitionStep> activities = new HashMap<String, WorkflowDefinitionStep>();

    private Map<String, WorkflowDefinitionStepRef> stepRefs = new HashMap<String, WorkflowDefinitionStepRef>();
    private Set<String> uniqueElements = new HashSet<>();
    private PropsManager props = new PropsManager();
    private Boolean threadingEnabled = false;
    private WorkflowDefinitionStep cancel;

    /**
     * Constructor Workflow definition.
     */
    public WorkflowDefinition2() {
        super(TYPE_NAME);
    }

    /**
     * Gets step refs.
     *
     * @return the step refs
     */
    public Map<String, WorkflowDefinitionStepRef> getStepRefs() {
        return stepRefs;
    }

    /**
     * Sets step refs.
     *
     * @param stepRefs the step refs
     */
    public void setStepRefs(Map<String, WorkflowDefinitionStepRef> stepRefs) {
        this.stepRefs = stepRefs;
    }

    /**
     * Gets activities.
     *
     * @return the activities
     */
    public Map<String, WorkflowDefinitionStep> getActivities() {
        return activities;
    }

    /**
     * Sets activities.
     *
     * @param activities the activities
     */
    public void setActivities(Map<String, WorkflowDefinitionStep> activities) {
        this.activities = activities;
    }

    /**
     * Gets props.
     *
     * @return the props
     */
    public PropsManager getProps() {
        return props;
    }

    /**
     * Sets props.
     *
     * @param props the props
     */
    public void setProps(PropsManager props) {
        this.props = props;
    }


    /**
     * Gets cancel.
     *
     * @return the cancel
     */
    public WorkflowDefinitionStep getCancel() {
        return cancel;
    }

    /**
     * Sets cancel.
     *
     * @param cancel the cancel
     */
    public void setCancel(WorkflowDefinitionStep cancel) {
        this.cancel = cancel;
    }


    /**
     * Gets workflow name.
     *
     * @return the workflow name
     */
    public String getWorkflowName() {

        return workflowName;

    }

    /**
     * Sets workflow name.
     *
     * @param workflowName the workflow name
     */
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    /**
     * Is solitary boolean.
     *
     * @return the boolean
     */
    public boolean isSolitary() {
        return solitary;
    }

    /**
     * Sets solitary.
     *
     * @param solitary the solitary
     */
    public void setSolitary(boolean solitary) {
        this.solitary = solitary;
    }

    /**
     * Gets unique elements.
     *
     * @return the unique elements
     */
    public Set<String> getUniqueElements() {
        return uniqueElements;
    }

    /**
     * Sets unique elements.
     *
     * @param uniqueElements the unique elements
     */
    public void setUniqueElements(Set<String> uniqueElements) {
        this.uniqueElements = uniqueElements;
    }

    /**
     * Gets threading enabled.
     *
     * @return the threading enabled
     */
    public Boolean getThreadingEnabled() {
        return threadingEnabled;
    }

    /**
     * Sets threading enabled.
     *
     * @param threadingEnabled the threading enabled
     */
    public void setThreadingEnabled(Boolean threadingEnabled) {
        this.threadingEnabled = threadingEnabled;
    }
}
