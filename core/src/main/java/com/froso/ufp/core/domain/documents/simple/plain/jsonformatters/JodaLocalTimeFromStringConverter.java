package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class JodaLocalTimeFromStringConverter implements Converter<String, LocalTime> {
    public LocalTime convert(String var1) {
        // rely on joda time iso8601 date format :(
        return LocalTime.fromMillisOfDay(Long.parseLong(var1));
    }
}
