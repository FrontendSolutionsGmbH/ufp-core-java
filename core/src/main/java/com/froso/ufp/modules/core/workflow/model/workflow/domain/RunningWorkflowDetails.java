/*
 * Copyright (c) 2015.3.28 . FroSo, Christian Kleinhuis (ck@froso.de)
 */

package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created by ckleinhuix on 28.03.2015.
 */
public class RunningWorkflowDetails implements IDataObject {

    private Boolean running = false;
    private Boolean alive = false;
    private Double progress = 0.0D;

    private List<WorkflowLogMessage> messages = new ArrayList<>();

    private List<WorkflowStepInfo> steps = new ArrayList<>();

    public Boolean isRunning() {

        return running;
    }

    public void setRunning(final Boolean running) {

        this.running = running;
    }

    public Boolean isAlive() {

        return alive;
    }

    public void setAlive(final Boolean alive) {

        this.alive = alive;
    }

    public Double getProgress() {

        return progress;
    }

    public void setProgress(final Double progress) {

        this.progress = progress;
    }

    public List<WorkflowStepInfo> getSteps() {

        return steps;
    }

    public void setSteps(final List<WorkflowStepInfo> steps) {

        this.steps = steps;
    }

    public List<WorkflowLogMessage> getMessages() {

        return messages;
    }

    public void setMessages(final List<WorkflowLogMessage> messages) {

        this.messages = messages;
    }
}
