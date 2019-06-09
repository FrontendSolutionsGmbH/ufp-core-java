package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.user.model.*;

import javax.validation.*;

public abstract class AbstractAuthentication<T extends IDataObject> extends AbstractDataDocumentWithCoreUserLink implements Authentication<T> {

    @Valid
    private T data;
    private CounterConsumer counters = new CounterConsumer();

    private boolean enabled = Boolean.TRUE;
    private boolean verified = Boolean.FALSE;

    public AbstractAuthentication(String name, T instance) {

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
