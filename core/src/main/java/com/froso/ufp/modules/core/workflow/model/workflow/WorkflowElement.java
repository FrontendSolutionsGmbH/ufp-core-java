package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 11.01.2016.
 *
 * @param <C> the type parameter
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@workflowElementId")

public interface WorkflowElement<C> {
    /**
     * Is last element boolean.
     *
     * @return the boolean
     */
    boolean isLastElement();

    /**
     * Check is idle boolean.
     *
     * @param c the c
     * @return the boolean
     */
    boolean checkIsIdle(C c);

    /**
     * Sets is last element.
     *
     * @param isLastElement the is last element
     */
    void setIsLastElement(boolean isLastElement);

    /**
     * Sets is continuous.
     *
     * @param isContinuous the is continuous
     */
    void setIsContinuous(boolean isContinuous);

    /**
     * Gets start date.
     *
     * @return the start date
     */
    DateTime getStartDate();

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    void setStartDate(DateTime startDate);

    /**
     * Gets finished date.
     *
     * @return the finished date
     */
    DateTime getFinishedDate();

    /**
     * Sets finished date.
     *
     * @param finishedDate the finished date
     */
    void setFinishedDate(DateTime finishedDate);

    /**
     * Performed boolean.
     *
     * @param context the context
     * @return the boolean
     */
    boolean performed(C context);

    /**
     * Is final boolean.
     *
     * @return the boolean
     */
    boolean isFinal();

    /**
     * Sets is final.
     *
     * @param islastelement the islastelement
     */
    void setIsFinal(boolean islastelement);

    /**
     * Is valid boolean.
     *
     * @param c the c
     * @return the boolean
     */
    boolean isValid(C c);

    /**
     * Find workflow element by name workflow element.
     *
     * @param searchName the search name
     * @return the workflow element
     */
    WorkflowElement<C> findWorkflowElementByName(String searchName);

    /**
     * Add child element.
     *
     * @param name    the name
     * @param element the element
     */
    @JsonManagedReference
    void addChildElement(String name, WorkflowElement element);

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets name.
     *
     * @param name the name
     */
    void setName(String name);

    /**
     * Process workflow element.
     *
     * @param context the context
     * @return workflow element
     * @throws WorkflowException the workflow exception
     */
    WorkflowElement process(C context) throws WorkflowException;

    /**
     * Gets child elements.
     *
     * @return the child elements
     */
    Map<String, WorkflowElement<C>> getChildElements();

    /**
     * Sets child elements.
     *
     * @param childElements the child elements
     */
    @JsonManagedReference
    void setChildElements(Map<String, WorkflowElement<C>> childElements);

    /**
     * Is continuous boolean.
     *
     * @return the boolean
     */
    boolean isContinuous();

    /**
     * Gets output.
     *
     * @param context the context
     * @return the output
     */
    Map<String, Object> getOutput(C context);

    /**
     * Process input.
     *
     * @param context  the context
     * @param theInput the the input
     */
    void processInput(C context, Map<String, String> theInput);

    /**
     * Gets state name on error.
     *
     * @return the state name on error
     */
    String getStateNameOnError();

    /**
     * Sets state name on error.
     *
     * @param stateNameOnError the state name on error
     */
    void setStateNameOnError(String stateNameOnError);
}
