package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * The type Facebook security hub.
 */
public class FacebookSecurityHub {
    private String verify_token;
    private String challenge;
    private String mode;

    /**
     * Gets verify token.
     *
     * @return the verify token
     */
    public String getVerify_token() {
        return verify_token;
    }

    /**
     * Sets verify token.
     *
     * @param verify_token the verify token
     */
    public void setVerify_token(String verify_token) {
        this.verify_token = verify_token;
    }

    /**
     * Gets challenge.
     *
     * @return the challenge
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * Sets challenge.
     *
     * @param challenge the challenge
     */
    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    /**
     * Gets mode.
     *
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets mode.
     *
     * @param mode the mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
