package com.froso.ufp.modules.optional.authenticate.ldap.service;

import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class LdapRegisterService extends AbstractRegisterService {

    @Autowired
    public LdapRegisterService(RegistrationsService registrationsService) {
        super(registrationsService);
    }

    protected String getAuthenticationName() {
        return LdapConstants.NAME;
    }
}
