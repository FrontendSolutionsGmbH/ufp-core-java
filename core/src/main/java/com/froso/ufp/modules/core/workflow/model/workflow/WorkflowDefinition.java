package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
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
public class WorkflowDefinition extends AbstractDataDocumentWithName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "WorkflowDefinition";
    private String workflowName = "default";
    // solitary flags mark that a single and one only instance of a workflow type may run at the same time
    private boolean solitary;
    private WorkflowElement root;
    private Set<String> uniqueElements = new HashSet<>();
    private WorkflowElement cancel;

    private Integer straightStepCount = 0;
    private Boolean threadingEnabled = false;

    /**
     * Constructor Workflow definition.
     */
    public WorkflowDefinition() {
        super(TYPE_NAME);
    }

    /**
     * Instantiates a new Workflow definition.
     *
     * @param name the name
     */
    public WorkflowDefinition(String name) {
        super(TYPE_NAME);
        workflowName = name;
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

    /**
     * Gets straight step count.
     *
     * @return the straight step count
     */
    public Integer getStraightStepCount() {

        return straightStepCount;
    }

    /**
     * Sets straight step count.
     *
     * @param straightStepCount the straight step count
     */
    public void setStraightStepCount(final Integer straightStepCount) {

        this.straightStepCount = straightStepCount;
    }

    /**
     * Is solitary.
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
    public void setSolitary(final boolean solitary) {

        this.solitary = solitary;
    }

    /**
     * Gets workflowName.
     *
     * @return the workflowName
     */
    public String getName() {

        return workflowName;
    }

    /**
     * Sets workflowName.
     *
     * @param workflowName the workflowName
     */
    public void setWorkflowName(String workflowName) {

        this.workflowName = workflowName;
    }

    /**
     * Gets root.
     *
     * @return the root
     */
    public WorkflowElement getRoot() {

        return root;
    }

    /**
     * Sets root.
     *
     * @param root the root
     * @throws WorkflowException the workflow exception
     */
    public void setRoot(WorkflowElement root) throws WorkflowException {
        if (root == null) {
            throw new WorkflowException("Workflow: Start may not be null");
        }
        this.root = root;
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
     * Gets cancel.
     *
     * @return the cancel
     */
    public WorkflowElement getCancel() {

        return cancel;
    }

    /**
     * Sets cancel.
     *
     * @param cancel the cancel
     */
    public void setCancel(WorkflowElement cancel) {

        this.cancel = cancel;
    }
}
