package com.froso.ufp.modules.core.worker.model;

import com.froso.ufp.modules.core.workflow.model.status.*;
import java.lang.reflect.*;

public class UFPWorkerInstanceData implements ServiceStatusReporter {

    private Object bean;
    private String name;
    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public ServiceStatus getServiceStatus() {


        if (bean instanceof ServiceStatusReporter) {
            return ((ServiceStatusReporter) bean).getServiceStatus();
        } else {
            return new ServiceStatus();
        }


    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void execute() throws InvocationTargetException, IllegalAccessException {
        method.invoke(bean, null);

    }


}
