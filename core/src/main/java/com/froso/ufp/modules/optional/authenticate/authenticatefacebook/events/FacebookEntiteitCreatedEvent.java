package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.events;

import com.froso.ufp.modules.core.user.model.*;
import facebook4j.*;
import org.springframework.context.*;

/**
 * The type Facebook entiteit created event.
 */
public class FacebookEntiteitCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = -8403774037278230919L;
    private ICoreUser coreUser;
    private User facebookUser;


    /**
     * Instantiates a new Facebook entiteit created event.
     *
     * @param coreUser       the core user
     * @param facebookUserIn the facebook user in
     * @param source         the source
     */
    public FacebookEntiteitCreatedEvent(ICoreUser coreUser, User facebookUserIn, Object source) {
        super(source);
        this.coreUser = coreUser;
        this.facebookUser = facebookUserIn;
    }

    /**
     * Gets core user.
     *
     * @return the core user
     */
    public ICoreUser getCoreUser() {
        return coreUser;
    }

    /**
     * Sets core user.
     *
     * @param coreUser the core user
     */
    public void setCoreUser(ICoreUser coreUser) {
        this.coreUser = coreUser;
    }

    /**
     * Gets facebook user.
     *
     * @return the facebook user
     */
    public User getFacebookUser() {
        return facebookUser;
    }

    /**
     * Sets facebook user.
     *
     * @param facebookUser the facebook user
     */
    public void setFacebookUser(User facebookUser) {
        this.facebookUser = facebookUser;
    }
}
