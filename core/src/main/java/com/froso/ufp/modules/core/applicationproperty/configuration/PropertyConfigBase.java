/**
 * the property configbase defines the locations where to search for application.properties files
 * for linux and windows...
 */
package com.froso.ufp.modules.core.applicationproperty.configuration;

import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.core.env.*;
import org.springframework.core.io.*;

/**
 * The type Property config base.
 */
@Configuration
public class PropertyConfigBase {

    /**
     * The constant LINUX_PROPERTY_LOCATION.
     */
    public static final String LINUX_PROPERTY_LOCATION = "/etc/application-ufp.properties";
    /**
     * The constant WINDOWS_PROPERTY_LOCATION.
     */
    public static final String WINDOWS_PROPERTY_LOCATION = "c:\\application-ufp.properties";
    /**
     * The constant APPLICATION_PROPERTIES.
     */
    public static final String APPLICATION_PROPERTIES = "application.ufp.dev.properties";
    public static final String APPLICATION_PROPERTIES_V2 = "application.properties";
    /**
     * The constant APPLICATION_PROPERTIES_QA.
     */
    public static final String APPLICATION_PROPERTIES_QA = "application.ufp.qa.properties";
    /**
     * The constant APPLICATION_PROPERTIES_PRODUCTION.
     */
    public static final String APPLICATION_PROPERTIES_PRODUCTION = "application.ufp.production.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConfigBase.class);
    @Autowired
    private Environment environment;

    /**
     * My property sources placeholder configurer.
     *
     * @return the property sources placeholder configurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {

        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();

        Iterable<Resource> foundResources = Iterables.filter(getResources(), new Predicate<Resource>() {
            @Override
            public boolean apply(Resource input) {

                boolean result = false;
                if (null != input) {
                    result = input.exists();
                }
                return result;
            }
        });
        p.setLocalOverride(false);
        //  p.setIgnoreResourceNotFound(true);

        // warning, this causes unfound properties to be left alone
        // warning warning warning ; changing this value will lead to unparseable values.... it is on true
        p.setIgnoreUnresolvablePlaceholders(false);

        p.setLocations(Iterables.toArray(foundResources, Resource.class));

        return p;
    }

    /**
     * Gets env confing.
     *
     * @return the env confing
     */
    public static String getEnvConfing() {

        String envConfig = System.getenv("UFP_CONF");
        if (envConfig == null) {
            envConfig = System.getProperty("UFP_CONF");
        }

        if (envConfig != null) {
            LOGGER.info("Initialising Properties UFP_CONF: " + envConfig);
            if (envConfig.contains("/")) {
                // a path seems to be configured, use that
                return envConfig;


            } else {

                // environment variable present, use prepared configs
                switch (envConfig.toLowerCase()) {
                    case "qa":
                        return APPLICATION_PROPERTIES_QA;
                    case "production":
                        return APPLICATION_PROPERTIES_PRODUCTION;
                    default:
                        return APPLICATION_PROPERTIES;
                }
            }
        }
        return APPLICATION_PROPERTIES;
    }

    private static List<Resource> getResources() {
        LOGGER.info("Initialising Properties");

        List<Resource> resources = Lists.newArrayList();

        String envConfig = System.getenv("UFP_CONF");
        if (envConfig == null) {
            envConfig = System.getProperty("UFP_CONF");
        }

        LOGGER.info("UFP_CONF-CONF VALUE IS:" + envConfig);
        ClassPathResource resource = new ClassPathResource(APPLICATION_PROPERTIES);
        resources.add(resource);

        if (envConfig != null) {
            LOGGER.info("Initialising Properties UFP_CONF: " + envConfig);
            if (envConfig.contains("/")) {
                // a path seems to be configured, use that
                resources.add(new PathResource(envConfig));


            } else {

                // environment variable present, use prepared configs
                switch (envConfig.toLowerCase()) {

                    case "qa":
                        LOGGER.info("Initialising Properties USING QA");
                        resources.add(new ClassPathResource(APPLICATION_PROPERTIES_QA));
                        break;
                    case "production":
                        LOGGER.info("Initialising Properties USING PRODUCTION");
                        resources.add(new ClassPathResource(APPLICATION_PROPERTIES_PRODUCTION));
                        break;


                    default:
                        break;

                }
            }
        }

        FileSystemResource fileResource = new FileSystemResource(LINUX_PROPERTY_LOCATION);
        resources.add(fileResource);
        FileSystemResource fileResource2 = new FileSystemResource(WINDOWS_PROPERTY_LOCATION);
        resources.add(fileResource2);
        return resources;
    }
}
