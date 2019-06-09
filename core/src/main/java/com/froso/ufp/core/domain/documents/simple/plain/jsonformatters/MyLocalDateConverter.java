package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class MyLocalDateConverter implements Converter<LocalDate, String> {
    public String convert(LocalDate var1) {
        // rely on joda time iso8601 date format :(

        return var1.toString();
    }
}
