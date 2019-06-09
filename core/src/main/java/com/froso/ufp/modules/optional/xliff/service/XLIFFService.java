package com.froso.ufp.modules.optional.xliff.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.xliff.model.*;
import org.springframework.stereotype.*;

@Service
public class XLIFFService extends AbstractRepositoryService2<XLIFFModel> {


    /**
     * Constructor Simple product service.
     */
    public XLIFFService() {

        super(XLIFFModel.TYPE_NAME);

    }

}
