package com.froso.ufp.modules.core.workflow.model.workflow;

/**
 * @author c.Kleinhuis (ck@froso.de)
 *
 *         Used to signal any errors that may occur when a workflow is processed.
 */


public class WorkflowException extends Exception {

    /**
     * Constructor Workflow exception.
     *
     * @param message the message
     */
    // Constructor with message only!
    public WorkflowException(String message) {

        super(message);
    }

    /**
     * Constructor Workflow exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    // Constructor with message and originating cause
    public WorkflowException(String message,
                             Throwable cause) {

        super(message, cause);
    }
}
