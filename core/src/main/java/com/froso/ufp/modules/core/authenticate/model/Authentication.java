package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.user.model.*;

public interface Authentication<T> extends IDataDocumentWithCoreUserLink, IDataDocumentWithClientLink, AuthenticationStatus {
    boolean isEnabled();

    Boolean isVerified();

    T getData();
}
