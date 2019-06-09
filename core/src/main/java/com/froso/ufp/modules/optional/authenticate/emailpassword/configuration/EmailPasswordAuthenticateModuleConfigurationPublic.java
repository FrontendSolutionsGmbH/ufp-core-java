package com.froso.ufp.modules.optional.authenticate.emailpassword.configuration;

import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.authenticate.emailpassword.controller.auth"
}
)
public class EmailPasswordAuthenticateModuleConfigurationPublic {

    @Bean
    public EmailPasswordAuthenticateService getEmailPasswordAuthenticateService() {

        // enter global properties here exposed in the globals ufp endpoint

        return new EmailPasswordAuthenticateService();

    }
}
