package com.froso.ufp.modules.core.worker.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.core.worker.model.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.config.annotation.*;

/**
 * The type Queue worker push sender.
 */
@Service
@EnableWebSocket
public class WorkerManagerService {


    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerManagerService.class);
    private final AtomicBoolean processing = new AtomicBoolean(false);

    private List<UFPWorkerInstanceData> schedulers = new ArrayList<>();
    @Autowired
    private UFPWorkerService workerService;
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private Map<String, UFPWorkerInstanceData> activeInstances = new HashMap<>();


    /**
     * Register annotated method.
     *
     * @param bean   the bean
     * @param method the method
     */
    public void registerAnnotatedMethod(Object bean, Method method) {
        try {

            if (method.isAnnotationPresent(WorkerAnnotation.class)) {
                WorkerAnnotation ta = method.getAnnotation(WorkerAnnotation.class);
                UFPWorkerInstanceData instanceData = new UFPWorkerInstanceData();
                instanceData.setBean(bean);
                instanceData.setMethod(method);
                instanceData.setName(ta.name());
                schedulers.add(instanceData);
                UFPWorker ufpWorker = workerService.getWorkerData(ta.name());
                if (ufpWorker == null) {
                    // create a new worker entry in db
                    UFPWorker worker = new UFPWorker();
                    worker.setName(ta.name());
                    worker.setDescription(ta.description());
                    worker.setStatus(UFPWorkerStatusEnum.IDLE);
                    worker.setIntervalSeconds(ta.intervalSeconds());
                    worker.setEnabled(ta.enabled());
                    workerService.save(worker);
                } else {
                    // use existing and reset running/error flags
                    ufpWorker.setStatus(UFPWorkerStatusEnum.IDLE);
                    workerService.save(ufpWorker);
                }
            }

        } catch (Exception e) {
            LOGGER.error("UFPWorker Error", e);
        }
    }


    /**
     * Is instance present boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public Boolean isInstancePresent(String name) {
        for (UFPWorkerInstanceData data : schedulers) {

            try {
                if (data.getName().equals(name)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("UFPWorker Error", e);
            }

        }

        return false;
    }

    private void handleInstance(final UFPWorkerInstanceData workerInstanceData) {
        try {
            /// make a thread for each of the working threads
            // any worker that is over its interval pause time will be triggered here
            // obtain current worker config from db
            final UFPWorker workerMetaData = workerService.getWorkerData(workerInstanceData.getName());
            if (workerMetaData != null) {
                if (workerMetaData.getEnabled() == true) {
                    if ((workerMetaData.getStatus() == UFPWorkerStatusEnum.IDLE)
                            || (workerMetaData.getStatus() == UFPWorkerStatusEnum.ERROR)) {
                        DateTime now = DateTime.now();
                        DateTime last = workerMetaData.getLastExecutionEnd();
                        int seconds = workerMetaData.getIntervalSeconds();
                        if (last != null) {
                            seconds = Seconds.secondsBetween(last, now).getSeconds();
                        }
                        if (seconds >= workerMetaData.getIntervalSeconds()) {
                            // modify entry in database to reflect start of worker and start time
                            workerMetaData.setLastExecutionStart(DateTime.now());
                            workerMetaData.setLastExecutionEnd(null);
                            workerMetaData.setStatus(UFPWorkerStatusEnum.WORKING);
                            workerService.save(workerMetaData);
                            // prepare thread runnable and execute the referenced method
                            Runnable t = new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        workerInstanceData.execute();
                                        workerMetaData.setServiceStatus(workerInstanceData.getServiceStatus());
                                        workerMetaData.setLastExecutionEnd(DateTime.now());
                                        workerMetaData.setStatus(UFPWorkerStatusEnum.IDLE);
                                        workerService.save(workerMetaData);
                                    } catch (Exception e) {
                                        LOGGER.error("handleInstance error", e);
                                        workerMetaData.setServiceStatus(workerInstanceData.getServiceStatus());
                                        workerMetaData.setLastExecutionEnd(DateTime.now());
                                        workerMetaData.setStatus(UFPWorkerStatusEnum.ERROR);

                                    } finally {
                                        workerService.save(workerMetaData);
                                    }
                                }
                            };
                            threadPoolTaskExecutor.execute(t);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("UFPWorker Error", e);
        }
    }

    /**
     * Send new emails.
     */
    @Scheduled(fixedRateString = "${" + UFPWorkerConstants.UFP_MODULES_CORE_QUEUE_UPDATE_INTERVAL_MILLISECONDS + ":1000}")
    @Async
    public void processQueue() {
        if (!processing.get()) {
            processing.set(true);
            for (final UFPWorkerInstanceData workerInstanceData : schedulers) {

                try {
                    handleInstance(workerInstanceData);
                } catch (Exception e) {
                    LOGGER.error("UFPWorker Error", e);
                }
            }
            processing.set(false);
        }

    }
}
