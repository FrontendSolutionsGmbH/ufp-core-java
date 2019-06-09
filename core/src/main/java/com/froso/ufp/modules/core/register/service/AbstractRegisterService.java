package com.froso.ufp.modules.core.register.service;

import com.froso.ufp.core.domain.events.*;

import javax.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
abstract public class AbstractRegisterService extends EventPublisherImpl {

    private final RegistrationsService registrationsService;

    public AbstractRegisterService(RegistrationsService registrationsService) {
        this.registrationsService = registrationsService;
    }

    protected abstract String getAuthenticationName();

    @PostConstruct
    private void initialise() {

        registrationsService.registerRessource(getAuthenticationName(), this);

    }

}
