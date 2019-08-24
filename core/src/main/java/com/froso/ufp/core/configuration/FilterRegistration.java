package com.froso.ufp.core.configuration;

import com.froso.ufp.core.*;
import com.froso.ufp.core.response.filter.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import java.util.*;
import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.*;


@Configuration
public class FilterRegistration {

    @Bean(name = "UserFilter")
    public FilterRegistrationBean<TokenTanslatorFilterUser> tokentranslatorUser() {
        FilterRegistrationBean<TokenTanslatorFilterUser> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenTanslatorFilterUser("User"));
        registrationBean.addUrlPatterns("/" + UFPConstants.API + "/User/*");
        registrationBean.setOrder(0);
        registrationBean.setName("Api-Filter-TokenValidator-User");

        return registrationBean;
    }

    @Bean(name = "AutFilter")
    public FilterRegistrationBean<TokenTanslatorFilterUser> tokentranslatorAUTHENTICATION() {
        FilterRegistrationBean<TokenTanslatorFilterUser> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TokenTanslatorFilterUser(UFPAuthenticateConstants.AUTHENTICATION));
        registrationBean.addUrlPatterns("/" + UFPConstants.API + "/" + UFPAuthenticateConstants.AUTHENTICATION + "/*");
        registrationBean.setOrder(1);

        registrationBean.setName("Api-Filter-TokenValidator-Auth");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<TokenTranslatorFilterAdmin> tokentranslator3() {
        FilterRegistrationBean<TokenTranslatorFilterAdmin> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TokenTranslatorFilterAdmin());
        registrationBean.setName("Api-Filter-TokenValidator-Admin");
        registrationBean.addUrlPatterns("/" + UFPConstants.API + "/" + UFPConstants.ADMIN + "/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
//        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new CharacterEncodingFilter());
//        Map<String, String> params = new HashMap<>();
//        params.put("encoding", "UTF-8");
//        params.put("forceEncoding", "true");
//        registrationBean.setName("Api-Filter-CharacterEncoding");
//        registrationBean.setInitParameters(params);
//        registrationBean.addUrlPatterns("/" + UFPConstants.API + "/*");
//        registrationBean.setOrder(2);
//
//        return registrationBean;
//    }

    @Bean
    public FilterRegistrationBean<TokenValidatorFilter> tokenValidatorFilter() {
        FilterRegistrationBean<TokenValidatorFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setName("Api-Filter-TokenValidator-Api");
        registrationBean.setFilter(new TokenValidatorFilter());
        registrationBean.addUrlPatterns("/" + UFPConstants.API + "/*");
        registrationBean.setOrder(3);

        return registrationBean;
    }

}
