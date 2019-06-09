package com.froso.ufp.modules.core.applicationproperty.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.modules.core.applicationproperty.exceptions.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.support.*;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.*;
import org.springframework.stereotype.*;

@Service
public class PropertyServiceRepositoryImpl implements IPropertyService {

    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyServiceRepositoryImpl.class);
    private static final Object locker = new Object();
    // Pseudo Singleton, instance is set during construction....
    private static PropertyServiceRepositoryImpl instance;
    private final AbstractBeanFactory beanFactory;
    private Map<String, String> registeredProperties = new TreeMap<>();
    @Autowired
    private ApplicationPropertyService applicationPropertyService;

    @Autowired
    public PropertyServiceRepositoryImpl(AbstractBeanFactory beanFactory) {

        this.beanFactory = beanFactory;

        initUnchangeableProperties();

        synchronized (locker) {
            instance = this;
        }
    }

    public static IPropertyService getInstance() {

        return instance;
    }

    public static IPropertyService construct(AbstractBeanFactory beanFactory) {

        if (null == instance) {
            instance = new PropertyServiceRepositoryImpl(beanFactory);
        }
        return instance;
    }
    public static Integer getPropertyInteger(String key) throws PropertyException {

        return Integer.parseInt(getProperty(key));
    }

    @Deprecated
    public static String getProperty(String key) throws PropertyException {

        if (null == instance) {
            throw new PropertyException("Property Accessor called before initialised");
        }
        return instance.getPropertyInternal(key);
    }

    public static Boolean getPropertyBoolean(String key) throws PropertyException {

        return "true".equals(getProperty(key));
    }

    public void registerProperty(String key, String defaultValue) throws PropertyException {
    }

    public final void initUnchangeableProperties() {

        Properties unchangeableProps = new Properties();
        unchangeableProps.setProperty(MongoConfig.PROP_NAME_UFP_CORE_MONGO_DATABASE, "uneditable");
        unchangeableProps.setProperty(MongoConfig.PROP_NAME_UFP_CORE_MONGO_HOST, "uneditable");
        unchangeableProps.setProperty(MongoConfig.PROP_NAME_UFP_CORE_MONGO_PASSWORD, "uneditable");
        unchangeableProps.setProperty(MongoConfig.PROP_NAME_UFP_CORE_MONGO_PORT, "uneditable");
        unchangeableProps.setProperty(MongoConfig.PROP_NAME_UFP_CORE_MONGO_USERNAME, "uneditable");
      /*   unchangeableProps.setProperty("log4j.appender.stdout", "uneditable");
        unchangeableProps.setProperty("log4j.appender.stdout.Target", "uneditable");
        unchangeableProps.setProperty("log4j.appender.stdout.layout", "uneditable");
        unchangeableProps.setProperty("log4j.appender.stdout.layout.ConversionPattern", "uneditable");
        unchangeableProps.setProperty("log4j.rootLogger", "uneditable");
         unchangeableProps.setProperty("ufp.modules.ftp.default.host", "uneditable");
        unchangeableProps.setProperty("ufp.modules.ftp.default.port", "uneditable");
        unchangeableProps.setProperty("ufp.modules.ftp.default.username", "uneditable");
        unchangeableProps.setProperty("ufp.modules.ftp.default.password", "uneditable");
        unchangeableProps.setProperty("ufp.modules.ftp.default..path", "uneditable");

        unchangeableProps.setProperty("ftp.private.host", "uneditable");
        unchangeableProps.setProperty("ftp.private.port", "uneditable");
        unchangeableProps.setProperty("ftp.private.username", "uneditable");
        unchangeableProps.setProperty("ftp.private.password", "uneditable");
        unchangeableProps.setProperty("ftp.private.path", "uneditable");
        */

    }

    public void registerProperty(String name, String defaultValue, Boolean editable) {
        registeredProperties.put(name, defaultValue);

    }


    @Override
    public String getPropertyValue(String key, String defaultValue) throws PropertyException {

        String result = getPropertyValue(key);
        if (result != null) {
            return result;
        }
        return defaultValue;

    }

    @Override
    public String getPropertyValue(String key) throws PropertyException {

        return getPropertyInternal(key);
    }

    @Override
    public Boolean getPropertyValueBoolean(String key) throws PropertyException {

        return "true".equals(getPropertyInternal(key));
    }

    @Override
    public Integer getPropertyValueInteger(String key) throws PropertyException {

        Integer result = 0;
        try {
            result = Integer.parseInt(getPropertyInternal(key));
        } catch (Exception e) {
            LOGGER.error("int property parse problem ", e);
        }
        return result;
    }

    private boolean isPossibleSPELExpression(String expression) {

        if (expression == null) {
            return false;
        }
        if (expression.contains("+")) {
            return true;
        }
        if (expression.contains("*")) {
            return true;
        }
        if (expression.contains("/")) {
            return true;
        }
        if (expression.contains("{")) {
            return true;
        }

        if (expression.contains("}")) {
            return true;
        }
        if (expression.contains("#")) {
            return true;
        }
        if (expression.contains("'")) {
            return true;
        }
        return expression.contains("$");

    }

    @Override
    public Integer getPropertyValueInteger(String key, Integer defaultValue) throws PropertyException {

        Integer result = defaultValue;
        try {
            result = Integer.parseInt(getPropertyInternal(key));
        } catch (Exception e) {
            LOGGER.error("int property parse problem ", e);
        }
        return result;
    }


    private String getPropertyInternal(String key) {
        // Check if key exists in database
        ApplicationProperty property = applicationPropertyService.findProperty(key);
        if (property != null) {
            return applicationPropertyService.replaceVars(property.getValue());
        }

        // otherwise use the beanfactory to read it from loaded application.properties
        String foundProp = null;
        try {
            foundProp = beanFactory.resolveEmbeddedValue("${" + key.trim() + "}");
            foundProp = applicationPropertyService.replaceVars(foundProp);
            // cache disabled because it just does not work that simple....    cache.put(key, foundProp);
        } catch (Exception ex) {
            // ok - property was not found
            LOGGER.warn("Problem Retrieving Property1:" + ex.getMessage());
        }

        if (null == foundProp) {
            return registeredProperties.get(key);
        }

        try {
            // pass ith throu the SPEL parser
            if (isPossibleSPELExpression(foundProp)) {
                ExpressionParser parser = new SpelExpressionParser();
                Expression exp = parser.parseExpression(foundProp);
                foundProp = exp.getValue().toString();
            }
        } catch (ParseException ex) {

        } catch (Exception ex) {
// warn: fixme: todo: what to do with a string that is not a spel expression? how can we detect before trying to parse it >
            LOGGER.warn("Problem Retrieving Property1: key:   " + key);
            LOGGER.warn("Problem Retrieving Property2: value: " + foundProp);
            LOGGER.warn("Problem Retrieving Property3: value Parsing additional SPEL expression yields " + ex.getMessage());

//            LOGGER.warn("Problem Retrieving Property:" + ex.getMessage());
        }
        // Create Property in db
        /*
        SimpleApplicationProperty property1 = new SimpleApplicationProperty();
        property1.setKey(key);
        property1.setValue(foundProp);
           simpleApplicationPropertyService.save(property1);
        */
        return foundProp;
    }
}
