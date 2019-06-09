/*
 * Copyright (c) 2016. FroSo, Christian Kleinhuis (ck@froso.de)
 *
 *
 */

package com.froso.ufp.core.service;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.resourcemetadata.model.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

@Service
abstract public class AbstractResourcesService<T> {

    private final ServiceService serviceService;
    //@Autowired
    // private ServiceService serviceService;
    protected Map<String, T> resources = new HashMap<>();

    public AbstractResourcesService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostConstruct
    private void initialise() {

        serviceService.registerRessource(getName(), this);
        //   abstractResourcesService.registerRessource(typeName, this);
        //   serviceService.registerRessource(getName(), this);

    }

    abstract public String getDescription();

    abstract public String getName();

    protected void onResourceRegistered(String name, T service) {
        // optiona template method
    }

    /**
     * Is path token free boolean.
     *
     * @param name    the name
     * @param service the service
     * @return the boolean
     */
    public boolean registerRessource(String name, T service) {

        resources.put(name, service);
        onResourceRegistered(name, service);
        return false;
    }

    public T getResource(String name) {
        return resources.get(name);
    }

    /**
     * Gets ressource names.
     *
     * @return the ressource names
     */
    public Map<String, ResourceMetadata> getRessourceNames() {

        Map<String, ResourceMetadata> result = new HashMap<>();

        for (Map.Entry<String, T> entry : resources.entrySet()) {

            if (entry.getValue() instanceof IMetaDataResource) {

                result.put(entry.getKey(), ((IMetaDataResource) entry.getValue()).getMetadata());
            } else {
                result.put(entry.getKey(), new ResourceMetadata());
            }

        }

        return result;

    }

}
