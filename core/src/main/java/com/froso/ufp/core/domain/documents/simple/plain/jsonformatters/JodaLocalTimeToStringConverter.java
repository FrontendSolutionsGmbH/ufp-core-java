package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class JodaLocalTimeToStringConverter implements Converter<LocalTime, String> {
    public String convert(LocalTime var1) {
        // rely on joda time iso8601 date format :(

        return "" + var1.millisOfDay().get();
    }
}
