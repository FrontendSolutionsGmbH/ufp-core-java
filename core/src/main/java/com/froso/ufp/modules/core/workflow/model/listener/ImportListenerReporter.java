/*
 * Copyright (c) 2015.3.28 . FroSo, Christian Kleinhuis (ck@froso.de)
 */

package com.froso.ufp.modules.core.workflow.model.listener;

import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;

/**
 * Created by ckleinhuix on 23.03.2015.
 */
public interface ImportListenerReporter {

    /**
     * Gets report.
     *
     * @return the report
     */
    List<WorkflowLogMessage> getReport();

    /**
     * shall return a normalized progress, 0..1 1=finished
     *
     * @return progress
     */
    Double getProgress();
}
