package com.froso.ufp.modules.core.workflow.model.workflow;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import java.util.*;
import org.joda.time.*;

/**
 * The type Abstract workflow element.
 *
 * @param <C> the type parameter
 * @author c.Kleinhuis (ck@froso.de)         <p>         Many WorkflowElements make up a workflow. A WorkflowElement contains activities to perform actual         activities.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@refId")
public abstract class AbstractWorkflowElement<C> implements WorkflowElement<C> {

    /**
     * The constant WORKFLOW_ID.
     */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    private boolean lastElement;
    private String name;

    @JsonManagedReference
    private Map<String, WorkflowElement<C>> childElements;
    private boolean continuous;

    private DateTime startDate = null;
    private DateTime finishedDate = null;
    private String stateNameOnError;

    /**
     * Constructor
     *
     * @param name a name for the workflow
     */
    protected AbstractWorkflowElement(String name) {

        this.name = name;
        childElements = new HashMap<>();
        continuous = true;
    }


    /**
     * Do set activity.
     *
     * @param activity the activity
     * @throws WorkflowException the workflow exception
     */
    protected void doSetActivity(WorkflowActivity activity) throws WorkflowException {
        // template method to set elements activity
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     * @throws WorkflowException the workflow exception
     */
    public final void setActivity(WorkflowActivity activity) throws WorkflowException {

        doSetActivity(activity);
    }

    /**
     * Check is valid.
     *
     * @param c the c
     * @return the boolean
     */
// Template Method to be overriden by subclasses
    protected abstract boolean checkIsValid(C c);

    /**
     * Check is idle.
     *
     * @param c the c
     * @return the boolean
     */
// Template Method to be overriden by subclasses
    @Override
    public boolean checkIsIdle(C c) {
        // Default return idle=false ;)
        return false;
    }

    @Override
    public boolean isLastElement() {

        return lastElement;
    }

    @Override
    public void setIsLastElement(final boolean isLastElement) {

        this.lastElement = isLastElement;
    }

    @Override
    public void setIsContinuous(final boolean isContinuous) {

        this.continuous = isContinuous;
    }

    @Override
    public DateTime getStartDate() {

        return startDate;
    }

    @Override
    public void setStartDate(final DateTime startDate) {

        this.startDate = startDate;
    }

    @Override
    public DateTime getFinishedDate() {

        return finishedDate;
    }

    @Override
    public void setFinishedDate(final DateTime finishedDate) {

        this.finishedDate = finishedDate;
    }

    /**
     * used to indicate that the workflow step actually performed some actions, to not overfill the workflow history
     * with non relevant logs
     *
     * @return
     */
    @Override
    public boolean performed(C context) {
        return true;
    }

    /**
     * Is final.
     *
     * @return the boolean
     */
    @Override
    public boolean isFinal() {

        return lastElement;
    }

    /**
     * Sets is final.
     *
     * @param islastelement the islastelement
     */
    @Override
    public void setIsFinal(boolean islastelement) {

        lastElement = islastelement;
    }

    /**
     * Is valid.
     *
     * @param c the c
     * @return the boolean
     */
    @Override
    public boolean isValid(C c) {

        return doCheckValid(c, new ArrayList<WorkflowElement>());
    }

    /**
     * Do check valid boolean.
     *
     * @param c         the c
     * @param processed the processed
     * @return the boolean
     */
    boolean doCheckValid(C c, List<WorkflowElement> processed) {

        boolean result = true;
        // Check that we do not land in an endless loop
        if (!processed.contains(this)) {
            // Add self to the processed list to avoid endless looping
            processed.add(this);
            // Check if this element is valid
            if (!checkIsValid(c)) {
                result = false;
            } else {
                // if is valid iterate over children
                for (Map.Entry<String, WorkflowElement<C>> element : childElements.entrySet()) {
                    if ((null != element) && (null != element.getValue())) {
                        if (!((AbstractWorkflowElement) element.getValue()).doCheckValid(c, processed)) {
                            result = false;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * helper method to find an element by name, searched throughout the children.
     *
     * @param searchName the search name
     * @return workflow element
     */
    @Override
    public final WorkflowElement<C> findWorkflowElementByName(String searchName) {

        return findWorkflowElementByName(searchName, new HashSet<String>());
    }

    /**
     * @param searchName
     * @param visited    the visited array is needed to prohibit endless loops due to looped workflow steps!
     * @return
     */
    private WorkflowElement<C> findWorkflowElementByName(String searchName, Set<String> visited) {

        WorkflowElement<C> result = null;
        // Check if we already visited this node
        if (visited.contains(this.name)) {
            // already visited this node, return null
        } else {
            // Insert to list of visited
            visited.add(this.name);
            if (this.name.equals(searchName)) {
                result = this;
            } else {
                result = findWorkflowElementInChildren(searchName, visited);
            }
        }
        return result;
    }

    /**
     * Find workflow element in children.
     *
     * @param name    the name
     * @param visited the visited
     * @return workflow element
     */
    final WorkflowElement<C> findWorkflowElementInChildren(String name, Set<String> visited) {

        WorkflowElement<C> result = null;
        for (Map.Entry<String, WorkflowElement<C>> entry : childElements.entrySet()) {
            // Make a recursive call if the value is not a null value ;)
            if (null != entry.getValue()) {
                result = ((AbstractWorkflowElement) entry.getValue()).findWorkflowElementByName(name, visited);
                if (null != result) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * method for setting a child element, it is public due to wiring reasons to achieve setting an element that would
     * not exist during creation!
     *
     * @param name    the name
     * @param element the element
     */

    @Override
    @JsonManagedReference
    public final void addChildElement(String name, WorkflowElement element) {

        childElements.put(name, element);
    }


    /**
     * Gets child element.
     *
     * @param name the name
     * @return child element
     */
    protected final WorkflowElement getChildElement(String name) {

        return childElements.get(name);
    }

    /**
     * Gets name.
     *
     * @return name name
     */
    @Override
    public final String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    @Override
    public final void setName(String name) {

        this.name = name;
    }

    /**
     * Gets child elements.
     *
     * @return child elements
     */
    @Override
    @JsonManagedReference

    public final Map<String, WorkflowElement<C>> getChildElements() {

        return childElements;
    }

    /**
     * Sets child elements.
     *
     * @param childElements the child elements
     */
    @Override

    public void setChildElements(Map<String, WorkflowElement<C>> childElements) {

        this.childElements = childElements;
    }

    /**
     * Is continuous.
     *
     * @return boolean boolean
     */
    @Override
    public final boolean isContinuous() {

        return continuous;
    }

    /**
     * Sets continuous.
     *
     * @param continuous the continuous
     */
    protected final void setContinuous(boolean continuous) {

        this.continuous = continuous;
    }

    /**
     * input and getOutput methods are used to pass data into a workflow and out of a workflow the general use of this
     * methods is that the processor engine has something to return when an element is non-continuos, and for
     * continuation of the workflow the input method is available
     * <p>
     * these methods are defined as base methods in the workflow element, because it is of general use, which would
     * over-complicate to distinguish between elements that receive/getOutput data and elements that just continue, in
     * those cases the input and getOutput methods are returning nothing and accept nothing ...
     *
     * @param context the context
     * @return output output
     */
    @Override
    public final Map<String, Object> getOutput(C context) {
        // default is returning a null object
        return doOutput(context);
    }

    /**
     * Do output.
     *
     * @param context the context
     * @return map map
     */
    protected abstract Map<String, Object> doOutput(C context);

    /**
     * Process input.
     *
     * @param context  the context
     * @param theInput the the input
     */
    @Override
    public void processInput(C context, Map<String, String> theInput) {
        // handle the input by passing it to the subclasses,
        doProcessInput(context, theInput);
    }

    /**
     * Do process input.
     *
     * @param context  the context
     * @param theInput the the input
     */
    protected abstract void doProcessInput(C context, Map<String, String> theInput);

    /**
     * Gets state name on error.
     *
     * @return the state name on error
     */
    @Override
    public String getStateNameOnError() {

        return stateNameOnError;
    }

    /**
     * Sets state name on error.
     *
     * @param stateNameOnError the state name on error
     */
    @Override
    public void setStateNameOnError(String stateNameOnError) {

        this.stateNameOnError = stateNameOnError;
    }
}
