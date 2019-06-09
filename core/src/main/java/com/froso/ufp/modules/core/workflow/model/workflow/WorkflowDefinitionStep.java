package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * Created by ckleinhuix on 19.01.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowDefinitionStep {


    private String stepName;
    private TemplateRef viewTemplate = null;

    private String stepTypeClassName;
    private WorkflowStepType stepReturnType;
    private Boolean continuous = Boolean.TRUE;
    private String description;
    private PropsManager properties = new PropsManager();

    public TemplateRef getViewTemplate() {
        return viewTemplate;
    }

    public void setViewTemplate(TemplateRef viewTemplate) {
        this.viewTemplate = viewTemplate;
    }

    public Boolean getContinuous() {
        return continuous;
    }

    public void setContinuous(Boolean continuous) {
        this.continuous = continuous;
    }

    /**
     * DefaultProps.Template
     * Gets step name.
     *
     * @return the step name
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * Sets step name.
     *
     * @param stepName the step name
     */
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }


    /**
     * Gets step actions.
     *
     * @return the step actions
     */
    public String getStepTypeClassName() {
        return stepTypeClassName;
    }

    /**
     * Sets step actions.
     *
     * @param stepTypeClassName the step actions
     */
    public void setStepTypeClassName(String stepTypeClassName) {
        this.stepTypeClassName = stepTypeClassName;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets step type.
     *
     * @return the step type
     */
    public WorkflowStepType getStepReturnType() {
        return stepReturnType;
    }

    /**
     * Sets step type.
     *
     * @param stepType the step type
     */
    public void setStepReturnType(WorkflowStepType stepType) {
        this.stepReturnType = stepType;
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public PropsManager getProperties() {
        return properties;
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    public void setProperties(PropsManager properties) {
        this.properties = properties;
    }
}
