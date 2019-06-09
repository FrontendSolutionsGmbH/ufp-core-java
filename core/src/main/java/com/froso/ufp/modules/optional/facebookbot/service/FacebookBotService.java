package com.froso.ufp.modules.optional.facebookbot.service;

import com.froso.ufp.core.service.util.query.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import java.util.*;
import javax.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class FacebookBotService extends AbstractClientRefService<ReceivedFacebookBotMessage> {

    private static final String PROP_NAME_FACEBOOK_BOT_CONNECT_TOKEN = "ufp.optional.facebook.bot.connectToken";

    @Autowired
    private IPropertyService propertyService;


    @PostConstruct
    private void activateBot() {

    }

    /**
     * Is text bot mode instant boolean.
     *
     * @return the boolean
     */
    public Boolean isTextBotModeInstant() {
        return "instant".equals(propertyService.getPropertyValue(TextBotConstants.PROPERTY_NAME_TEXTBOT_MODE));


    }

    /**
     * Verify facebook security hub string.
     *
     * @param facebookSecurityHub the facebook security hub
     * @return the string
     */
    public String verifyFacebookSecurityHub(FacebookSecurityHub facebookSecurityHub) {
        String result = "";
        // first verify incoming token is equal to ours
        if (null != facebookSecurityHub && null != facebookSecurityHub.getVerify_token()) {
            if (facebookSecurityHub.getVerify_token().equals(propertyService.getPropertyValue(PROP_NAME_FACEBOOK_BOT_CONNECT_TOKEN))) {
                // token quals, return challenge
                result = facebookSecurityHub.getChallenge();
            }
        }
        return result;

    }

    /**
     * Find all new list.
     *
     * @param pageable the pageable
     * @return the list
     */
    public List<ReceivedFacebookBotMessage> findAllNew(Pageable pageable) {
        Map<String, String> search = new HashMap<>();
        search.put("status", "NEW");
        search.put(SearchQuery.LIMIT, String.valueOf(pageable.getPageSize()));

        return findByKeyValues(search);

    }

}
