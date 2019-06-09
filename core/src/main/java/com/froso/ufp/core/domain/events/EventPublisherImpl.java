package com.froso.ufp.core.domain.events;

import org.springframework.context.*;

public class EventPublisherImpl implements ApplicationEventPublisherAware {

    protected ApplicationEventPublisher publisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

}
