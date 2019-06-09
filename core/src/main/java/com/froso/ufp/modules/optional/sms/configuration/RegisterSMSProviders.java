package com.froso.ufp.modules.optional.sms.configuration;

import com.froso.ufp.modules.optional.sms.model.sms.*;
import com.froso.ufp.modules.optional.sms.service.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.*;
import org.springframework.stereotype.*;


@Component
public class RegisterSMSProviders {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterSMSProviders.class);
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    SMSResourcesService smsResourcesService;

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
        LOGGER.info("a context refreshed event happened" + getInstantiatedSigletons(applicationContext).toString());
        List<Object> beans = getInstantiatedSigletons(applicationContext);
        for (Object bean : beans) {
            if (bean instanceof ISMSProvider) {
                ISMSProvider ismsProvider = (ISMSProvider) bean;
                LOGGER.info("Found SMSProvider Bean:" + bean.toString());
                smsResourcesService.registerRessource(ismsProvider.getName(), ismsProvider);
            }
        }
    }
}
