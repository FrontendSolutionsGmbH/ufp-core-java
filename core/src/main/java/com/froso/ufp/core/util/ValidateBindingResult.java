package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import org.springframework.validation.*;

/**
 * Created by ckleinhuix on 28/08/2016.
 */
public class ValidateBindingResult {


    /**
     * Validate.
     *
     * @param bindingResult the binding result
     */
    public static void validate(BindingResult bindingResult) {

        if (bindingResult.getAllErrors().size() > 0) {
            throw new ValidationException2.ValidationException(bindingResult);
        }
    }
}
