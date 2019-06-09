package com.froso.ufp.modules.core.authenticate.model;

public class AuthenticationStatusImpl implements AuthenticationStatus {

    private boolean enabled;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
