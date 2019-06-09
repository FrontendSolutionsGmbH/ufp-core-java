package com.froso.ufp.modules.optional.textcomponent.controller;

import com.froso.ufp.modules.optional.textcomponent.model.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TextComponentConstants.FULL_PATH)
@Api(description = "Localisation", tags = TextComponentConstants.PATH_NAME)
public class TextComponentValueController {

    @Autowired
    private TextComponentValueCacheService textComponentValueCacheService;

    /**
     * Read elements response entity.
     *
     * @param group    the group
     * @param lang     the language
     * @param response the response
     * @return the response entity
     */
    @ApiOperation(value = "Get all keys for a language group", notes = "Returns all keys with values for the selected language.")
    @RequestMapping(value = "/{group}/{lang}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getTranslation(
            @PathVariable String group, @PathVariable String lang, @ApiParam(hidden = true)
            HttpServletResponse response
    ) {
        response.setContentType("application/json");
        response.addHeader("Content-Type", "application/json");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd-HH-mm");
        String add = "";

        response.addHeader("Content-Disposition", "form-data; name=\"attachment\"; filename=\"" + group + "-" + lang + "-" + fmt.print(DateTime.now()) + add + ".json\"");
        return textComponentValueCacheService.getTranslation(group, lang);

    }
}
