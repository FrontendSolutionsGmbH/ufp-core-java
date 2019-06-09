package com.froso.ufp.modules.optional.textcomponent.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import io.swagger.annotations.*;
import java.io.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + TextComponent.TYPE_NAME)
@Api(description = "Crud Admin Repository", tags = TextComponent.TYPE_NAME)
public class AdminTextComponentController
        extends BaseRepositoryController<TextComponent> {


    /**
     * Globals output.
     *
     * @param request the request
     * @return the response entity
     * @throws IOException the io exception

     @RequestMapping(value = "/LoadDefaults",
     method = RequestMethod.POST)
     @ResponseBody public ResponseEntity<BackendResponse> getGlobals(HttpServletRequest request) throws
     IOException {

     // Get Identifier of default language
     SimpleTextComponentLanguage language = languageService.findByIdentifier(SimpleTextComponentLanguage.DEFAULT_LANGUAGE);
     if (language == null) {
     // create new default if not existantr
     language = languageService.createNewDefault();
     }

     languageService.importDefault();
     textComponentService.importDefault();
     valueService.importDefault();


     // Create Parent TextComponent Entry for all Enums
     SimpleTextComponent textComponentEnums = textComponentService.getAndCreateIfNotExistant("enums");
     // Fill all enum key values into the language table, first create categories, then translations (by default just the values of the enums)
     List<EnumDescription> enums = new ArrayList<>();// FIXME:  EnumController.getEnumDescriptions();
     for (EnumDescription enumDescription : enums) {
     // Create TextComponent Entries for the groups and values
     SimpleTextComponent textComponent = textComponentService.getAndCreateIfNotExistant(enumDescription.getName(), textComponentEnums.getId());


     for (String value : enumDescription.getValues()) {
     // Create TextComponent Entries for the groups and values
     SimpleTextComponent textComponentEntry = textComponentService.getAndCreateIfNotExistant(value, textComponent.getId());

     // And create translation value entry
     SimpleTextComponentValue textValue = valueService.getAndCreateIfNotExistant(textComponentEntry.getId(), language.getId());
     textValue.setValue(language.getIdentifier() + ":" + value);
     valueService.save(textValue);

     }

     }


     ResponseHandler responseHandler = new ResponseHandler(request);
     return responseHandler.getResponseEntity();
     }           */

}
