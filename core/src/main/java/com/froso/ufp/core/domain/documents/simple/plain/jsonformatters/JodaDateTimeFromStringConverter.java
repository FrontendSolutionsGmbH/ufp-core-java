package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class JodaDateTimeFromStringConverter implements Converter<String, DateTime> {
    public DateTime convert(String var1) {
        // rely on joda time iso8601 date format :(
        return DateTime.parse(var1);
    }
}
