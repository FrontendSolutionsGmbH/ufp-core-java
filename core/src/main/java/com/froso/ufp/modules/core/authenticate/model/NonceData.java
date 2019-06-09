package com.froso.ufp.modules.core.authenticate.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.*;
import org.joda.time.*;

import java.io.*;

@ApiModel(description = "Nonce Data Structure for Numbers-used-ONCE")
public class NonceData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nonce;
    private DateTime validUntil = DateTime.now();

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getValidUntil() {
        return validUntil;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setValidUntil(DateTime validUntil) {
        this.validUntil = validUntil;
    }

    @JsonIgnore
    public Boolean isNoncePresent() {
        return !((nonce == null) || (StringUtils.EMPTY.equals(nonce)));
    }
}
