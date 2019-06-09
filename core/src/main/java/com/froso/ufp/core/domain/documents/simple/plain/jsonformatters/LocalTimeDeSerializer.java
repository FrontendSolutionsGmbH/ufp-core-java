package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import org.apache.commons.lang.*;

public class LocalTimeDeSerializer extends JsonDeserializer<LocalTime> {
    public static DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("HH:mm[:ss]");
    @Override
    public LocalTime deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) throws IOException {
        if (StringUtils.EMPTY.equals(jsonParser.getValueAsString())) {
            return null;
        } else {


            LocalTime localTime = LocalTime.from(timeFormatter.parse(jsonParser.getValueAsString()));
            return localTime;
        }
    }
}
