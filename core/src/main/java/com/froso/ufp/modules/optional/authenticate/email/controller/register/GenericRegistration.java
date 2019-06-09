package com.froso.ufp.modules.optional.authenticate.email.controller.register;

import io.swagger.annotations.*;
import javax.validation.*;
import javax.validation.constraints.*;

/**
 * Created by ckleinhuis on 18.11.2016.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public class GenericRegistration<T, K> {

    @Valid
    @NotNull
    @ApiModelProperty(required = true, notes = "specific registration data")
    private T authentication;
    @ApiModelProperty(required = true, notes = "authentication data")
    @Valid
    @NotNull
    private K data;

    /**
     * Gets authentication.
     *
     * @return the authentication
     */
    public T getAuthentication() {
        return authentication;
    }

    /**
     * Sets authentication.
     *
     * @param authentication the authentication
     */
    public void setAuthentication(T authentication) {
        this.authentication = authentication;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public K getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(K data) {
        this.data = data;
    }


}
