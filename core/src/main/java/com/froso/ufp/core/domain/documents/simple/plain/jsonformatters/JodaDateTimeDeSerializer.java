package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import org.apache.commons.lang.*;
import org.joda.time.*;

public class JodaDateTimeDeSerializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        if (StringUtils.EMPTY.equals(jsonParser.getValueAsString())) {
            return null;
        } else {
            return DateTime.parse(jsonParser.getValueAsString());
        }
    }
}
