package com.froso.ufp.modules.optional.ftp.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.ftp.model.*;
import com.froso.ufp.modules.optional.ftp.service.*;
import io.swagger.annotations.*;
import java.io.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

/**
 * Created by alex on 20.11.14.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + FTPSetting.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = FTPSetting.TYPE_NAME)
class AdminFtpSettingController extends BaseRepositoryController<FTPSetting> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminFtpSettingController.class);
    private FTPService ftpService;

    /**
     * Constructor Admin keeper controller.
     *
     * @param service the simple service
     */
    @Autowired
    public AdminFtpSettingController(FTPService service) {

        ftpService = service;
    }

    /**
     * Read element response entity.
     *
     * @param userId    the user id
     * @param elementId the element id
     * @param request   the request
     * @return the response entity
     */
    @ApiOperation(value = "Access a file directly")
    @RequestMapping(value = "/{elementId}/**/*.*", method = RequestMethod.GET)
    @ResponseBody
    public String readElementDirectly(@PathVariable String userId, @PathVariable("elementId") String elementId, HttpServletRequest request) {


        return "CONTENT";
    }

    /**
     * get a single element from the repository
     *
     * @param userId    the user id
     * @param elementId the element id
     * @param request   the request
     * @return element element
     * @throws IOException the io exception
     */
    @ApiOperation(value = "Browser FTP Directory")
    @RequestMapping(value = "/{elementId}/browse/**", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> browseFTP(@PathVariable String userId, @PathVariable("elementId") String elementId, HttpServletRequest request) throws IOException {

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
        //  ResponseHandler.setDefaultHeaders(response);
        String thepath = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        LOGGER.debug("Page Requested with name:" + thepath);
        String requestFile = thepath.substring(thepath.indexOf(elementId) + 8 + elementId.length());
        if ("".equals(requestFile)) {
            requestFile = ".";
        }
        manager.addResult(ftpService.getFtpDirList(elementId, requestFile));
        return manager.getResponseEntity();
    }


}
