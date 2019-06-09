package com.froso.ufp.modules.core.user.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import org.springframework.hateoas.*;

public interface IDataDocumentWithCoreUserLink extends Identifiable<String>, IDataDocument {
    DataDocumentLink<ICoreUser> getCoreUser();

    void setCoreUser(DataDocumentLink<ICoreUser> coreUser);
}
