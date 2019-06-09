package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.time.*;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {
    @Override
    public void serialize(LocalTime value,
                          JsonGenerator gen,
                          SerializerProvider arg2) throws IOException {
        // rely on joda time iso8601 date format :(
        gen.writeString(value.toString());

    }
}
