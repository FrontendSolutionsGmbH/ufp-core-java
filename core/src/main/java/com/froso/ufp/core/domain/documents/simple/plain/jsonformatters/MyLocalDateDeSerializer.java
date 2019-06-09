package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import org.apache.commons.lang.*;
import org.joda.time.*;

public class MyLocalDateDeSerializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) throws IOException {
        if (StringUtils.EMPTY.equals(jsonParser.getValueAsString())) {
            return null;
        } else {
            return LocalDate.parse(jsonParser.getValueAsString());
        }
    }
}
