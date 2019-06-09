package com.froso.ufp.modules.optional.authenticate.authenticatesms.exceptions;

import com.froso.ufp.core.response.result.*;
import com.google.common.collect.*;
import org.joda.time.*;

/**
 * Created by ck on 22.06.2016.
 */
public class SMSAuthenticateErrors {

    public static final String SMS_NONCE_REQUIRED = "SMS_NONCE_REQUIRED";


    public static final UFPResult UFPRESULT_NONCE_REQUIRED = new UFPResult(SMS_NONCE_REQUIRED, 101, "A nonce is required to complete the process", new LocalDate("2016-06-23"));

    public final ImmutableList<UFPResult> results = ImmutableList.of(
            UFPRESULT_NONCE_REQUIRED,
            new UFPResult("OK", 2, "2", new LocalDate())
    );
}
