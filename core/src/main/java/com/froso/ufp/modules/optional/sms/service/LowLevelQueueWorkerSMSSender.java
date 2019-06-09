package com.froso.ufp.modules.optional.sms.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.sms.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.atomic.*;

@Service
public class LowLevelQueueWorkerSMSSender {

    public static final String NAME = "SMS Sender";
    public static final String PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP = "workflow.maxEntriesToProcessInOneStep";
    public static final String PROPERTY_WORKFLOW_MAXTHREADS = "workflow.maxthreads";
    private static final String PROP_NAME_SEND_SMS_ENABLED = "workflow.sms.enabled";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(LowLevelQueueWorkerSMSSender.class);
    private final AtomicBoolean processingSMS = new AtomicBoolean(false);

    @Autowired
    private IPropertyService propertyService;
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private LowLevelSMSService lowLevelSMSService;
    @Autowired
    private LowLevelSMSSenderService lowLevelSMSSenderService;

    @WorkerAnnotation(name = "LowLevel SMS Queue Worker", description = "Low Level Sms are send as is (no further template processing) by this worker", intervalSeconds = 1)
    public void sendNewEmails() {

        if (!propertyService.getPropertyValueBoolean(PROP_NAME_SEND_SMS_ENABLED)) {
            return;
        }
        if (!processingSMS.get()) {
            processingSMS.set(true);
            Runnable t = new Runnable() {
                @Override
                public void run() {

                    Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP), Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
                    List<QueueSms> messagesNew = lowLevelSMSService.findAllNew(pageable);
                    List<QueueSms> messagesErrornous = lowLevelSMSService.findAllErrornous(pageable);
                    // Add the errornous as well
                    messagesNew.addAll(messagesErrornous);
                    Parallel.parallelFor(messagesNew, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAXTHREADS), "SMSSending", new ParallelStep() {
                                @Override
                                public boolean execute(int index, List<?> list) {

                                    QueueSms lowLevelSMS = (QueueSms) list.get(index);

                                    lowLevelSMS.getInfo().setRetryCount(lowLevelSMS.getInfo().getRetryCount() + 1);

                                    if (lowLevelSMS.getInfo().getRetryCount() > 10) {
                                        lowLevelSMS.getInfo().setStatus(MessageStatusEnum.ERROR_CANCELLED);
                                    } else {
                                        LOGGER.debug("Processing:" + lowLevelSMS);
                                        try {
                                            lowLevelSMS = lowLevelSMSSenderService.sendSMS(lowLevelSMS);
                                            lowLevelSMS.getInfo().setStatus(MessageStatusEnum.PROCESSED);
                                        } catch (Exception e) {
                                            lowLevelSMS.getInfo().setStatus(MessageStatusEnum.ERROR);
                                            lowLevelSMS.getInfo().setErrorMessage(e.getMessage());
                                        }
                                    }
                                    lowLevelSMSService.save(lowLevelSMS);
                                    return true;
                                }
                            }

                    );
                    processingSMS.set(false);
                }
            };
            threadPoolTaskExecutor.execute(t);
        }
    }

}
