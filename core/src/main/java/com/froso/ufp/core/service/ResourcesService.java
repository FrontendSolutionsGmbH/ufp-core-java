/*
 * Copyright (c) 2015. FroSo, Christian Kleinhuis (ck@froso.de)
 *
 * the request definition service is a method for modules to configure allowed "insecure" urls
 *
 */

package com.froso.ufp.core.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.service.util.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.resourcemetadata.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * The resources service contains infos about registered ufp-ressources, it resolves the foreign 1:n and n:1 keys
 * naming them foreignKeys and foreignKeysIncoming with resources pointing to this one, it does this by searching
 * for DataDocumentLink instances in the given pojos
 */
@Service
public class ResourcesService extends AbstractResourcesService<AbstractRepositoryService2> {

    /**
     * constructor based autowiring
     */
    @Autowired
    public ResourcesService(ServiceService serviceService) {
        super(serviceService);
    }

    /**
     * old utility mode for grouping counts ...
     * @return
     */
    @Deprecated
    public static final ResourceView getCountView() {

        ResourceView result = new ResourceView();
        result.setName("COUNT_CREATE_PER_DAY");
        result.setViewType(ViewType.GROUP);
        result.getFilter().put("groupingKeyFunction", "function(doc){" +
                "var date=new Date(doc.metaData.creationTimestamp);" +
                "return {id:date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()}}");

        result.getFilter().put("initialValue", "{ count: 0 }");
        result.getFilter().put("finalizeFunction", "function(doc){return doc;}");
        result.getFilter().put("reduceFunction", "function(doc, prev) { prev.count += 1 }");
        return result;

    }

    /**
     * utility method to resolve a link to a concrete dataobject, dont use :)
     * @param link
     * @return
     */
    @Deprecated
    public IDataDocument resolveLink(IDataDocumentLink link) {

        IDataDocument result = null;
        AbstractRepositoryService2 service = getResourceServiceByResourceName(link.getResourceName());

        if (service != null) {
            result = service.findOneBrute(link.getId());
        }

        return result;
    }

    /**
     * retrieves the resource service by name returning basically the repository
     * @param name
     * @return
     */
    public AbstractRepositoryService2 getResourceServiceByResourceName(String name) {

        AbstractRepositoryService2 result = null;
        for (Map.Entry<String, AbstractRepositoryService2> entry : resources.entrySet()) {
            if (entry.getValue().getTypeName().equals(name)) {
                result = entry.getValue();
                break;
            }

        }
        return result;
    }

    /**
     * resolve a service by7 the resource class name
     * @param clazz
     * @return
     */
    public AbstractRepositoryService2 getResourceServiceByClassName(Class clazz) {

        AbstractRepositoryService2 result = null;
        for (Map.Entry<String, AbstractRepositoryService2> entry : resources.entrySet()) {
            if (entry.getValue().getClassOfRepository().equals(clazz)) {
                result = entry.getValue();
                break;
            }

        }
        return result;

    }

    @Override
    public String getDescription() {
        return "Lists all available CRUD Repositories";
    }

    @Override
    public String getName() {
        return "crud";
    }

    private boolean nameIsInResource(ResourceMetadata metadata, String name) {

        if (metadata.getResourceName().equals(name)) {
            return true;
        }
        for (String interfaceName : metadata.getInterfaces()) {
            if (name.equals(interfaceName)) {
                return true;
            }
        }
        return false;

    }

    public String getResourceNameForInterface(String interfaceName) {
        Map<String, ResourceMetadata> resources = getRessourceNames();

        for (Map.Entry<String, ResourceMetadata> entryInner : resources.entrySet()) {
            for (String interfaceCurrent : entryInner.getValue().getInterfaces()) {

                if (interfaceCurrent.equals(interfaceName)) {

                    return entryInner.getKey();

                }

            }
        }

        return null;

    }

    @Override
    public Map<String, ResourceMetadata> getRessourceNames() {
        Map<String, ResourceMetadata> allResources = super.getRessourceNames();

        // iterate over all resources and set incoming foreign keys for every entry
        for (Map.Entry<String, ResourceMetadata> currentResource : allResources.entrySet()) {
            if (currentResource.getValue() instanceof ResourceMetadata) {
                // cast 1
                ResourceMetadata resourceMetadata = currentResource.getValue();
                resourceMetadata.getForeignKeysIncoming().clear();

                // search pointing foreign keys to current resource
                for (Map.Entry<String, ResourceMetadata> entryInner : allResources.entrySet()) {
                    if (entryInner.getValue() instanceof ResourceMetadata) {
                        // cast 2
                        ResourceMetadata innerResourceMetadata = entryInner.getValue();
                        if (!innerResourceMetadata.getResourceName().equals(resourceMetadata.getResourceName())) {
                            for (ForeignKey foreignKey : innerResourceMetadata.getForeignKeys()) {
                                // if (foreignKey.getResourceName().equals(resourceMetadata.getResourceName())) {
                                if (nameIsInResource(resourceMetadata, foreignKey.getResourceName())) {
                                    // create new foreign key this time pointing to the resource instead away
                                    ForeignKey foreignKeyRevers = new ForeignKey();
                                    foreignKeyRevers.setResourceName(innerResourceMetadata.getResourceName());
                                    foreignKeyRevers.setFieldName(foreignKey.getFieldName());
                                    resourceMetadata.getForeignKeysIncoming().add(foreignKeyRevers);

                                }
                            }
                        }
                    }
                }
            }

        }
        return allResources;
    }

}
