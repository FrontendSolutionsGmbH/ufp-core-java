package com.froso.ufp.modules.optional.textcomponent.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.textcomponent.configuration.*;
import com.froso.ufp.modules.optional.textcomponent.exceptions.*;
import com.froso.ufp.modules.optional.textcomponent.model.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + TextComponent.TYPE_NAME + "/Intl")
@Api(description = "Internationalisation creation/updating endpoints",
        tags = "Intl")
public class AdminIntlTextComponentValueController {
    @Autowired
    private TextComponentValueService textComponentValueService;
    @Autowired
    private TextComponentLanguageService textComponentLanguageService;
    @Autowired
    private TextComponentService textComponentService;
    @Autowired
    private GlobalsService globalsService;

    /**
     * convenience method, to post entry by language key, message key, value (for language)
     *
     * @param userId         the user id
     * @param setRequestData the set request data
     * @param request        the request
     * @return the response entity
     */
    @ApiOperation(value = "create/set language key",
            notes = "Creates a language (default) entry key")
    @RequestMapping(value = "/Entry",
            method = RequestMethod.POST)

    public ResponseEntity<BackendResponseTemplateSingleObject<TextComponentValue>>

    createOrSetIntlKey(
            @PathVariable("token")
                    String userId,
            @RequestBody
                    SetRequestData setRequestData,
            @ApiParam(hidden = true)
                    HttpServletRequest request) {
        TextComponentLanguage textComponentLanguage;
        TextComponentValue textComponentValue;
        TextComponent textComponent;

        ResponseHandlerTemplateSingleObject<TextComponentValue> manager = new ResponseHandlerTemplateSingleObject<>(request);
        // first find id, either by language id, or by short language key, if none found take default language
        textComponentLanguage = textComponentLanguageService.findByIdentifierOrCreateAndReturn(setRequestData.getLanguage());
        if (textComponentLanguage == null) {
            textComponentLanguage = textComponentLanguageService.findOneBrute(setRequestData.getLanguage());
        }
        if (textComponentLanguage == null && setRequestData.getLanguage() == null) {
            textComponentLanguage = textComponentLanguageService.findOneBrute(globalsService.getAllProperties().get(TextComponentModuleConfiguration.DEFAULT_LANG_ID_KEY).toString());
        } else
            // create new language
            if (textComponentLanguage == null) {
                textComponentLanguage = textComponentLanguageService.createNewDefault();
                textComponentLanguage.setLanguageKey(setRequestData.getLanguage());
                textComponentLanguage.getMetaData().getCreatorUserLink().setId(userId);
                textComponentLanguage.getMetaData().getLastEditorUserLink().setId(userId);
                textComponentLanguageService.save(textComponentLanguage);
            }
        // now text component language should be availabel

        if (textComponentLanguage == null) {
            throw new TextComponentExceptions.NoLanguage();
        } else {
            // then find key
            textComponent = textComponentService.getAndCreateIfNotExistant(setRequestData.getKey());
            //textComponent.setGroupName(setRequestData.getGroupName());
            textComponent.setGroupNames(setRequestData.getGroupNames());

            textComponent.setKey(setRequestData.getKey());
            textComponent.getMetaData().getCreatorUserLink().setId(userId);
            textComponent.getMetaData().getLastEditorUserLink().setId(userId);
            textComponentService.save(textComponent);

        }

        if (textComponent == null) {
            //fixme: make dedicated error
            throw new TextComponentExceptions.NoLanguage();
        } else {

            textComponentValue = textComponentValueService.getAndCreateIfNotExistant(textComponent.getId(), textComponentLanguage.getId(), setRequestData.getValue());

        }

        manager.addResult(textComponentValue);
        return manager.getResponseEntity();
    }

    /**
     * convenience method, to remove an intl key
     *
     * @param userId         the user id
     * @param setRequestData the set request data
     * @param request        the request
     * @return the response entity
     */
    @ApiOperation(value = "removes intl key",
            notes = "removes an intl key")
    @RequestMapping(value = "/Entry",
            method = RequestMethod.DELETE)

    public ResponseEntity<BackendResponseTemplateEmpty>

    removeIntlKey(
            @PathVariable("token")
                    String userId,
            @RequestBody
                    SetRequestData setRequestData,
            @ApiParam(hidden = true)
                    HttpServletRequest request) {

        ResponseHandlerTemplateEmpty manager = new ResponseHandlerTemplateEmpty(request);
        TextComponent text = textComponentService.findOneByKeyValue("key", setRequestData.getKey());
        textComponentService.delete(text);
        return manager.getResponseEntity();
    }

}
