package com.froso.ufp.modules.optional.userresourcefilter.configuration;

import com.froso.ufp.modules.optional.userresourcefilter.controller.*;
import com.froso.ufp.modules.optional.userresourcefilter.service.*;
import org.springframework.context.annotation.*;

@Configuration
public class EnableDefaultController {

    @Bean
    public UserResourceFilterController getUserDefaultResourceFilterController(UserResourceFilterService userResourceFilterService) {

        return new UserResourceFilterController(userResourceFilterService);

    }
}
