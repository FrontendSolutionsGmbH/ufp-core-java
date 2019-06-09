package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Service
public class PushService extends AbstractRepositoryService2<QueuePushMessage> {

    /**
     * Constructor Simple email log service.
     */
    public PushService() {

        super(QueuePushMessage.TYPE_NAME);
    }

    /**
     * Find all new list.
     *
     * @param p the p
     * @return the list
     */
    public List<QueuePushMessage> findAllNew(Pageable p) {
        return findByKeyValue("status", MessageStatusEnum.WAITING_TO_SEND.toString());
    }

    /**
     * Find all errornous list.
     *
     * @param p the p
     * @return the list
     */
    public List<QueuePushMessage> findAllErrornous(Pageable p) {
        return findByKeyValue("state", "ERROR");
    }

}
