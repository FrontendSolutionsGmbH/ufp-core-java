package com.froso.ufp.modules.optional.datapoll.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.datapoll.model.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import org.apache.commons.io.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.config.annotation.*;

/**
 * The type Queue worker email sender.
 */
@EnableWebSocket
@Service
public class QueueWorkerPollData implements ServiceStatusReporter, INamedObject {

    public static final String NAME = "EmailSenderService";
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueWorkerPollData.class);
    @Autowired
    private PollDataService pollDataService;
    @Autowired
    private PollConfigService pollConfigService;


    private String readFile(PollDataConfig config) {
        String result = null;
        try {
            InputStream in = new URL(config.getUrl()).openStream();

            try {
                result = IOUtils.toString(in, Charset.defaultCharset());
            } finally {
                IOUtils.closeQuietly(in);
            }

            if (result != null) {

                PollData pollData = pollDataService.createNewDefault();
                pollData.setRequest(config.getUrl());
                pollData.setClient(config.getClient());
                pollData.setData(result);
                pollData.setType(config.getName());
                pollData.setInitiator(new DataDocumentLink<>(config.getId()));
                pollDataService.save(pollData);

            }
        } catch (Exception e) {

            LOGGER.error("queueqorkerdata error", e);
            result = e.getMessage();
        }

        return result;
    }


    private void handle(PollDataConfig config) {
        String result = readFile(config);
// and store log
        config.setLastExecuted(DateTime.now());
        config.setLastResult(result);
        pollConfigService.save(config);
    }

    @WorkerAnnotation(intervalSeconds = 1, description = "Works through email queue and processes them", name = "QueueWorkerEmailSender")
    public void pollDataExecutor() {

        DateTime now = DateTime.now();
        LOGGER.info("POLLING");
        List<PollDataConfig> pollDataConfigs = pollConfigService.findByKeyValue("enabled", "true");
        for (PollDataConfig config : pollDataConfigs) {

            if ((config.getStart() == null || config.getStart().isBefore(now)) && (config.getEnd() == null || config.getEnd().isAfter(now))) {
                // we are in start/end interval
                // check last time executed
                if (config.getLastExecuted() == null) {
                    // first time
                    // we should execute
                    handle(config);

                } else {
                    if (config.getLastExecuted().plusMinutes(config.getIntervalMinutes()).isBefore(now)) {
                        handle(config);
                    }
                }
            }

        }


    }

    public String getName() {

        return NAME;

    }

    @Override
    public ServiceStatus getServiceStatus() {

        ServiceStatus s = new ServiceStatus();
        //     s.setIsWorking(sendMail(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL), "Status Mail", "test test test"));
        //     s.addLogMessage("From: " + propertyService.getPropertyValue(PROP_NAME_SENDMAIL_FROM));
        //     s.addLogMessage("AdminMail: " + propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL));
        //     s.setName(TYPE_NAME);
        return s;
    }

}
