package com.froso.ufp.core.domain;

import com.froso.ufp.core.domain.interfaces.*;

import java.util.*;

public class ServiceResult implements IDataObject {

    private static final long serialVersionUID = -4329181531569300513L;
    Map<String, Map<String, Object>> services = new HashMap<>();

    public Map<String, Map<String, Object>> getServices() {
        return services;
    }

    public void setServices(Map<String, Map<String, Object>> services) {
        this.services = services;
    }
}
