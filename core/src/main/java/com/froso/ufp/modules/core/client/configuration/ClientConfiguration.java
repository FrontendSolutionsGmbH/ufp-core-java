package com.froso.ufp.modules.core.client.configuration;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.client.controller",
        "com.froso.ufp.modules.core.client.service"
})
public class ClientConfiguration {


    /**
     * Configure bean.
     *
     * @param globalsService the globals service
     * @param provider       the provider
     */
    @Autowired(required = false)
    public void configureBean(GlobalsService globalsService, GlobalClientIdProvider provider, GlobalClientThemeProvider globalClientThemeProvider) {
        if (globalsService != null) {
            globalsService.addProperty("defaultClientId", ClientService.PROP_NAME_CLIENT_ID);
            globalsService.add("currentClientId", provider);
            globalsService.add("theme", globalClientThemeProvider);


        }
    }

    /**
     * Gets global client provider.
     *
     * @return the global client provider
     */
    @Bean
    public GlobalClientIdProvider getGlobalClientProvider() {
        return new GlobalClientIdProvider();
    }

    /**
     * Gets global client theme provider.
     *
     * @return the global client theme provider
     */
    @Bean
    public GlobalClientThemeProvider getGlobalClientThemeProvider() {
        return new GlobalClientThemeProvider();
    }


}
