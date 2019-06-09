/*
 * Copyright (c) 2015. FroSo, Christian Kleinhuis (ck@froso.de)
 *
 * the request definition service is a method for modules to configure allowed "insecure" urls
 *
 */

package com.froso.ufp.core.service;

import com.froso.ufp.core.response.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.01.14 Time: 14:12
 * <p>
 * helper service for retrieving properties
 */
@Service
public class ErrorsService extends AbstractResourcesService<IResultStatusEnumCode> {

    @Autowired
    public ErrorsService(ServiceService serviceService) {
        super(serviceService);
    }

    @Override
    public String getDescription() {

        return "Registered error Codes";
    }

    @Override
    public String getName() {

        return "errors";
    }

    /**
     * Register errors.
     *
     * @param <T>  the type parameter
     * @param type the type
     */
    public <T extends Enum<T> & IResultStatusEnumCode> void registerErrors(Class<T> type) {

    }
}
