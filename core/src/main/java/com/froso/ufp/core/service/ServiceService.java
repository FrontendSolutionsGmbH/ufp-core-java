/*
 * Copyright (c) 2016. FroSo, Christian Kleinhuis (ck@froso.de)
 *
 *
 */

package com.froso.ufp.core.service;

import com.froso.ufp.core.domain.*;
import java.util.*;
import org.springframework.stereotype.*;

@Service
public class ServiceService {


    private Map<String, AbstractResourcesService> services = new HashMap<>();


    /**
     * Is path token free boolean.
     *
     * @param name    the name
     * @param service the service
     * @return the boolean
     */
    public void registerRessource(String name, AbstractResourcesService service) {

        services.put(name, service);

    }


    /**
     * Gets ressource names.
     *
     * @return the ressource names
     */
    public ServiceResult getServices() {
        ServiceResult result = new ServiceResult();
        for (Map.Entry<String, AbstractResourcesService> entry : services.entrySet()) {
            String key = entry.getKey();
            AbstractResourcesService value = entry.getValue();

            if (result.getServices().get(key) == null) {
                result.getServices().put(key, value.getRessourceNames());
            } else {
                throw new RuntimeException("Duplicate Resource name entry fix please!");
            }
        }


        return result;


    }
}
