package com.froso.ufp.modules.optional.textcomponent.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 12.12.13.
 */
@Service
public class TextComponentValueService extends AbstractClientRefService<TextComponentValue> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextComponentValueService.class);

    @Autowired
    private TextComponentLanguageService languageService;


    @Override
    protected void fillDefaultObject(TextComponentValue object) {
        object.getLanguageLink().setId(languageService.getDefaultLanguageID());
        object.getComponentLink().setId("NON-EXISTING-DEFAULT-" + RandomStringUtils.randomNumeric(10));
        object.setValue("DefaultTextComponentValue");
    }


    @Override
    protected void prepareSave(TextComponentValue object) {
        /*
         for performance reasons, on every save of a textcomponent value, its position in the component tree is saved
         to the object for later easy find of translation
          */


        //   object.setPath(textComponentService.getPath(object.getComponent().getId()));


    }


    /**
     * main method to be called for replacing textcomponents in any string, used AFTER the velocity template
     * has been processed, every string marked with $[path] is tried to be replaced
     *
     * @param input          the input
     * @param data           the data
     * @param velocityEngine the velocity engine
     * @return string string
     */
    public String replaceTextComponents(String input, Map<String, Object> data, VelocityEngine velocityEngine) {
        String result = input;
        // iterate through all found $[path] marked elements in the text

        /**
         *  matches all pattern $path[$index]
         *
         *  important:
         *  the $index is then not translated by velocity, this is the reason because m.group(2) of the regexp is
         *  then put through the velocity engine again
         */
        try {
            Matcher m = Pattern.compile("\\$(.*)\\[(.*)\\]").matcher(input);

            StringBuffer buffer = new StringBuffer();
            while (m.find()) {

                String found1 = m.group(1);
                String found2 = m.group(2);
                StringWriter writer = new StringWriter();
                if (velocityEngine.evaluate(new VelocityContext(data), writer, "REPLACE TEXT COMPONENTS", m.group(2))) {
                    found2 = writer.toString();
                }
                String path = found1 + "." + found2;
                Map<String, String> search = new HashMap<>();

                search.put("path", path);
                search.put("languageID", languageService.getDefaultLanguageID());
                // try to find entry in db for language:
                List<TextComponentValue> value = findByKeyValue("path", path);
                if (value.size() > 0) {
                    m.appendReplacement(buffer, value.get(0).getValue());
                }

                m.appendTail(buffer);

            }

            if (buffer.length() != 0) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            LOGGER.error("Textcomponent error ", e);
        }

        return result;
    }


    /**
     * Gets and create if not existant.
     *
     * @param componentID the component id
     * @param languageID  the language id
     * @param newValue    the new value
     * @return the and create if not existant
     */
    public TextComponentValue getAndCreateIfNotExistant(String componentID, String languageID, String newValue) {

        Map<String, String> search = new HashMap<>();
        search.put("componentLink._id", "=" + componentID);
        search.put("languageLink._id", "=" + languageID);

        // Create Parent TextComponent Entry for all Enums
        List<TextComponentValue> textComponentValues = search(search);

        TextComponentValue textComponentValue;
        if (textComponentValues.size() > 0) {
            textComponentValue = textComponentValues.get(0);
        } else {
            textComponentValue = createNewDefault();
            textComponentValue.getComponentLink().setId(componentID);
            textComponentValue.getLanguageLink().setId(languageID);
            textComponentValue.setValue(newValue);
            save(textComponentValue);
        }
        if (newValue != null) {
            textComponentValue.setValue(newValue);
            save(textComponentValue);
        }
        return textComponentValue;

    }

}
