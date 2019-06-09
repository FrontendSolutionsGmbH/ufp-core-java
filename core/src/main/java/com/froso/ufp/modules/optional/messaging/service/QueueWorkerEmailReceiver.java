package com.froso.ufp.modules.optional.messaging.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import java.util.concurrent.atomic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.concurrent.*;

/**
 * The type Queue worker email sender.
 */
//@Service
public class QueueWorkerEmailReceiver {

    public static final String PROP_NAME_RECEIVE_EMAILS_ENABLED = "receivemail.receiveEnabled";
    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "EmailSenderService";
    //get log4j handler
    private final AtomicBoolean processingEmails = new AtomicBoolean(false);
    /**
     * The Application property service.
     */
    @Autowired
    GlobalsService globalsService;
    /**
     * The Thread pool task executor.
     */
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private CheckingMails checkingMails;
    @Autowired
    private IPropertyService propertyService;

    @WorkerAnnotation(name = "Receiver Email Checker", description = "Checks if email received")
    public void sendNewEmails() {

        if (propertyService.getPropertyValueBoolean(PROP_NAME_RECEIVE_EMAILS_ENABLED)) {
            if (!processingEmails.get()) {
                processingEmails.set(true);
                Runnable t = new Runnable() {
                    @Override
                    public void run() {


                        checkingMails.main();
                        processingEmails.set(false);
                    }
                };
                threadPoolTaskExecutor.execute(t);
            }
        }

    }

}
