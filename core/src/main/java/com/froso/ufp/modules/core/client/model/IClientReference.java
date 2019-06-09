package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;

public interface IClientReference {
    DataDocumentLink<IClient> getClient();
}
