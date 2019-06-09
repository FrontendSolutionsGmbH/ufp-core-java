package com.froso.ufp.modules.core.user.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.user.model.*;

/**
 * Created by ckleinhuix on 26/12/2016.
 *
 * @param <T> the type parameter
 */
public interface ICoreUserService<T extends ICoreUser> extends RepositoryService<T> {

    /**
     * Find one core user core user.
     *
     * @param id the id
     * @return the core user
     */
    T findOneCoreUser(String id);

}
