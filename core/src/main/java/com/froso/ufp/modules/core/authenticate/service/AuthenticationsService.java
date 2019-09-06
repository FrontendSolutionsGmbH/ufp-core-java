package com.froso.ufp.modules.core.authenticate.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.roles.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AuthenticationsService extends AbstractResourcesService<AuthenticateService> {

    public static final String PROP_NAME_AUTOLOGIN = "ufp.optional.authenticate.autologin";
    public static final String PROP_NAME_AUTOREGISTER = "ufp.optional.authenticate.autoregister";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationsService.class);

    private final IPropertyService propertyService;
    private final ICoreUserService coreUserService;
    private final CoreUserHelperService coreUserHelperService;
    private final UserRoleService userRoleService;
    private final GlobalsService globalsService;

    @Autowired
    public AuthenticationsService(IPropertyService propertyService,
                                  ICoreUserService coreUserService,
                                  CoreUserHelperService coreUserHelperService,
                                  GlobalsService globalsService,
                                  ServiceService serviceService,
                                  UserRoleService userRoleService) {
        super(serviceService);
        this.coreUserHelperService = coreUserHelperService;
        this.globalsService = globalsService;
        this.propertyService = propertyService;
        this.coreUserService = coreUserService;
        this.userRoleService=userRoleService;
    }

    public Boolean isAutoRegisterEnabled() {
        return propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOREGISTER);
    }

    @Override
    public String getDescription() {

        return "lists enabled authentication methods";
    }

    @Override
    public String getName() {

        return "authentications";
    }

    @Override
    protected void onResourceRegistered(String name, AuthenticateService service) {

        Map<String, AuthenticateMetadata> data = new HashMap<>();

        for (Map.Entry<String, AuthenticateService> entry : this.resources.entrySet()) {
            AuthenticateMetadata meta = new AuthenticateMetadata();
            meta.setClientPasswordEncoding(entry.getValue().getPasswordEncoding());
            data.put(entry.getKey(), meta);
            LOGGER.info(entry.getKey() + "/" + entry.getValue());
        }
        globalsService.add("auth", data);
    }

    public TokenData finalizeAuthorization(IDataDocumentWithCoreUserLink element) {

        ICoreUser loggedInCoreUser = (ICoreUser) coreUserService.findOne(element.getCoreUser().getId());

return finalizeAuthorization(loggedInCoreUser);
    }

    public TokenData finalizeAuthorization(ICoreUser loggedInCoreUser) {

        if (loggedInCoreUser == null) {
            throw new UFPAuthenticateException.AuthNotValidatedException();
        }
        coreUserHelperService.transformUserToCryptonizedID(loggedInCoreUser);
        TokenData data= new TokenData(loggedInCoreUser.getId());
        data.setFirstName(loggedInCoreUser.getFirstName());
        data.setFirstName(loggedInCoreUser.getLastName());
        data.setRights(userRoleService.getAllRights( loggedInCoreUser.getRoles()));

        return data;
    }

    public Map<String, List<Authentication>> getAllAuthenticationsForCoreUser(String coreUserId) {
        Map<String, List<Authentication>> result = new HashMap<>();
        for (Map.Entry<String, AuthenticateService> entry : this.resources.entrySet()) {
            //    LOGGER.info(entry.getKey() + "/" + entry.getValue());

            if (entry.getValue() instanceof AuthenticateService) {
                List<Authentication> authentications = entry.getValue().getAuthenticationsForUser(coreUserId);
                if (!authentications.isEmpty()) {
                    result.put(entry.getKey(), authentications);
                }
            }
        }

        return result;

    }

    public AuthenticateShortRef convertToRef(Authentication authentication, String authName) {
        AuthenticateShortRef ref = new AuthenticateShortRef();
        ref.setId((String) authentication.getId());
        ref.setEnabled(authentication.isEnabled());
        ref.setVerified(authentication.isVerified());
        ref.setType(authName);
        ref.setData(authentication.getData());
        return ref;
    }

    private List<AuthenticateShortRef> convertRefs(List<Authentication> authentications, String authName) {

        List<AuthenticateShortRef> result = new ArrayList<>();
        for (Authentication authentication : authentications) {
            AuthenticateShortRef ref = convertToRef(authentication, authName);
            result.add(ref);
        }
        return result;
    }

    public Map<String, List<AuthenticateShortRef>> getAllAuthenticationsForCoreUserAsShortRef(String coreUserId) {
        Map<String, List<AuthenticateShortRef>> result = new HashMap<>();
        for (Map.Entry<String, AuthenticateService> entry : this.resources.entrySet()) {
            //    LOGGER.info(entry.getKey() + "/" + entry.getValue());

            if (entry.getValue() instanceof AuthenticateService) {
                List<Authentication> authentications = entry.getValue().getAuthenticationsForUser(coreUserId);
                if (!authentications.isEmpty()) {
                    result.put(entry.getKey(), convertRefs(authentications, entry.getKey()));
                }
            }
        }

        return result;

    }
}
