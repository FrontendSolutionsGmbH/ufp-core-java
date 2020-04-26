package com.froso.ufp.modules.core.applicationproperty.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import org.joda.time.*;

import java.util.*;

/**
 * Created by alex on 20.11.14.
 */
// @Service

/**
 * disabled through autoscannning, not yet tested in real live environments
 */
public class ApplicationPropertyOverrideService extends AbstractRepositoryService2<ApplicationPropertyOverride> {

    /**
     * Constructor Simple application property service.
     */
    public ApplicationPropertyOverrideService() {

        super(ApplicationPropertyOverride.TYPE_NAME);
    }

    ApplicationProperty getOverridenProperty(String key) {

        LocalTime now = LocalTime.now();
        List<ApplicationPropertyOverride> overrideEntries = findByKeyValue("key", "=" + key);
        for (ApplicationPropertyOverride applicationPropertyOverride : overrideEntries) {

            if (applicationPropertyOverride.getStart().isBefore(now) && applicationPropertyOverride.getEnd().isAfter(now)) {
                ApplicationProperty result = new ApplicationProperty();
                result.setKey(key);
                result.setValue(applicationPropertyOverride.getValue());
            }

        }
        return null;
    }

}
