package com.froso.ufp.modules.core.applicationproperty.model;

import com.mongodb.*;
import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class LocalDateWriteConverter implements Converter<org.joda.time.LocalTime, DBObject> {

    public DBObject convert(LocalTime source) {
        DBObject dbo = new BasicDBObject();
//    dbo.put("_id", source.getId());
        dbo.put("hour", source.getHourOfDay());
        dbo.put("minute", source.getMinuteOfHour());
        dbo.put("second", source.getSecondOfMinute());
        dbo.put("millis", source.getMillisOfSecond());
        // dbo.put("name", source.getFirstName());
        // dbo.put("age", source.getAge());
        return dbo;
    }
}