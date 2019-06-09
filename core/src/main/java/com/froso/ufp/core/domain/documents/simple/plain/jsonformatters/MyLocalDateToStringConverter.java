package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import org.joda.time.*;
import org.springframework.core.convert.converter.*;

public class MyLocalDateToStringConverter implements Converter<LocalDate, String> {
    public String convert(LocalDate var1) {
        return var1.toString();
    }
}
