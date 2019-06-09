package com.froso.ufp.modules.core.user.service;

import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.session.interfaces.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;
import org.apache.commons.lang3.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 11:03 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * the userservice handles application users, and gives a method for checking a login of a simpleUser
 */
@Service
public class CoreUserHelperService {
    public static final String TOKENIDENTIFIER_TIME = "t";
    public static final String TOKENIDENTIFIER_USERID = "u";
    public static final int ONCE_PER_SESSION_RANDOM_STRING_LENGTH = 16;
    private static final String TOKENIDENTIFIER_RETAIL = "r";
    private static final String TOKENIDENTIFIER_ROLE = "ro";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreUserHelperService.class);
    private static final Object safer = new Object();
    private static String oncePerSessionRandomKey;
    private final IPropertyService propertyService;
    private final ISessionService sessionService;
    private final ICoreUserService<ICoreUser> coreUserService;

    @Autowired
    public CoreUserHelperService(IPropertyService propertyService, ISessionService sessionService, ICoreUserService<ICoreUser> coreUserService) {
        this.propertyService = propertyService;
        this.sessionService = sessionService;
        this.coreUserService = coreUserService;
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
        stringBuffer.append("=");
        stringBuffer.append(coreUser.getRole());
        return Encryptor.enrypt(stringBuffer.toString(), getConstantOncePerSession128BitSecretKey());
    }

    /**
     * Gets constant once per session 128 bit secret key.
     *
     * @return the constant once per session 128 bit secret key
     */
    public static String getConstantOncePerSession128BitSecretKey() {

        synchronized (safer) {
            if (null == CoreUserHelperService.oncePerSessionRandomKey) {
                // Create a nice Key once per session ...
                //
                // !!WARNING!! MIGHT IMPROVE THROUGH GENTLE RESET AFTER TIMED INTERVALSE
                // this implementation should be restarted from time to time ;)
                CoreUserHelperService.oncePerSessionRandomKey = RandomStringUtils.randomAlphanumeric
                        (ONCE_PER_SESSION_RANDOM_STRING_LENGTH);
            }
        }
        return CoreUserHelperService.oncePerSessionRandomKey;
    }

    /**
     * Creates a SimpleUser in the Database, if a simpleUser with the email/name already exists a mongo exception is
     * catched and transformed into our own UserException
     *
     * @param coreUser a valid simpleUser objec
     * @return the created simpleUser in the database
     * @throws UserException the coreUser exception
     */
    public ICoreUser createUser(ICoreUser coreUser) throws
            UserException {
        if (coreUser == null) {
            coreUser = coreUserService.createNewDefault();
            coreUser.setFirstName("defaultFirstName");
            coreUser.setLastName("defaultLastName");
        }
        ICoreUser result = null;
        /*  if (!isValidNewPassword(coreUser.getUsername())) {
            throw new UserException.UserInvalidPassword();
        }*/
        try {
            // Create Encrypted Password
            //    String origPassword = coreUser.getUsername();
            //   String encrPassword = getTransformedAndEncryptedPassword(origPassword);
            //    coreUser.setUsername(encrPassword);
            coreUser.setActive(Boolean.TRUE);
            //    simpleUser.setMobilePhoneNumber(UFPPhoneNumberUtil.rewritePhoneNumber(simpleUser.getMobilePhoneNumber()));
            // Set Default CoreUser-Role
//            coreUser.setRole(UserRoleEnum.ROLE_ADMIN);
            result = coreUserService.save(coreUser);
        } catch (DuplicateKeyException ignored) {
            throw new UserException.DuplicateUser(ignored);
        } catch (Exception exception) {
            LOGGER.error("Create SimpleUser Error" + exception.getMessage(), exception);
            throw new UserException(exception.getMessage(), exception);
        }
        return result;
    }

    /**
     * this methed exchanges the id of a coreUser to an encrypted string in the following format:
     * <p>
     * retail:userid:{id]:time:{datetime}}
     *
     * @param coreUser the core user
     */
    public void transformUserToCryptonizedID(ICoreUser coreUser) {

        LOGGER.info("transformUserToCryptonizedID (create session token)" + coreUser.getId());
        // delete ALL existant sessions from coreUser
        LOGGER.info("deleting active sessions )" + coreUser.getId());
        sessionService.setAllSessionsFromUserToInactive(coreUser);

        // And create new session
        Session session = sessionService.getDefault();
        session.getUserLink().setId(coreUser.getId());

        //   session.setDeviceCient(RequestHeaderRetriever.getCurrentClient());

        LOGGER.info("creating new session sessionduration is " + propertyService.getPropertyValue(Session.PROP_NAME_SESSION_DURATION));
        LOGGER.info("creating new session sessionduration is (integer) " + Integer.parseInt(propertyService.getPropertyValue(Session.PROP_NAME_SESSION_DURATION)));
        LOGGER.info("creating new session creationtime is  " + session.getCreationTime());
        session.setEndsOn(session.getCreationTime().plusMillis(Integer.parseInt(propertyService.getPropertyValue(Session.PROP_NAME_SESSION_DURATION))));
        LOGGER.info("creating new session then ends on: " + session.getEndsOn());
        coreUser.setId(getEncryptedUserId(coreUser));
        session.setToken(coreUser.getId());
        session.setRemoteHost(RequestRemoteHostRetriever.getRemoteHostOfCurrentRequest());
        session.setActive(true);
        sessionService.save(session);
        LOGGER.info("session created  " + session.getId());

    }

}
