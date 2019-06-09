package com.froso.ufp.modules.core.globals.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.globals.model.*;
import com.froso.ufp.modules.core.globals.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping(UFPConstants.API)
@Api(description = "System Information",
        tags = "Globals")
public class GlobalsController {

    public static final String HEADLINE = "**Parameter**|Description\n---|---\n";
    public static final String HOLDS_THE_APPNAME = "**applicationName**| The name of the application.\n";
    public static final String HOLDS_THE_FACEBOOKAPPID = "**facebookAppID**| The ID of the optional " +
            "Facebook application (if there is a Facebook application).\n";
    public static final String HOLDS_THE_DEFAULTLANGID = "**defaultLangID**| The UUID of the default language.\n";
    public static final String HOLDS_THE_DEFAULTCLIENTID = "**defaultClientID**| The default ID of the client.\n";
    public static final String HOLDS_THE_APPVERSION = "**applicationVersion**| The application version.\n";
    public static final String HOLDS_THE_SESSIONDURATION = "**sessionDuration**| The duration a session stays active," +
            " after this period it is no longer usable and a new authorization has to be executed.\n";
    public static final String HOLDS_THE_BUILDTIME = "**applicationBuildTime**| The date of the backend build running right now.\n";
    public static final String AUTH_HOLDS_THE_ENABLED_AUTHORIZATION_METHODS = "**auth[]**| Array that holds the " +
            "enabled authorization methods.\n";
    public static final String REG_HOLDS_THE_ENABLED_REGISTRATION_METHODS = "**reg[]**| Array that holds the enabled " +
            "registration methods.\n";
    public static final String HOLDS_THE_DEFAULTLANG = "**defaultLang**| The ISO 639-1 code of the default language" +
            ".\n";

    private final GlobalsServiceImpl globalsServiceImplImpl;

    @Autowired
    public GlobalsController(GlobalsServiceImpl globalsServiceImplImpl) {
        this.globalsServiceImplImpl = globalsServiceImplImpl;
    }

    @RequestMapping(
            value = "/Globals",
            method = RequestMethod.GET)
    @ApiOperation(
            produces = UFPConstants.APPLICATION_DEFAULT_CONTENT_TYPE_UTF8,
            consumes = UFPConstants.APPLICATION_DEFAULT_CONTENT_TYPE_UTF8,
            value = "configuration of the backend",
            notes = "Shows usual configuration parameters of the backend system.\n\n" +
                    HEADLINE +
                    HOLDS_THE_BUILDTIME +
                    HOLDS_THE_APPNAME +
                    HOLDS_THE_APPVERSION +
                    AUTH_HOLDS_THE_ENABLED_AUTHORIZATION_METHODS +
                    HOLDS_THE_DEFAULTCLIENTID +
                    HOLDS_THE_DEFAULTLANG +
                    HOLDS_THE_DEFAULTLANGID +
                    HOLDS_THE_FACEBOOKAPPID +
                    REG_HOLDS_THE_ENABLED_REGISTRATION_METHODS +
                    HOLDS_THE_SESSIONDURATION
    )

    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<GlobalSettings3>> getGlobals(HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject<GlobalSettings3> responseHandler = new ResponseHandlerTemplateSingleObject<>(request);
        responseHandler.addResult(globalsServiceImplImpl.getGlobals3());

        return responseHandler.getResponseEntity();
    }

}
