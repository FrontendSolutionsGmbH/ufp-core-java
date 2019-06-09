package com.froso.ufp.modules.optional.textcomponent.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.textcomponent.model.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textcomponent.controller",
        "com.froso.ufp.modules.optional.textcomponent.service",
        "com.froso.ufp.modules.optional.textcomponent.eventhandler"
})
public class TextComponentModuleConfiguration {

    /**
     * The constant DEFAULT_LANG_KEY.
     */
    public static final String DEFAULT_LANG_KEY = "defaultLang";
    /**
     * The constant DEFAULT_LANG_ID_KEY.
     */
    public static final String DEFAULT_LANG_ID_KEY = "defaultLangId";
    @Autowired
    private TextComponentLanguageService textComponentLanguageService;

    /**
     * Configure token path.
     *
     * @param requestDefinitionService the request definition service
     */
    @Autowired(required = false)
    public void configureTokenPath(RequestDefinitionService requestDefinitionService) {


        requestDefinitionService.registerTokenFreePath(TextComponentConstants.FULL_PATH);

    }

    /**
     * Globals configuration.
     *
     * @param globalsService the globals service
     */
    @Autowired(required = false)
    public void globalsConfiguration(GlobalsService globalsService) {


        // Check if the default language is existant
        TextComponentLanguage textComponentLanguage = textComponentLanguageService.findOneByKeyValue(TextComponentLanguageService.PROP_NAME_IDENTIFIER, TextComponentLanguage.DEFAULT_LANGUAGE);
        if (textComponentLanguage == null) {

            textComponentLanguage = textComponentLanguageService.createNewDefault();
            textComponentLanguage.setLanguageKey(TextComponentLanguage.DEFAULT_LANGUAGE);
            textComponentLanguage.setLanguageName(TextComponentLanguage.DEFAULT_LANGUAGE_NAME);
            textComponentLanguageService.save(textComponentLanguage);
        }
        if (globalsService != null) {
            globalsService.add(DEFAULT_LANG_KEY, textComponentLanguage.getLanguageKey());
            globalsService.add(DEFAULT_LANG_ID_KEY, textComponentLanguage.getId());
        }


                       /*                                           globalsService.


        mediaTypeService.registerMediaTypeDeterminer(new FileTypePicture());
                         */

    }

}
