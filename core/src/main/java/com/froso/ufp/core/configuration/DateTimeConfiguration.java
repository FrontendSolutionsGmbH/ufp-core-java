package com.froso.ufp.core.configuration;

import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.*;

/**
 * Created by ckleinhuix on 07.05.2015.
 */
@Configuration
public class DateTimeConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * time zone configuration, is set ONCE at application startup
     */

    @Value("${timezone:00:00}")
    private String timeZone;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {


        String[] timezonoffset = timeZone.split(":");

        DateTimeZone.setDefault(DateTimeZone.forOffsetHoursMinutes(Integer.parseInt(timezonoffset[0]), Integer.parseInt(timezonoffset[1])));


    }
}
