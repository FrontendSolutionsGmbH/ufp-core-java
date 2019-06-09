package com.froso.ufp.modules.core.authenticate.service;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import javax.annotation.*;
import org.apache.velocity.exception.*;
import org.springframework.beans.factory.annotation.*;

abstract public class AbstractAuthenticateService extends EventPublisherImpl implements AuthenticateService {
    public static final String PROP_NAME_CLIENT_PASSWORD_ENCODING_PREFIX = "ufp.optional.authenticate";
    @Autowired
    protected AuthenticationsService authenticationsService;
    @Autowired
    protected IPropertyService applicationPropertyService;
    @Autowired
    protected ICoreUserService userService;
    @Autowired
    protected CoreUserHelperService coreUserHelperService;

    public final String getPasswordEncoding() {
        return applicationPropertyService.getPropertyValue(PROP_NAME_CLIENT_PASSWORD_ENCODING_PREFIX + "." + this.getAuthenticationName().toLowerCase() + ".clientpasswordencoding", "sha256");
    }

    protected abstract String getAuthenticationName();

    @PostConstruct
    private void initialise() {
        authenticationsService.registerRessource(getAuthenticationName(), this);
    }

    @Override
    public TokenData getAuthenticationResult(Authentication authentication) {

        ICoreUser entity = (ICoreUser) userService.findOne(authentication.getCoreUser().getId());
        try {
            coreUserHelperService.transformUserToCryptonizedID(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundException("PROBLEM CREATING ENTITY");
        }

        TokenData result = new TokenData();
        result.setToken(entity.getId());
        return result;


    }


}
