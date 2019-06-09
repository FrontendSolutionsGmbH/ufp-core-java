package com.froso.ufp.modules.core.user.exception;

import com.froso.ufp.core.exceptions.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 10:29 To change this
 * template use File | Settings | File Templates.
 *
 * This Exception class collects exceptions that may be thrown from the user-service
 */
public class UserException extends ServiceException {

    /**
     * Constructor CoreUser exception.
     *
     * @param message the message
     */
    public UserException(String message) {

        super(message);
    }

    /**
     * Constructor CoreUser exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserException(String message, Throwable cause) {

        super(message, cause);
    }

    public static class DuplicateUser extends UserException {

        public static final String USER_ALREADY_EXISTANT = "CoreUser Already Existant";

        /**
         * Constructor Duplicate user.
         */
        public DuplicateUser() {

            super(USER_ALREADY_EXISTANT);
        }

        /**
         * Constructor Duplicate user.
         *
         * @param cause the cause
         */
        public DuplicateUser(Throwable cause) {

            super(USER_ALREADY_EXISTANT, cause);
        }
    }

    public static class UserHasActiveOrderAndCanNotBeDeleted extends UserException {

        public static final String USER_HAS_ACTIVE_ORDER = "CoreUser has active orders";

        /**
         * Constructor Duplicate user.
         */
        public UserHasActiveOrderAndCanNotBeDeleted() {

            super(USER_HAS_ACTIVE_ORDER);
        }
    }

    public static class UserNotExistant extends UserException {

        public static final String USER_NOT_EXISTANT = "CoreUser not existant";

        /**
         * Constructor CoreUser not existant.
         */
        public UserNotExistant() {

            super(USER_NOT_EXISTANT);
        }

        /**
         * Constructor CoreUser not existant.
         *
         * @param cause the cause
         */
        public UserNotExistant(Throwable cause) {

            super(USER_NOT_EXISTANT, cause);
        }
    }

    public static class UserInvalidPasswordCombination extends UserException {
        public static final String USER_INVALID_EMAIL_PASSWORD_COMBINATION = "CoreUser invalid Email/Password combination";

        /**
         * Constructor CoreUser invalid auth_email combination.
         *
         * @param data the data
         */
        public UserInvalidPasswordCombination() {

            super(USER_INVALID_EMAIL_PASSWORD_COMBINATION);

        }

        /**
         * Constructor CoreUser invalid auth_email combination.
         *
         * @param data  the data
         * @param cause the cause
         */
        public UserInvalidPasswordCombination(Throwable cause) {

            super(USER_INVALID_EMAIL_PASSWORD_COMBINATION, cause);

        }

    }

    public static class UserInvalidNewPassword extends UserException {

        public static final String USER_INVALID_NEW_PASSWORD = "CoreUser invalid new Password";

        /**
         * Constructor CoreUser invalid new auth_email.
         *
         * @param cause the cause
         */
        public UserInvalidNewPassword(Throwable cause) {

            super(USER_INVALID_NEW_PASSWORD, cause);
        }

        /**
         * Constructor CoreUser invalid new auth_email.
         */
        public UserInvalidNewPassword() {

            super(USER_INVALID_NEW_PASSWORD);
        }
    }

    public static class UserInvalidPassword extends UserException {

        public static final String USER_INVALID_PASSWORD = "CoreUser invalid Password";

        /**
         * Constructor CoreUser invalid auth_email.
         */
        public UserInvalidPassword() {

            super(USER_INVALID_PASSWORD);
        }

        /**
         * Constructor CoreUser invalid auth_email.
         *
         * @param cause the cause
         */
        public UserInvalidPassword(Throwable cause) {

            super(USER_INVALID_PASSWORD, cause);
        }
    }

    public static class UserChangePasswordPasswordsNotMatching extends UserException {

        public static final String USER_CHANGE_PASSWORD_NEW_PASSWORD_AND_NEW_PASSWORD_VERIFY_DO_NOT_MATCH = "CoreUser " +
                "Change " +
                "Password, newPassword and " +
                "newPasswordVerify do not match!";

        /**
         * Constructor CoreUser change auth_email passwords not matching.
         */
        public UserChangePasswordPasswordsNotMatching() {

            super(USER_CHANGE_PASSWORD_NEW_PASSWORD_AND_NEW_PASSWORD_VERIFY_DO_NOT_MATCH);
        }
    }

    public static class UserChangePasswordNewPwRequired extends UserException {

        public static final String USER_CHANGE_PASSWORD_NEW_PASSWORD_MUST_BE_DIFFERENT = "CoreUser Change Password, " +
                "newPassword must be " +
                "different!";

        /**
         * Constructor CoreUser change auth_email new pw required.
         */
        public UserChangePasswordNewPwRequired() {

            super(USER_CHANGE_PASSWORD_NEW_PASSWORD_MUST_BE_DIFFERENT);
        }
    }

    public static class UserNotActive extends UserException {

        public static final String USER_REQUIRES_ACTIVATION_PLEASE_CHECK_EMAIL = "CoreUser requires activation, please " + "check email!";

        /**
         * Constructor CoreUser not active.
         */
        public UserNotActive() {

            super(USER_REQUIRES_ACTIVATION_PLEASE_CHECK_EMAIL);
        }
    }

    public static class UserBlocked extends UserException {

        public static final String USER_IS_CURRENTLY_BLOCKED = "CoreUser is Currently Blocked!";

        /**
         * Constructor CoreUser blocked.
         */
        public UserBlocked() {

            super(USER_IS_CURRENTLY_BLOCKED);
        }
    }
}
