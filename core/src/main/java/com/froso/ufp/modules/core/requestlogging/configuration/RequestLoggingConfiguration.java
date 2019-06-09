package com.froso.ufp.modules.core.requestlogging.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.requestlogging.controller",
        "com.froso.ufp.modules.core.requestlogging.service"
})
public class RequestLoggingConfiguration {
    /**
     * Some filter registration filter registration bean.
     *
     * @return the filter registration bean
     */
    /*
    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(RequestLogFilter());
        registration.addUrlPatterns("/url/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("someFilter");
        registration.setOrder(1);
        return registration;
    }
             */

}
