package com.froso.ufp.modules.optional.authenticate.masterpassword.configuration;

import com.froso.ufp.modules.core.globals.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.authenticate.masterpassword.configuration",
        "com.froso.ufp.modules.optional.authenticate.masterpassword.controller",
        "com.froso.ufp.modules.optional.authenticate.masterpassword.service"
})
public class MasterPasswordModuleConfiguration {


    /**
     * Configure bean.
     *
     * @param globalsService the globals service
     */
    @Autowired(required = false)
    public void configureUfpMasterPassword(GlobalsService globalsService) {

        // enter global properties here exposed in the globals ufp endpoint

    }


}
