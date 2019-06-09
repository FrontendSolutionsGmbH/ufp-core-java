package com.froso.ufp.modules.core.user.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.response.SwaggerDocSnippets;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import io.swagger.annotations.*;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/Account")
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = CoreUser.TYPE_NAME)
class AdminAccountController {


    /**
     * The Service.
     */
    @Autowired
    protected ICoreUserService coreUserService;


    /**
     * Read element response entity.
     *
     * @param userId  the user id
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "admin account (CoreUser)",
            notes = "Returns the current element from the database." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_READ_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = StringUtils.EMPTY,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<CoreUser>> readElement(
            @PathVariable("token")
                    String userId, HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject manager = new ResponseHandlerTemplateSingleObject<>(request);

        IDataDocument simpleObject = coreUserService.findOneBrute(userId);

        // Put Transformed JsonMap Output into result
        manager.addResult(simpleObject);

        return manager.getResponseEntity();
    }

    /**
     * store a json input element in the repository
     * <p>
     * if incoming object.id == null it is saved as copy!
     *
     * @param userId       the user id
     * @param dataInParsed the data in parsed
     * @param request      the request
     * @return response entity
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     */
    @ApiOperation(value = "updates the admin account",
            notes = "Stores the send data in database for current admin user." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_UPDATE_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = StringUtils.EMPTY,
            method = RequestMethod.POST)

    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<ICoreUser>> updateElement(
            @PathVariable("token")
                    String userId,
            @RequestBody(required = true)
                    Map<String, Object> dataInParsed,
            HttpServletRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ResponseHandlerTemplateSingleObject<ICoreUser> manager = new ResponseHandlerTemplateSingleObject<>(request);
        // check for default object

        // remove existing element
        IDataDocument original = coreUserService.findOne(userId);
        dataInParsed.remove("metaData");
        dataInParsed.remove("id");
        dataInParsed.remove("blocked");
        dataInParsed.remove("active");
        PropertyUtils.copyProperties(original, dataInParsed);
        original = coreUserService.save(original);
        manager.addResult((ICoreUser) original);


        return manager.getResponseEntity();
    }


}
