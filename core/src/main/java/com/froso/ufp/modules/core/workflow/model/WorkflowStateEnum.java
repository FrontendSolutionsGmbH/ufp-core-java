package com.froso.ufp.modules.core.workflow.model;

/**
 * Created by ckleinhuix on 14.12.13.
 *
 * defines the states a workflow can have
 */
public enum WorkflowStateEnum {
    UNDEFINED,
    NEW,
    IDLE,
    PROCESSING,
    INITIALISED,
    ACTIVITY_BEGIN,
    ACTIVITY_PROCESSING,
    ACTIVITY_END,
    ACTIVITY_ERROR,
    CANCELLED_ERROR,
    CANCELLED_UNIQUE,
    FINISHED,
    STOPPED
}
