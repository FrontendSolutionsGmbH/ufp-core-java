package com.froso.ufp.modules.optional.theme.service;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.theme.model.*;
import java.util.*;
import org.springframework.stereotype.*;

@Service
public class ThemeService extends AbstractClientRefService<ThemeModel> {

    private static Map<String, String> getColorDefaults() {
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("ufp-color-light", "#fff");
        myMap.put("ufp-color-primary", "#3185ff");
        myMap.put("ufp-color-secondary", "#fff");
        myMap.put("ufp-color-secondary-contrast", "#3185ff");
        myMap.put("ufp-color-transparent", "transparent");
        myMap.put("ufp-color-background-top-right", "#144c9c");
        myMap.put("ufp-color-background-center", "#3c5096");
        myMap.put("ufp-color-background-bottom-left", "#202d5e");
        myMap.put("ufp-color-primary-box-background", "#fff");
        myMap.put("ufp-color-secondary-box-background", "rgba(41,37,75,0.85)");
        myMap.put("ufp-color-separator-shadow", "rgba(0,0,0,0.1)");
        myMap.put("ufp-color-button-shadow", "rgba(0,0,0,0.25)");
        myMap.put("ufp-color-bright-border", "rgba(255,255,255,0.5)");
        myMap.put("ufp-color-dark-border", "rgba(0,0,0,0.5)");
        myMap.put("ufp-color-table-border", "#dcdcdc");
        myMap.put("ufp-color-nav-topic-border", "rgba(255,255,255,0.25)");
        myMap.put("ufp-color-popup-background", "rgba(0,0,0,0.618)");
        myMap.put("ufp-color-radio-checkbox-back", "#323232");
        myMap.put("ufp-color-fieldset-back", "rgba(0,0,0,0.05)");
        myMap.put("ufp-color-fieldset-border", "rgba(0,0,0,0.25)");
        myMap.put("ufp-color-fieldset-border-bright", "rgba(255,255,255,0.25)");
        myMap.put("ufp-color-dark-text", "#000");
        myMap.put("ufp-color-light-text", "#fff");
        myMap.put("ufp-color-even-table-back", "#f4f4f4");
        myMap.put("ufp-color-alert", "#ec6144");
        myMap.put("ufp-color-success", "#13ca20");

        return myMap;
    }

    @Override
    public ThemeModel createNewDefault() {

        ThemeModel result = super.createNewDefault();
        //   save(result)");


        result.getProperties().add(new KeyValueItem("ufp-background-angle", "45deg"));

        Map<String, String> defaults = getColorDefaults();
        for (Map.Entry<String, String> entry : defaults.entrySet()) {

            KeyValueItemTyped<UfpColor> color = new KeyValueItemTyped<>(entry.getKey(), new UfpColor(entry.getValue()));

            result.getColors().add(color);
        }

        return result;
    }


}
