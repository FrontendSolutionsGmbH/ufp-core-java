package com.froso.ufp.modules.optional.messaging.service;

/**
 * The type Queue email service.
 */
@Service
public class QueueEmailService extends AbstractClientRefService<QueueEmail> {

    /**
     * Find all new list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<QueueEmail> findAllNew(Pageable pageable) {
        return findByKeyValue("status", "=" + MessageStatusEnum.WAITING_TO_SEND.toString());
    }

    /**
     * Find all errornous list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<QueueEmail> findAllErrornous(Pageable pageable) {
        return findByKeyValue("status", "=" + MessageStatusEnum.ERROR.toString());
    }
}
