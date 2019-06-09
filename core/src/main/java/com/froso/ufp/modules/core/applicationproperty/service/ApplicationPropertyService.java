package com.froso.ufp.modules.core.applicationproperty.service;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.service.util.defaultgeneration.*;
import com.froso.ufp.modules.core.applicationproperty.configuration.*;
import com.froso.ufp.modules.core.applicationproperty.exceptions.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.support.*;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

/**
 * Created by alex on 20.11.14.
 */
@Service
public class ApplicationPropertyService extends AbstractRepositoryService2<ApplicationProperty> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPropertyService.class);
    @Autowired
    private AbstractBeanFactory beanFactory;

    @Autowired(required = false)
    private ApplicationPropertyOverrideService applicationPropertyOverrideService;

    /**
     * Constructor Simple application property service.
     */
    public ApplicationPropertyService() {

        super(ApplicationProperty.TYPE_NAME);
    }

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     * @throws PropertyException the property exception
     */
    public String getPropertyValue(String key) {

        return getPropertyInternal(key);
    }

    public String getPropertyValue(String key, String defaultValue) throws PropertyException {
        try {
            return getPropertyInternal(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Gets property value boolean.
     *
     * @param key the key
     * @return the property value boolean
     * @throws PropertyException the property exception
     */
    public Boolean getPropertyValueBoolean(String key) {

        return "true".equals(getPropertyInternal(key));
    }

    /**
     * Gets property value integer.
     *
     * @param key the key
     * @return the property value integer
     * @throws PropertyException the property exception
     */
    public Integer getPropertyValueInteger(String key) {

        return Integer.parseInt(getPropertyInternal(key));
    }

    /**
     * Find property.
     *
     * @param key the key
     * @return the simple application property
     */
    public ApplicationProperty findProperty(String key) {

        // check if the override service has an entry
        // if yes return that as applicationproperty

        ApplicationProperty result = null;
        if (applicationPropertyOverrideService != null) {
            result = applicationPropertyOverrideService.getOverridenProperty(key);
        }
        if (result == null) {

            result = findOneByKeyValue("key", "=" + key);
        }
        return result;
    }

    /**
     * Register property.
     *
     * @param name         the name
     * @param defaultValue the default value
     * @throws PropertyException the property exception
     */
//    public void registerProperty(String name, String defaultValue) throws PropertyException {
//        ApplicationProperty prop = findProperty(name.toLowerCase());
//        if (null == prop) {
//            // Property does not exist, create a new one
//            prop = new ApplicationProperty();
//            prop.setKey(name);
//            prop.setValue(defaultValue);
//            save(prop);
//
//        }
//    }

    /**
     * Replace vars string.
     *
     * @param input the input
     * @return the string
     */
    String replaceVars(String input) {
        int count = 0;
        String result = input;
        while ((count < 10) && (result.contains("${"))) {
            count++;
            result = beanFactory.resolveEmbeddedValue(result);

        }

        return result;
    }

    private String getPropertyInternal(String key) {
        // Check if key exists in database
        ApplicationProperty property = findProperty(key);
        if (property != null) {
            return replaceVars(property.getValue());
        }

        // otherwise use the beanfactory to read it from loaded application.properties
        String foundProp;

        // System Property always overrides:
        foundProp = beanFactory.resolveEmbeddedValue("${" + key.trim() + "}");
        foundProp = replaceVars(foundProp);
        // cache disabled because it just does not work that simple....    cache.put(key, foundProp);

        return foundProp;
    }

    /**
     * override save to avoid inserting properties that are set explicitly to unchangeable
     *
     * @param element the element
     * @return simple application property
     */
//    @Override
//    public void prepareSave(ApplicationProperty element) {
//
//      /*  if (PropertyServiceRepositoryImpl.getUnchangeableProperties().getProperty(element.getKey()) == null) {
//            // if not in unchangeable list forward to original save method
//            // if not in unchangeable list forward to original save metho
//
//        } else {
//            // otherwise throw error
//            throw new PropertyException("Application Property " + element.getKey() + " not found");
//        }     */
//    }
    @Override
    protected void fillDefaultObject(ApplicationProperty object) {
        // template method, sub classes may initialises their objects as they desire...
        Map<String, String> search = new HashMap<>();

        search.put("key", object.getKey());
        List<ApplicationProperty> searchResult = search(search);

        Integer highestDefaultValue = 1;
        for (ApplicationProperty media : searchResult) {

            Integer value = DefaultGenerationHelper.extractDefaultIndex(media.getKey());
            if (value > highestDefaultValue) {
                highestDefaultValue = value;
            }
        }

        object.setKey(DefaultGenerationHelper.makeDefaultIndexed(object.getKey(), highestDefaultValue + 1));

    }

    public PropertySettingResponse getProperties() {
        PropertySettingResponse entries = new PropertySettingResponse();
        //load from classpath
        try {
            ClassPathResource res1 = new ClassPathResource(PropertyConfigBase.APPLICATION_PROPERTIES);
            ClassPathResource res2 = new ClassPathResource(PropertyConfigBase.APPLICATION_PROPERTIES_V2);
            if (res2.exists()) {
                entries.put("Default", PropertiesLoaderUtils.loadProperties(res2));
            } else if (res1.exists()) {
                entries.put("Default", PropertiesLoaderUtils.loadProperties(res1));

            }
        } catch (IOException e) {
            LOGGER.warn("AdminPropertyControllerClasspathNotFound" + e.getMessage());

        }

        // load from filepath 1
        try {
            entries.put("Config", PropertiesLoaderUtils.loadProperties(new FileSystemResource(PropertyConfigBase.LINUX_PROPERTY_LOCATION)));
        } catch (IOException e) {
            LOGGER.warn("AdminPropertyControllerFileSystemLinuxNotFound" + e.getMessage());

        }
        //load from filepath if windows
        try {
            entries.put("Config", PropertiesLoaderUtils.loadProperties(new FileSystemResource(PropertyConfigBase.WINDOWS_PROPERTY_LOCATION)));
        } catch (IOException e) {
            LOGGER.warn("AdminPropertyControllerFilesystemWindowsNotFound" + e.getMessage());
        }

//        try {
//
//            entries.put("Env", PropertiesLoaderUtils.loadProperties(new ClassPathResource(PropertyConfigBase.getEnvConfing())));
//        } catch (Exception e) {
//            LOGGER.warn("AdminPropertyControllerFilesystemWindowsNotFound" + e.getMessage());
//
//
//        }

        // create currents

        if (entries.get("Default") != null) {
            Properties current = new Properties();
            for (Object entry : entries.get("Default").keySet()) {
                LOGGER.info("Getting value for " + entry.toString());
                try {
                    String stringentry = entry.toString();
                    current.put(stringentry, getPropertyValue(stringentry));
                } catch (Exception e) {
                    LOGGER.warn("Current Property retrieval error" + e.getMessage());
                }

            }
            entries.put("Current", current);
        }

        // Add field for livesystem
        List<ApplicationProperty> liveprops = findAll();
        Properties liveproperties = new Properties();
        for (ApplicationProperty property : liveprops) {
            liveproperties.setProperty(property.getKey(), property.getValue());
        }
        entries.put("DB", liveproperties);
        // entries.put("unchangeable", PropertyServiceRepositoryImpl.getUnchangeableProperties());
        return entries;
    }

    public ApplicationProperty setOrCreateSingleProperty(String key, String value) {
        ApplicationProperty property = findProperty(key);
        if (property != null) {
            property.setValue(value);
        } else {
            property = new ApplicationProperty();
            property.setValue(value);
            property.setKey(key);
        }
        return save(property);
    }

    public ApplicationProperty getSingleProperty(String key, ResponseHandler manager) {
        ApplicationProperty property = findProperty(key);
        if (property != null) {
            manager.setStatus(ResultStatusEnumCode.OK);

        } else {
            manager.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE);
        }
        return property;
    }

    public void deleteSingleProperty(String key, ResponseHandler manager) {
        ApplicationProperty property = findProperty(key);
        if (property != null) {
            delete(property);
            manager.setStatus(ResultStatusEnumCode.OK);

        } else {
            manager.setStatus(ResultStatusEnumCode.ERROR_RESOURCE_NOTAVAILABLE);
        }
    }

}
