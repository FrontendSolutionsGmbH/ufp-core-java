/*
 * Copyright (c) 2015.3.28 . FroSo, Christian Kleinhuis (ck@froso.de)
 */

package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.core.util.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 28.03.2015.
 *
 * collects data belonging to a particular step in a workflow
 */
public class WorkflowStepInfo {

    private Boolean running;
    private DateTime startDate;
    private DateTime endDate;
    private Double progress = 1.0D;
    private String errorMessage;
    private String name;

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {

        this.errorMessage = errorMessage;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getStartDate() {

        return startDate;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setStartDate(final DateTime startDate) {

        this.startDate = startDate;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getEndDate() {

        return endDate;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setEndDate(final DateTime endDate) {

        this.endDate = endDate;
    }

    public Boolean isRunning() {

        return running;
    }

    public void setRunning(final Boolean running) {

        this.running = running;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public Long getExpectedDurationSeconds() {

        Double percentage = getProgress();
        if (percentage < NumberUtil.EPSILON) {
            percentage = 1.0D;
        }
        return Math.round((1.0D / percentage) * getDurationSeconds());
    }

    public Double getProgress() {

        return progress;
    }

    public void setProgress(final Double progress) {

        this.progress = progress;
    }

    public int getDurationSeconds() {

        DateTime date1 = startDate;
        DateTime date2;
        if (endDate == null) {
            date2 = DateTime.now();
        } else {
            date2 = endDate;
        }
        int seconds = Seconds.secondsBetween(date1, date2).getSeconds();
        return seconds;
    }
}
