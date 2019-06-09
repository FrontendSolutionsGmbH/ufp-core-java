package com.froso.ufp.modules.core.workflow.model.status;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * @author alex on 22.02.14.
 */
public interface ServiceStatusReporter extends INamedObject {
    /**
     * Gets service status.
     *
     * @return the service status
     */
    ServiceStatus getServiceStatus();
}
