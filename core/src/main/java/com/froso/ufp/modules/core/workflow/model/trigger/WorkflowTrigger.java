package com.froso.ufp.modules.core.workflow.model.trigger;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;

/**
 * Created by ckleinhuix on 25.01.2016.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("WORKFLOW")
        }),
        defaultView = @ResourceViewAnnotation(visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("id")
        }))

)
public class WorkflowTrigger extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "WorkflowTrigger";

    /**
     * The Workflow definition link.
     */
    private DataDocumentLink<WorkflowDefinition2> workflowDefinitionLink = new DataDocumentLink<>();

    private Trigger trigger;

    private String typeName;

    /**
     * Instantiates a new Workflow trigger.
     */
    public WorkflowTrigger() {
        super(TYPE_NAME);
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets type name.
     *
     * @param typeName the type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets workflow definition link.
     *
     * @return the workflow definition link
     */
    public DataDocumentLink<WorkflowDefinition2> getWorkflowDefinitionLink() {
        return workflowDefinitionLink;
    }

    /**
     * Sets workflow definition link.
     *
     * @param workflowDefinitionLink the workflow definition link
     */
    public void setWorkflowDefinitionLink(DataDocumentLink<WorkflowDefinition2> workflowDefinitionLink) {
        this.workflowDefinitionLink = workflowDefinitionLink;
    }

    /**
     * Gets trigger.
     *
     * @return the trigger
     */
    public Trigger getTrigger() {
        return trigger;
    }

    /**
     * Sets trigger.
     *
     * @param trigger the trigger
     */
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

}
