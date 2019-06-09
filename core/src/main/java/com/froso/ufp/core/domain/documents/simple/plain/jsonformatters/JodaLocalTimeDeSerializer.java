package com.froso.ufp.core.domain.documents.simple.plain.jsonformatters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by ckleinhuix on 18.01.14.
 */
public class JodaLocalTimeDeSerializer extends JsonDeserializer<LocalTime> {
    public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS");
    public static final DateTimeFormatter fmt2 = DateTimeFormat.forPattern("HH:mm:ss");

    /**
     * Deserialize date time.
     *
     * @param jsonParser             the json parser
     * @param deserializationContext the deserialization context
     * @return the date time
     * @throws IOException the iO exception
     */
    @Override
    public LocalTime deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) throws IOException {
        if (StringUtils.EMPTY.equals(jsonParser.getValueAsString())) {
            return null;
        } else {

            try {
                LocalTime localTime = fmt.parseLocalTime(jsonParser.getValueAsString());
                return localTime;

            } catch (Exception e) {
                try {
                    LocalTime localTime2 = fmt2.parseLocalTime(jsonParser.getValueAsString());
                    return localTime2;
                } catch (Exception e2) {
                    return null;
                }
            }

        }
    }
}
