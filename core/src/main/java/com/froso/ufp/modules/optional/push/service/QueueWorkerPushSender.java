package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.apache.commons.lang3.exception.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.*;

/**
 * The type Queue worker push sender.
 */
@Service
public class QueueWorkerPushSender {

    /**
     * The constant MAX_RETRY_COUNT.
     */
    public static final int MAX_RETRY_COUNT = 10;
    /**
     * The constant PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP.
     */
    public static final String PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP = "workflow.maxEntriesToProcessInOneStep";
    /**
     * The constant PROPERTY_WORKFLOW_MAXTHREADS.
     */
    public static final String PROPERTY_WORKFLOW_MAXTHREADS = "workflow.maxthreads";
    private static final String PROP_NAME_SEND_PUSH_ENABLED = "workflow.push.enabled";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueWorkerPushSender.class);
    private final AtomicBoolean processingPush = new AtomicBoolean(false);

    @Autowired
    private IPropertyService propertyService;
    /**
     * The Thread pool task executor.
     */
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private PushService pushService;


    @Autowired
    private PushSenderService pushSenderService;


    @WorkerAnnotation(name = "Push Messages Worker", description = "Works through Push messages to be send to devices")
    public void sendNewEmails() {


        if (!propertyService.getPropertyValueBoolean(PROP_NAME_SEND_PUSH_ENABLED)) {
            return;
        }
        if (!processingPush.get()) {
            processingPush.set(true);
            Runnable t = new Runnable() {
                @Override
                public void run() {
                    try {

                        Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP), Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
                        List<QueuePushMessage> workflowInstances = pushService.findAllNew(pageable);
                        List<QueuePushMessage> workflowInstancesError = pushService.findAllErrornous(pageable);
                        // Add the errornous as well
                        workflowInstances.addAll(workflowInstancesError);
                        Parallel.parallelFor(workflowInstances, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAXTHREADS), "PushSending", new ParallelStep() {
                            @Override
                            public boolean execute(int index, List<?> list) {
                                QueuePushMessage simpleQueuePushMessage = (QueuePushMessage) list.get(index);
                                try {


                                    LOGGER.debug("Processing QUEUE Push :" + simpleQueuePushMessage.toString());
                                    pushSenderService.sendPushMessageToUserDirect(simpleQueuePushMessage);
                                    pushService.save(simpleQueuePushMessage);
                                    LOGGER.debug("Processing QUEUE Finish :" + simpleQueuePushMessage.toString());
                                } catch (Exception e) {

                                    LOGGER.error("queue worker error", e);
                                    simpleQueuePushMessage.setErrorMessage(e.getMessage() + '-' + ExceptionUtils.getRootCauseMessage(e));
                                    simpleQueuePushMessage.setStatus(MessageStatusEnum.ERROR);
                                    pushService.save(simpleQueuePushMessage);


                                }
                                return true;
                            }
                        });
                        processingPush.set(false);
                    } catch (Exception e) {
                        LOGGER.error("Push Message Error", e);
                        processingPush.set(false);

                    }
                }

            };
            threadPoolTaskExecutor.execute(t);

        }
    }

}
