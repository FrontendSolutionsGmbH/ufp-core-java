package com.froso.ufp.modules.optional.securitylog.interfaces;

import com.froso.ufp.modules.optional.securitylog.model.*;

/**
 * Created by ckleinhuix on 03.12.2015.
 */
public interface ISecurityLogService {

    /**
     * Save.
     *
     * @param securityLog the simple security log
     * @return the simple security log
     */
    SecurityLog save(SecurityLog securityLog);

}
