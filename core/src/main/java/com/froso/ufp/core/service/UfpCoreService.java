package com.froso.ufp.core.service;

import com.froso.ufp.core.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UfpCoreService {

    @Autowired
        protected IPropertyService propertyService;


    public String getApplicationName(){
        return propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_NAME);
    }
    public String getApplicationVersion(){
        return propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_VERSION);
    }
    public String getApplicationBuildTime(){
        return propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_BUILDTIME);
    }
    public String getApplicationBuild(){
        return propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_BUILD);
    }
    public String getApplicationHostName(){
        return propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_HOSTNAME);
    }
    public String getApplicationVersionString(){
        return getApplicationName()+"-"+getApplicationVersion()+"-"+getApplicationBuild();
    }



}
