package com.froso.ufp.modules.optional.authenticate.masterpassword.service;

import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.model.*;
import org.springframework.stereotype.*;

@Service
public class MasterPasswordAuthenticateCRUDService extends AbstractCoreAuthenticationsService<MasterPasswordModel> {

    public MasterPasswordAuthenticateCRUDService() {

        super(MasterPasswordModel.TYPE_NAME);

    }

}
