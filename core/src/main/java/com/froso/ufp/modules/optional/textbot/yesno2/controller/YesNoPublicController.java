package com.froso.ufp.modules.optional.textbot.yesno2.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.util.query.*;
import com.froso.ufp.modules.optional.textbot.yesno2.actions.*;
import com.froso.ufp.modules.optional.textbot.yesno2.model.*;
import com.froso.ufp.modules.optional.textbot.yesno2.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = "the yes-bot public api",
        tags = "YES")
@RequestMapping("/YesNo")
class YesNoPublicController {

    @Autowired
    private CountFor countForAction;
    @Autowired
    private CountAgainst countAgainstAction;

    @Autowired
    private YesNoTextBotService service;

    @Autowired
    private YesNoTextBotConfigService yesNoTextBotConfigService;

    /**
     * Perform distinct response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "current yes-bot value",
            notes = "this method returns the current value which is determined magically intern (latest) ")
    @RequestMapping(value = "/Current",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<YesNoTextBotModel>> getCurrentYesNoStanding(
            HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        ResponseHandlerTemplateSingleObject<YesNoTextBotModel> manager = new ResponseHandlerTemplateSingleObject<>(request);
        Map<String, String> search = new HashMap<>();
        search.put(SearchQuery.SORT_PROPERTY, "-metaData.creationTimestamp");
        YesNoTextBotModel searchresult = service.findOneByKeyValues(search);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * Gets config.
     *
     * @param request the request
     * @return the config
     */
    @ApiOperation(value = "current yes-bot config, enabled/selection ....",
            notes = "the config provides various administrable settings - only through logged in administrator")
    @RequestMapping(value = "/Config",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<YesNoTextBotConfig>> getConfig(
            HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        ResponseHandlerTemplateSingleObject<YesNoTextBotConfig> manager = new ResponseHandlerTemplateSingleObject<>(request);
        YesNoTextBotConfig searchresult = yesNoTextBotConfigService.findAllGetFirst();
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * Gets yes no.
     *
     * @param request the request
     * @return the yes no
     */
    @ApiOperation(value = "all available question in database",
            notes = "this method is used to search/browser through existing questions")
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate<YesNoTextBotModel>> getYesNo(
            HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        ResponseHandlerTemplate<YesNoTextBotModel> manager = new ResponseHandlerTemplate<>(request);


        List<YesNoTextBotModel> elements = service.search(BaseRepositoryController.getSingleParameterValueMapFromRequest(request));
        if (elements.size() > 0) {
            manager.addResult(elements);
        }
        return manager.getResponseEntity();

    }


    /**
     * Vote for response entity.
     *
     * @param yesNoId the yes no id
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "retrieves a specific yes-bot standing",
            notes = "this method returns the current value which is determined magically intern (latest) ")
    @RequestMapping(value = "/{yesNoId}",
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<YesNoTextBotModel>> getStanding(@PathVariable String yesNoId,
                                                                                                    HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        ResponseHandlerTemplateSingleObject<YesNoTextBotModel> manager = new ResponseHandlerTemplateSingleObject<>(request);
        Map<String, String> search = new HashMap<>();
        search.put(SearchQuery.SORT_PROPERTY, "metaData.lastChangedTimeStamp");
        search.put(SearchQuery.SORT_DIRECTION_PROPERTY, SearchQuery.SORT_DESC);
        YesNoTextBotModel searchresult = service.findOne(yesNoId);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * Vote for response entity.
     *
     * @param yesNoId the yes no id
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "vote against a specific yes-bot question",
            notes = "this method returns the current value which is determined magically intern (latest) ")
    @RequestMapping(value = "/{yesNoId}/VoteAgainst",
            method = RequestMethod.DELETE)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<YesNoTextBotModel>> voteAgainst(@PathVariable String yesNoId,
                                                                                                    HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        ResponseHandlerTemplateSingleObject<YesNoTextBotModel> manager = new ResponseHandlerTemplateSingleObject<>(request);
        countAgainstAction.execute(yesNoId);
        Map<String, String> search = new HashMap<>();
        search.put(SearchQuery.SORT_PROPERTY, "metaData.lastChangedTimeStamp");
        YesNoTextBotModel searchresult = service.findOne(yesNoId);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }

    /**
     * Vote against response entity.
     *
     * @param yesNoId the yes no id
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "vote for a specific yes-bot question",
            notes = "this method returns the current value which is determined magically intern (latest) ")
    @RequestMapping(value = "/{yesNoId}/VoteFor",
            method = RequestMethod.POST)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<YesNoTextBotModel>> voteFor(@PathVariable String yesNoId,
                                                                                                HttpServletRequest request) {
        //  Map<String, String> allRequestParams = getSingleParameterValueMapFromRequest(request);
        countForAction.execute(yesNoId);
        ResponseHandlerTemplateSingleObject<YesNoTextBotModel> manager = new ResponseHandlerTemplateSingleObject<>(request);
        Map<String, String> search = new HashMap<>();
        search.put(SearchQuery.SORT_PROPERTY, "metaData.lastChangedTimeStamp");
        YesNoTextBotModel searchresult = service.findOne(yesNoId);
        manager.addResult(searchresult);
        return manager.getResponseEntity();
    }
}
