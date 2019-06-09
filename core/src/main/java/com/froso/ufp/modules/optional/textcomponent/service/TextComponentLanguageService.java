package com.froso.ufp.modules.optional.textcomponent.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Created by ckleinhuix on 12.12.13.
 */
@Service
public class TextComponentLanguageService extends AbstractClientRefService<TextComponentLanguage> implements IGlobalsPropertyProvider {

    /**
     * The constant PROP_NAME_IDENTIFIER.
     */
    public static final String PROP_NAME_IDENTIFIER = "languageKey";

    @Autowired
    private GlobalsService globalsService;

    @PostConstruct
    protected void postConstruct() {

        globalsService.add("languages", this);

    }

    @Override
    public Object getPropertyValue() {
        Set<String> result = new TreeSet<>();
        List<TextComponentLanguage> allLanguages = findAll();
        for (TextComponentLanguage lang : allLanguages) {

            if (null != lang.getLanguageKey()) {
                result.add(lang.getLanguageKey());
            }

        }
        return result;
    }

    protected void fillDefaultObject(TextComponentLanguage object) {

        // check if default language is alread available

        if (findByIdentifier(TextComponentLanguage.DEFAULT_LANGUAGE) == null) {

            object.setLanguageKey(TextComponentLanguage.DEFAULT_LANGUAGE);
            object.setLanguageName(TextComponentLanguage.DEFAULT_LANGUAGE_NAME);

        } else {
            object.setLanguageName(TextComponentLanguage.DEFAULT_LANGUAGE);
            object.setLanguageKey(TextComponentLanguage.DEFAULT_LANGUAGE_NAME);

        }

    }

    /**
     * Gets default language id.
     *
     * @return the default language id
     */
    public String getDefaultLanguageID() {

        TextComponentLanguage defaultLang = findByIdentifierOrCreateAndReturn(TextComponentLanguage.DEFAULT_LANGUAGE);
        if (defaultLang == null) {
            defaultLang = createNewDefault();
        }

        return defaultLang.getId();

    }

    /**
     * Find by identifier simple text component language.
     *
     * @param identifier the identifier
     * @return the simple text component language
     */
    public TextComponentLanguage findByIdentifierOrCreateAndReturn(String identifier) {
        if (identifier != null) {

            List<TextComponentLanguage> result = findByKeyValue(PROP_NAME_IDENTIFIER, identifier);
            if (result.size() > 0) {
                return result.get(0);
            } else {
                // create language key
                TextComponentLanguage resultsingle = createNewDefault();
                resultsingle.setLanguageName(identifier);
                save(resultsingle);
                return resultsingle;

            }
        }
        return null;

    }

    /**
     * Find by identifier text component language.
     *
     * @param identifier the identifier
     * @return the text component language
     */
    public TextComponentLanguage findByIdentifier(String identifier) {
        if (identifier != null) {

            List<TextComponentLanguage> result = findByKeyValue(PROP_NAME_IDENTIFIER, identifier);
            if (result.size() > 0) {
                return result.get(0);
            }
        }
        return null;

    }

}
