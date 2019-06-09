package com.froso.ufp.modules.core.register.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.core.resourcemetadata.model.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RegistrationsService extends AbstractResourcesService<AbstractRegisterService> {

    private final ICoreUserService coreUserService;
    private final CoreUserHelperService coreUserHelperService;
    private final GlobalsService globalsService;

    @Autowired
    public RegistrationsService(ICoreUserService coreUserService, CoreUserHelperService coreUserHelperService, GlobalsService globalsService, ServiceService serviceService) {

        super(serviceService);
        this.coreUserService = coreUserService;
        this.coreUserHelperService = coreUserHelperService;
        this.globalsService = globalsService;
    }

    @Override
    public String getDescription() {
        return "lists enabled authentication methods";
    }

    @Override
    public String getName() {

        return "registrations";
    }

    @Override
    protected void onResourceRegistered(String name, AbstractRegisterService service) {
        Map<String, ResourceMetadata> resources = this.getRessourceNames();
        Map<String, RegistrationMetadata> registerTypes = new HashMap<>();

        for (Map.Entry entry : resources.entrySet()) {

            RegistrationMetadata metadata = new RegistrationMetadata();
            registerTypes.put(entry.getKey().toString(), metadata);
        }
        globalsService.add("reg", registerTypes);
    }

    /**
     * Finalize authorization token data. returns the associated t=core users token
     *
     * @param element the element
     * @return the token data
     * @throws Exception the exception
     */
    public TokenData finalizeAuthorization(IDataDocumentWithCoreUserLink element) {

        ICoreUser loggedInCoreUser = (ICoreUser) coreUserService.findOne(element.getCoreUser().getId());
        if (loggedInCoreUser == null) {
            throw new UFPAuthenticateException.AuthNotValidatedException();
        }
        coreUserHelperService.transformUserToCryptonizedID(loggedInCoreUser);
        return new TokenData(loggedInCoreUser.getId());
    }
}
