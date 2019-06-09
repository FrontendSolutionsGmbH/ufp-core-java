package com.froso.ufp.modules.optional.authenticate.emailpassword.configuration;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.emailpassword.controller.register"
        })
public class EmailPasswordAuthenticateModuleConfigurationRegisterPublic {

}
