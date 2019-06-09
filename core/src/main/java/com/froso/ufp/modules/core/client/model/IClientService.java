package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.service.*;

public interface IClientService<T extends IClient> extends RepositoryService<T> {
    String getCurrentClientId();

}
