package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import org.joda.time.*;

public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {
    @Override
    public void serialize(DateTime value,
                          JsonGenerator gen,
                          SerializerProvider arg2) throws IOException {
        // rely on joda time iso8601 date format :(
        gen.writeString(value.toString());

    }
}
