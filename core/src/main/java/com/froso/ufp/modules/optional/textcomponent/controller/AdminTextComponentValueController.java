package com.froso.ufp.modules.optional.textcomponent.controller;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.textcomponent.model.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import io.swagger.annotations.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + TextComponentValue.TYPE_NAME)
@Api(description = "internationalisation utility endpoints",
        tags = TextComponentValue.TYPE_NAME)
public class AdminTextComponentValueController extends BaseRepositoryController<TextComponentValue> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTextComponentValueController.class);
    @Autowired
    private TextComponentService textComponentService;

    @Autowired
    private TextComponentValueService textComponentValueService;
    @Autowired
    private TextComponentLanguageService textComponentLanguageService;

    /**
     * Upload key value intl response entity.
     *
     * @param userId   the user id
     * @param file     the file
     * @param lang     the language
     * @param request  the request
     * @param response the response
     * @return the response entity
     * @throws IOException the io exception
     */
    @RequestMapping(value = "/{lang}/upload", method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateEmpty> uploadKeyValueIntl(
            @PathVariable("token") String userId,
            @RequestParam("file") final MultipartFile file,
            @PathVariable("lang") final String lang,
            @ApiParam(hidden = true, required = false) MultipartHttpServletRequest request,
            HttpServletResponse response) throws IOException {
        //  handle file here
//        String filenameID = AbstractFileServiceFTP.createIDFromString(file.getOriginalFilename());

        StringWriter writer = new StringWriter();
        IOUtils.copy(file.getInputStream(), writer);
        String theString = writer.toString();
        KeyValueMap ob = new ObjectMapper().readValue(theString, KeyValueMap.class);

        TextComponentLanguage language = textComponentLanguageService.findByIdentifierOrCreateAndReturn(lang);


        for (Map.Entry<String, String> entry : ob.entrySet()) {
            try {
                TextComponent textComponent = textComponentService.getAndCreateIfNotExistant(entry.getKey());
                // find corresponding language and key value


                textComponentValueService.getAndCreateIfNotExistant(textComponent.getId(), language.getId(), entry.getValue());
            } catch (Exception e) {
                LOGGER.error("working at " + entry.getKey() + entry.getValue());
                LOGGER.error("admin textcomponentvalue controller error:",e);
            }

            // ...
        }
        ResponseHandlerTemplateEmpty manager = new ResponseHandlerTemplateEmpty(request);
        //  manager.addResult(element);
        // manager.addMessage("File Received, uploading and processing");
        return manager.getResponseEntity();
    }
}
