package com.froso.ufp.core.domain.validation;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created by ckleinhuix on 28/08/2016.
 */
public class ValidationResults implements IDataObject {
    private List<ValidationResult> validationResults = new ArrayList<>();

    /**
     * Gets validation results.
     *
     * @return the validation results
     */
    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    /**
     * Sets validation results.
     *
     * @param validationResults the validation results
     */
    public void setValidationResults(List<ValidationResult> validationResults) {
        this.validationResults = validationResults;
    }
}
