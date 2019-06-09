package com.froso.ufp.modules.optional.push.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.user.model.*;
import javax.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * The type Simple app device.
 */
@Document(collection = AppDevice.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("APPDEVICE")
        }),
        defaultView = @ResourceViewAnnotation(visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("_edit"),
                @ResourceVisibleColumn("id"),
                @ResourceVisibleColumn("appId"),
                @ResourceVisibleColumn("token"),
                @ResourceVisibleColumn("appVersion"),
                @ResourceVisibleColumn("language"),
                @ResourceVisibleColumn("isProduction"),
                @ResourceVisibleColumn("deviceType"),
                @ResourceVisibleColumn("coreUser")
        }))

)
public final class AppDevice extends AbstractDataDocumentWithCoreUserLink {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "AppDevice";

    @NotNull
    private String token = "defaultToken";
    private AppDeviceTypeEnum deviceType = AppDeviceTypeEnum.ANDROID;
    private Boolean isProduction = Boolean.FALSE;
    private String appId;
    private String appVersion;
    private String language;

    /**
     * Constructor Simple app device.
     */
// Constructor
    public AppDevice() {

        super(TYPE_NAME);
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {

        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {

        this.language = language;
    }

    /**
     * Gets is production.
     *
     * @return the is production
     */
    public Boolean getIsProduction() {

        return isProduction;
    }

    /**
     * Sets is production.
     *
     * @param production the production
     */
    public void setIsProduction(Boolean production) {

        isProduction = production;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {

        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {

        this.token = token;
    }

    /**
     * Gets device type.
     *
     * @return the device type
     */
    public AppDeviceTypeEnum getDeviceType() {

        return deviceType;
    }

    /**
     * Sets device type.
     *
     * @param deviceType the device type
     */
    public void setDeviceType(AppDeviceTypeEnum deviceType) {

        this.deviceType = deviceType;
    }

    /**
     * Gets app version.
     *
     * @return the app version
     */
    public String getAppVersion() {

        return appVersion;
    }

    /**
     * Sets app version.
     *
     * @param appVersion the app version
     */
    public void setAppVersion(String appVersion) {

        this.appVersion = appVersion;
    }

    /**
     * Gets app id.
     *
     * @return the app id
     */
    public String getAppId() {

        return appId;
    }

    /**
     * Sets app id.
     *
     * @param appId the app id
     */
    public void setAppId(String appId) {

        this.appId = appId;
    }


}
