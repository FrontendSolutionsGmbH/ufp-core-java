package com.froso.ufp.modules.optional.clickatelltest.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.clickatelltest.model.*;
import com.froso.ufp.modules.optional.smsprovider.clickatellsmsprovider.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

// deprecated. use sms queue and propty setting for testing functionality @Controller

@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/Clickatell")
@Api(description = "Test Services", tags = "Clickatell")
class ClickatellController {

    public static final String CLICKATELL_AUTHTOKEN = "ufp.modules.sms.provider.clickatell.authtoken";
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickatellController.class);

    @Autowired
    private IPropertyService propertyService;


    /**
     * Gets all Elements from the repository
     *
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "Test SmsProvider Clickatell",
            notes = "<p>This method returns the external message status for a messageId returned by sendTestSmsMessage in the field externalMessageId</p>",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = "/status/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<ClickatellStatusResponse>> getClickatellStatus(@PathVariable String token, @PathVariable String messageId, HttpServletRequest request) {
        ResponseHandlerTemplateSingleObject<ClickatellStatusResponse> manager = new ResponseHandlerTemplateSingleObject<>(request);
        ClickatellRest smsService = new ClickatellRest(propertyService.getPropertyValue(CLICKATELL_AUTHTOKEN));
        ClickatellStatusResponse response = new ClickatellStatusResponse();
        ClickatellRest.Message msg = null;

        try {
            msg = smsService.getMessageStatus(messageId);
            response.setCharge(msg.charge);
            response.setMessageId(msg.message_id);
            response.setStatus(msg.status);
            response.setStatusString(msg.statusString);
            manager.addResult(response);
        } catch (Exception e) {

            LOGGER.error("clickatell", e);
            throw new ResourceException.ResourceNotAvailable("Invalid messageId", "Clickatell", "Clickatell", e);
            //manager.addResult(response);
        }
        return manager.getResponseEntity();

    }

}
