package com.froso.ufp.core.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.01.14 Time: 14:12
 * <p>
 * helper service for retrieving properties
 */
@Service
public class ServerService {

    /**
     * The constant PROP_NAME_APPLICATION_SERVER_API.
     */
    public static final String PROP_NAME_APPLICATION_SERVER_API = "application.server.api";
    /**
     * The constant CORS_SETTING_USER.
     */
    public static final String CORS_SETTING_USER = "ufp.core.security.cors.user";

    @Autowired
    protected IPropertyService propertyService;

    /**
     * Gets api url.
     *
     * @return the api url
     */
    public String getApiUrl() {
        if (propertyService == null) {
            return null;
        } else {
            return propertyService.getPropertyValue(PROP_NAME_APPLICATION_SERVER_API);
        }
    }


    /**
     * Gets server settings.
     *
     * @return the server settings
     */
    public Map<String, String> getServerSettings() {
        Map<String, String> result = new HashMap<>();
        result.put("api", getApiUrl());
        return result;
    }

}
