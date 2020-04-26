package com.froso.ufp.modules.optional.dataTestModule.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.dataTestModule.model.*;
import org.springframework.stereotype.*;

@Service
public class DataTestService extends AbstractRepositoryService2<DataTestModel> {

    /**
     * Constructor Simple product service.
     */
    public DataTestService() {

        super(DataTestModel.TYPE_NAME);

    }

}
