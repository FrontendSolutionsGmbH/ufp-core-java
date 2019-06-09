package com.froso.ufp.core.configuration;

import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;

/**
 * Created by ck on 18.05.2016.
 */
@Configuration
public class PropertyConfigs {

    @Bean
    public static PropertyPlaceholderConfigurer getPropertyPlaceHolderConfigurer() {

        PropertyPlaceholderConfigurer propertyPlaceHolderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceHolderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        propertyPlaceHolderConfigurer.setIgnoreResourceNotFound(true);
        propertyPlaceHolderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertyPlaceHolderConfigurer;


    }


    @Bean
    public static PropertyOverrideConfigurer getPropertyOverrideConfigurer() {

        PropertyOverrideConfigurer propertyOverrideConfigurer = new PropertyOverrideConfigurer();
        propertyOverrideConfigurer.setIgnoreInvalidKeys(true);
        return propertyOverrideConfigurer;

    }
}
