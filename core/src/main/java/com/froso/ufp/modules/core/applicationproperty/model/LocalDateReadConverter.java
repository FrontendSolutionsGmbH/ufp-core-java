package com.froso.ufp.modules.core.applicationproperty.model;

import com.mongodb.*;
import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class LocalDateReadConverter implements Converter<DBObject, LocalTime> {

    public LocalTime convert(DBObject source) {

        Integer hour = Integer.parseInt(source.get("hour").toString());
        Integer minute = Integer.parseInt(source.get("minute").toString());
        Integer second = Integer.parseInt(source.get("second").toString());
        Integer millis = Integer.parseInt(source.get("millis").toString());
        LocalTime p = new LocalTime(hour, minute, second, millis);
        //source.get("_id"), (String) source.get("name"));
        //p.setAge((Integer) source.get("age"));


        return p;
    }
}