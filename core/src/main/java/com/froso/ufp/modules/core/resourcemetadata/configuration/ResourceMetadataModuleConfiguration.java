package com.froso.ufp.modules.core.resourcemetadata.configuration;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.resourcemetadata.controller",
        "com.froso.ufp.modules.core.resourcemetadata.service"
})
public class ResourceMetadataModuleConfiguration {

}
