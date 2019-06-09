package com.froso.ufp.core.exceptions;

/**
 * Created with IntelliJ IDEA.
 * Entiteit: ck
 * Date: 06.03.14
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class HMACException extends UFPRuntimeException {

    private final String hmacIn;
    private final String hmacCode;

    /**
     * Constructor HMAC exception.
     *
     * @param message           the message
     * @param hmacInput         the hmac input
     * @param hmacGeneratedCode the hmac generated code
     */
    public HMACException(String message, String hmacInput, String hmacGeneratedCode) {

        super(message);
        hmacIn = hmacInput;
        hmacCode = hmacGeneratedCode;
    }

    /**
     * Gets hmac in.
     *
     * @return the hmac in
     */
    public String getHmacIn() {

        return hmacIn;
    }

    /**
     * Gets hmac code.
     *
     * @return the hmac code
     */
    public String getHmacCode() {

        return hmacCode;
    }
}
