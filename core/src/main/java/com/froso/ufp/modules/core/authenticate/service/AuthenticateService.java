package com.froso.ufp.modules.core.authenticate.service;

import com.froso.ufp.modules.core.authenticate.model.*;
import java.util.*;

/**
 * Created by ck on 07.07.2016.
 */
public interface AuthenticateService {
    TokenData getAuthenticationResult(Authentication authentication);

    List<Authentication> getAuthenticationsForUser(String coreUserId);

    String getPasswordEncoding();
}
