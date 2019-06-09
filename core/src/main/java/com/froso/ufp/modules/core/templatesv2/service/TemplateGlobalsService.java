package com.froso.ufp.modules.core.templatesv2.service;

import com.froso.ufp.core.util.*;
import java.util.*;
import org.springframework.stereotype.*;

/**
 * this service serves for registering template variables provided to every template
 */
@Service
public class TemplateGlobalsService {

    public static final String TEMPLATE_VAR_RESOURCES = "Resources";

    private Map<String, Object> data = new LinkedHashMap<>();

    public void registerTemplateVariable(String area, String name, Object value, int sort) {

    }

    public void registerTemplateVariable(String area, String name, Object value) {

        if (data.get(area) == null) {
            data.put(area, new LinkedHashMap<String, Object>());
        }

        if (data.get(area) instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) data.get(area);

            map.put(name, value);

        }
    }

    private String replace(String value) {
        return value.replace(":baseUrl", RequestRemoteHostRetriever.getApiBasePath());
    }

    public Map<String, Object> getData() {
        Map<String, Object> result = processMap(this.data);
        return result;
    }


    private Map<String, Object> processMap(Map<String, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getValue() instanceof String) {
                result.put(entry.getKey(), replace(entry.getValue().toString()));

            } else if (entry.getValue() instanceof Map) {
                Map<String, Object> map2 = (Map<String, Object>) entry.getValue();

                result.put(entry.getKey(), processMap(map2));
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
