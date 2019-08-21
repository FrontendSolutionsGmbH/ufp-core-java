package com.froso.ufp.modules.core.user.service;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.util.*;
import org.apache.commons.lang3.*;
import org.joda.time.*;
import org.slf4j.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 11:03 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * the userservice handles application users, and gives a method for checking a login of a simpleUser
 */
//@Service
public class CoreUserService
        extends AbstractRepositoryService2<CoreUser> {
    public static final String TOKENIDENTIFIER_TIME = "t";
    public static final String TOKENIDENTIFIER_USERID = "u";
    public static final int ONCE_PER_SESSION_RANDOM_STRING_LENGTH = 16;
    private static final String TOKENIDENTIFIER_RETAIL = "r";
    private static final String TOKENIDENTIFIER_ROLE = "ro";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreUserService.class);
    private static final Object safer = new Object();
    private static String oncePerSessionRandomKey;

    public CoreUserService() {

        super(CoreUser.TYPE_NAME);

        // define fields to be used in "contains" search
        containsDefinition.add("firstName");
        containsDefinition.add("lastName");

    }

    /**
     * Gets encrypted coreUser id.
     *
     * @param coreUser the coreUser
     * @return the encrypted coreUser id
     * @throws Exception the exception
     */
    public static String getEncryptedUserId(ICoreUser coreUser) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TOKENIDENTIFIER_RETAIL);
        stringBuffer.append("?");
        stringBuffer.append(TOKENIDENTIFIER_TIME);
        stringBuffer.append("=");
        stringBuffer.append(Encryptor.dateToEncryptionDateString(DateTime.now()));
        stringBuffer.append("&");
        stringBuffer.append(TOKENIDENTIFIER_USERID);
        stringBuffer.append("=");
        stringBuffer.append(coreUser.getId());
        stringBuffer.append("&");
        stringBuffer.append(TOKENIDENTIFIER_ROLE);
//        stringBuffer.append("=");
//        stringBuffer.append(coreUser.getRole());
        return Encryptor.enrypt(stringBuffer.toString(), getConstantOncePerSession128BitSecretKey());
    }

    /**
     * Gets constant once per session 128 bit secret key.
     *
     * @return the constant once per session 128 bit secret key
     */
    public static String getConstantOncePerSession128BitSecretKey() {

        synchronized (safer) {
            if (null == CoreUserService.oncePerSessionRandomKey) {
                // Create a nice Key once per session ...
                //
                // !!WARNING!! MIGHT IMPROVE THROUGH GENTLE RESET AFTER TIMED INTERVALSE
                // this implementation should be restarted from time to time ;)
                CoreUserService.oncePerSessionRandomKey = RandomStringUtils.randomAlphanumeric
                        (ONCE_PER_SESSION_RANDOM_STRING_LENGTH);
            }
        }
        return CoreUserService.oncePerSessionRandomKey;
    }

    /**
     * Gets active user only by email.
     *
     * @param email the email
     * @return active user only by email
     * @throws UserException.UserNotActive
     */
    public CoreUser getActiveUserOnlyByEmail(String email) {

        CoreUser coreUser = findUserByEmailWithValidation(email);
        UserValidator.validateUser(coreUser);
        return coreUser;
    }

    /**
     * Find user by email with validation user.
     *
     * @param email the email
     * @return the user
     */
    public CoreUser findUserByEmailWithValidation(String email) {

        CoreUser result = findOneByKeyValue("email", email);
        if (null == result) {
            throw new MainResourceNotFoundException(typeName, email);
        }

        UserValidator.validateUser(result);

        return result;
    }

    /**
     * Gets user by email without validation.
     *
     * @param email the email
     * @return the user by email without validation
     */
    public CoreUser getUserByEmailWithoutValidation(String email) {

        return findOneByKeyValue("email", email);
    }

    /**
     * Gets active user only by id.
     *
     * @param id the id
     * @return active user only by id
     * @throws UserException.UserNotActive
     */
    public CoreUser getActiveUserOnlyById(String id) {

        CoreUser coreUser = findOne(id);
        UserValidator.validateUser(coreUser);
        return coreUser;
    }

    @Override
    public CoreUser findOne(String id) {

        CoreUser result = null;
        if (null != id) {
            result = super.findOne(id);
            UserValidator.validateUser(result);
        }
        return result;
    }

    /**
     * Find one and return user with tokenized id.
     *
     * @param id the id
     * @return the simple user
     */
    private CoreUser findOneAndReturnUserWithoutId(String id) {

        CoreUser result = findOne(id);
        // so, if we went down here the user is "logged in"
        // we return a simpleUser tokenID !!! VERY IMPORTANT ;)
        try {
            result.setId(null);
        } catch (Exception e) {
            LOGGER.error("SimpleUserService.findOneAndReturnUserWithoutId() Exception:", e);
            result = null;
        }
        return result;
    }

    /**
     * Find one with out validation.
     *
     * @param id the id
     * @return the simple user
     */
    public CoreUser findOneWithOutValidation(String id) {

        CoreUser result = null;
        if (null != id) {
            result = super.findOne(id);
        }
        return result;
    }

    /**
     * Prepare duplication.
     *
     * @param object the object
     */
    protected void prepareDuplication(CoreUser object) {
        fillDefaultObject(object);
    }

}
