package com.froso.ufp.core.domain.documents.simple.plain.meta;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;
import java.io.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 25.03.2015.
 */
public class JacksonDateTimeSerializer extends StdSerializer<DateTime> {

    private static final long serialVersionUID = -6241997616550698465L;

    /**
     * Constructor Jackson date time serializer.
     */
    public JacksonDateTimeSerializer() {

        super(DateTime.class);
    }

    /**
     * Serialize void.
     *
     * @param date     the date
     * @param json     the json
     * @param provider the provider
     * @throws IOException             the iO exception
     * @throws JsonGenerationException the json generation exception
     */
    @Override
    public void serialize(DateTime date,
                          JsonGenerator json,
                          SerializerProvider provider) throws IOException {
        // The client side will handle presentation, we just want it accurate
        json.writeString(date.toString());
    }
}
