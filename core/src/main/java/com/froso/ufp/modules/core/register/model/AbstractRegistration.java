package com.froso.ufp.modules.core.register.model;

import com.froso.ufp.modules.core.user.model.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class AbstractRegistration<T> extends AbstractDataDocumentWithCoreUserLink {
    private T data;
    private CounterConsumer counters = new CounterConsumer();
    private Boolean enabled = Boolean.TRUE;
    private Boolean verified = Boolean.FALSE;

    public AbstractRegistration(String name, T instance) {

        super(name);
        this.data = instance;
    }

    public CounterConsumer getCounters() {
        return counters;
    }

    public void setCounters(CounterConsumer counters) {
        this.counters = counters;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
