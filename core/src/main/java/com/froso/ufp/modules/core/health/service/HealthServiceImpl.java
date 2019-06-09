package com.froso.ufp.modules.core.health.service;

import com.froso.ufp.modules.core.health.interfaces.*;
import com.froso.ufp.modules.core.health.model.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Service
public class HealthServiceImpl implements HealthService {
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class);
    private Map<String, HealthProvider> healthProviders = new HashMap<String, HealthProvider>();


    public void registerhealthCheckCallback(String name, HealthProvider provider) {

        healthProviders.put(name, provider);
    }

    public HealthStatusMap getHealthStatusMap() {
        HealthStatusMap result = new HealthStatusMap();

        for (Map.Entry<String, HealthProvider> entry : healthProviders.entrySet()) {
            LOGGER.info(entry.getKey() + "/" + entry.getValue());
        }

        return result;

    }

}
