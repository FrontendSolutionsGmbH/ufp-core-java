package com.froso.ufp.modules.optional.facebookbot.configuration;

import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.service.*;
import com.froso.ufp.modules.optional.sms.configuration.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.facebookbot.controller",
        "com.froso.ufp.modules.optional.facebookbot.service"
})
public class FacebookBotModuleConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookBotModuleConfiguration.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private FacebookBotMethodService facebookBotMethodService;
    private Boolean initialised = false;

    public static List<Object> getInstantiatedSigletons(ApplicationContext ctx) {
        List<Object> singletons = new ArrayList<>();

        String[] all = ctx.getBeanDefinitionNames();

        ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext) ctx).getBeanFactory();
        for (String name : all) {
            Object s = clbf.getSingleton(name);
            if (s != null)
                singletons.add(s);
        }

        return singletons;

    }

    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        if (initialised) {
            return;
        }
        LOGGER.info("a context refreshed event happened" + RegisterSMSProviders.getInstantiatedSigletons(applicationContext).toString());
        List<Object> beans = getInstantiatedSigletons(applicationContext);
        for (Object bean : beans) {
            if (bean.getClass().isAnnotationPresent(TextBotAnnotation.class)) {
                facebookBotMethodService.registerBotHandler(bean);
             /*   java.language.reflect.Method[] methods = bean.getClass().getMethods();
                for (java.language.reflect.Method m : methods) {
                    if (m.isAnnotationPresent(TextBotMethodAnnotation.class)) {
                        TextBotMethodAnnotation ta = m.getAnnotation(TextBotMethodAnnotation.class);
                        facebookBotMethodService.registerBotHandler(bean);
                        LOGGER.info(ta.regex());
                    }
                }*/

            }

        }
        initialised = true;
    }
}
