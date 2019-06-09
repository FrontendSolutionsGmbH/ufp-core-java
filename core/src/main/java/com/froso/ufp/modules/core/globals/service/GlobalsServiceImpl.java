package com.froso.ufp.modules.core.globals.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.globals.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class GlobalsServiceImpl implements GlobalsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalsServiceImpl.class);
    private final IPropertyService propertyService;
    private Map<String, String> globalsMapStatic = new TreeMap<>();
    private Map<String, String> globalsMapProperties = new TreeMap<>();
    private Map<String, Object> globalsMapObjects = new TreeMap<>();
    private Map<String, IGlobalsPropertyProvider> globalProps = new TreeMap<>();

    @Autowired
    public GlobalsServiceImpl(IPropertyService propertyService) {
        LOGGER.info("Globals Service Instantiated");
        this.propertyService = propertyService;
    }

    public GlobalSettings3 getGlobals() {

        GlobalSettings3 result2 = new GlobalSettings3();
        Map<String, Object> resultmap = getAllProperties();

        result2.putAll(resultmap);

        return result2;

    }

    public GlobalSettings3 getGlobals3() {

        Map<String, Object> resultmap = getAllProperties();
        GlobalSettings3 globalSettings3 = new GlobalSettings3();
        globalSettings3.putAll(resultmap);
        return globalSettings3;

    }

    @Override
    public Map<String, Object> getAllProperties() {
        Map<String, Object> resultmap = new TreeMap<>();
        resultmap.putAll(globalsMapStatic);
        resultmap.putAll(globalsMapObjects);
        for (Map.Entry<String, String> item : globalsMapProperties.entrySet()) {

            resultmap.put(item.getKey(), propertyService.getPropertyValue(item.getValue()));
        }
        for (Map.Entry<String, IGlobalsPropertyProvider> item : globalProps.entrySet()) {

            resultmap.put(item.getKey(), item.getValue().getPropertyValue());
        }
        return resultmap;
    }

    @Override
    public void add(String key, Object object) {

        globalsMapObjects.put(key, object);
    }

    @Override
    public void add(String key, String value) {
        globalsMapStatic.put(key, value);
    }

    @Override
    public void add(String key, IGlobalsPropertyProvider object) {
        this.globalProps.put(key, object);
    }

    @Override
    public void addProperty(String key, String propertyName) {

        globalsMapProperties.put(key, propertyName);

    }

}
