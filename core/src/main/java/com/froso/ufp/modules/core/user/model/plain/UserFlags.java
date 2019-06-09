package com.froso.ufp.modules.core.user.model.plain;

import com.froso.ufp.core.domain.documents.simple.validation.*;
import io.swagger.annotations.*;
import java.io.*;
import javax.validation.constraints.*;

/**
 * @author c.Kleinhuis
 *
 *         Terms and condition Flags stored to a user account. privacy and termsandconditions need to be accepted
 */
public class UserFlags implements Serializable {

    @NotNull(message = ValidationMessages.NOT_NULL)
    @AssertTrue(message = ValidationMessages.HAS_TO_BE_TRUE)
    @ApiModelProperty(example = "true")
    private Boolean privacyAcceptance = true;


    @NotNull(message = ValidationMessages.NOT_NULL)
    @AssertTrue(message = ValidationMessages.HAS_TO_BE_TRUE)
    @ApiModelProperty(example = "true")
    private Boolean termsAndConditions = true;


    @NotNull(message = ValidationMessages.NOT_NULL)
    @ApiModelProperty(example = "true")
    private Boolean marketingContact = false;

    /**
     * Gets privacy acceptance.
     *
     * @return the privacy acceptance
     */
    public Boolean getPrivacyAcceptance() {

        return privacyAcceptance;
    }

    /**
     * Sets privacy acceptance.
     *
     * @param privacyAcceptAnce the privacy accept ance
     */
    public void setPrivacyAcceptance(Boolean privacyAcceptAnce) {

        this.privacyAcceptance = privacyAcceptAnce;
    }

    /**
     * Gets terms and conditions.
     *
     * @return the terms and conditions
     */
    public Boolean getTermsAndConditions() {

        return termsAndConditions;
    }

    /**
     * Sets terms and conditions.
     *
     * @param termsAndConditions the terms and conditions
     */
    public void setTermsAndConditions(Boolean termsAndConditions) {

        this.termsAndConditions = termsAndConditions;
    }

    /**
     * Gets marketing contact.
     *
     * @return the marketing contact
     */
    public Boolean getMarketingContact() {

        return marketingContact;
    }

    /**
     * Sets marketing contact.
     *
     * @param marketingContact the marketing contact
     */
    public void setMarketingContact(Boolean marketingContact) {

        this.marketingContact = marketingContact;
    }
}
