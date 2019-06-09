package com.froso.ufp.modules.core.user.configuration;

import com.froso.ufp.core.*;
import com.froso.ufp.core.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration

@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.user.controller",
        "com.froso.ufp.modules.core.user.activities",
        "com.froso.ufp.modules.core.user.commands",
        "com.froso.ufp.modules.core.user.eventhandler",
        "com.froso.ufp.modules.core.user.service"
})
public class UserModuleConfiguration {
    /**
     * Configure token free path.
     *
     * @param requestDefinitionService the request definition service
     */
    @Autowired
    public void configureTokenPath(RequestDefinitionService requestDefinitionService) {


        requestDefinitionService.registerTokenFreePath("/" + UFPConstants.API + "/CoreUser/Auth");
        requestDefinitionService.registerTokenFreePath("/" + UFPConstants.API + "/CoreUser/Command");
    }


}
