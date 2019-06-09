package com.froso.ufp.modules.optional.textcomponent.service;

import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 12.12.13.
 */
@Service
public class TextComponentValueCacheService {
    @Autowired
    private TextComponentService textComponentService;

    @Autowired
    private TextComponentLanguageService textComponentLanguageService;
    @Autowired
    private TextComponentValueService textComponentValueService;

    private Map<String, Map<String, Map<String, String>>> cache = new HashMap<>();


    /**
     * Clear cache.
     */
    public void clearCache() {
        cache = new HashMap<>();
    }


    /**
     * Read elements response entity.
     *
     * @param group the group
     * @param lang  the language
     * @return the response entity
     */
    public Map<String, String> getTranslation(
            String group, String lang
    ) {


        Map<String, String> result = new HashMap<>();
        // get language id
        TextComponentLanguage langDef = textComponentLanguageService.findOneByKeyValue("lang", lang);
        // get components

        if (langDef == null) {


            langDef = textComponentLanguageService.findOneByKeyValue("lang", TextComponentLanguage.DEFAULT_LANGUAGE);

        }


        if (langDef != null)

        {

            /* caching disabled
            if (cache.get(group) != null) {

                if (cache.get(group).get(langDef.getLanguage()) != null) {
                    return cache.get(group).get(langDef.getLanguage());
                }
            }
            */

            //      List<TextComponent> components = textComponentService.findByKeyValue("groupNames", group);
            List<TextComponent> components = textComponentService.findAll();
            // get translation for each component
            for (TextComponent component : components) {

                Map<String, String> searchmap = new HashMap<>();

                searchmap.put("languageLink._id", "=" + langDef.getId());
                searchmap.put("componentLink._id", "=" + component.getId());
                TextComponentValue value = textComponentValueService.findOneByKeyValues(searchmap);
                if (value != null) {
                    result.put(component.getName(), value.getValue());
                }

            }

            if (cache.get(group) == null) {

                Map<String, Map<String, String>> newmap = new HashMap<>();
                newmap.put(langDef.getLanguageName(), result);

                cache.put(group, newmap);

            }

        }

        return result;

    }
}
