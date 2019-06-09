package com.froso.ufp.core.configuration;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.*;
import java.text.*;
import java.util.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 29.05.2015.
 */
public class JacksonMapperConfig {

//    private static ObjectMapper instance;


    /**
     * Gets object mapper.
     *
     * @param view the view
     * @return the object mapper
     */
    public static ObjectMapper getObjectMapper(final Class view) {
        ObjectMapper mapper = new ObjectMapper() {

            private static final long serialVersionUID = 1L;

            @Override
            protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {

                return super._serializerProvider(config.withView(view));
            }
        };
        mapper.setDateFormat(DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH));// important dateData conversion jackson style ..
        // Disallow serialization of null values
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //  mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
        // mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;

    }

    /**
     * Gets object mapper.
     *
     * @return the object mapper
     */
    @Bean
    ObjectMapper getObjectMapper() {

        ObjectMapper instance = new ObjectMapper();

        instance.setDateFormat(DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH));// important dateData conversion jackson style ..
        // Disallow serialization of null values
        //  instance.enableDefaultTyping();
        instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return instance;

    }

}
