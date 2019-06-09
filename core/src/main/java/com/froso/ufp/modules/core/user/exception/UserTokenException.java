package com.froso.ufp.modules.core.user.exception;

/**
 * Created with IntelliJ IDEA.
 * CoreUser: ck
 * Date: 10.03.14
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
public class UserTokenException
        extends RuntimeException {


    public UserTokenException() {

        super();
    }

    public UserTokenException(String message) {

        super(message);
    }

    public static class TokenExpired
            extends UserTokenException {

    }

    public static class InvalidToken
            extends UserTokenException {

    }

    public static class InvalidRole
            extends UserTokenException {

    }

    public static class RemoteHostChanged
            extends UserTokenException {

    }
}
