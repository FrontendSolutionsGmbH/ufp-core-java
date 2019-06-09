package com.froso.ufp.modules.core.worker.configuration;

import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.core.worker.service.*;
import com.froso.ufp.modules.optional.facebookbot.configuration.*;
import com.froso.ufp.modules.optional.sms.configuration.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.worker.controller",
        "com.froso.ufp.modules.core.worker.service"
})
public class WorkerModuleConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerModuleConfiguration.class);
    /**
     * The Application context.
     */
    @Autowired
    ApplicationContext applicationContext;

    /**
     * The Worker manager service.
     */
    @Autowired
    WorkerManagerService workerManagerService;

    private Boolean initialised = false;


    /**
     * Context refreshed event.
     */
    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        if (initialised) {
            return;
        }
        LOGGER.info("a context refreshed event happened" + RegisterSMSProviders.getInstantiatedSigletons(applicationContext).toString());
        List<Object> beans = FacebookBotModuleConfiguration.getInstantiatedSigletons(applicationContext);
        for (Object bean : beans) {
            // if (bean.getClass().isAnnotationPresent(WorkerAnnotation.class)) {
            //     facebookBotMethodService.registerBotHandler(bean);
            java.lang.reflect.Method[] methods = bean.getClass().getMethods();
            for (java.lang.reflect.Method m : methods) {
                try {
                    if (m.isAnnotationPresent(WorkerAnnotation.class)) {
                        WorkerAnnotation ta = m.getAnnotation(WorkerAnnotation.class);

                        workerManagerService.registerAnnotatedMethod(bean, m);

                        //       facebookBotMethodService.registerBotHandler(bean);
                        //       System.out.println(ta.regex());
                    }
                } catch (Exception e) {
                    LOGGER.error("workermoduleconfig error",e);
                }
            }

            // }

        }
        initialised = true;
    }
}
