package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;

public interface IDataDocumentWithClientLink extends IDataDocument {

    DataDocumentLink<IClient> getClient();

    void setClient(DataDocumentLink<IClient> client);
}
