package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class MyLocalDateFromStringConverter implements Converter<String, LocalDate> {
    public LocalDate convert(String var1) {
        return LocalDate.parse(var1);
    }
}
