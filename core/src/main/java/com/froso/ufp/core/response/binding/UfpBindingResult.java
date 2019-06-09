package com.froso.ufp.core.response.binding;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: ck
 * Date: 11.12.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class UfpBindingResult
        implements IDataObject {

    private List<UfpBindingResultItem> bindingResults = new ArrayList<>();

    /**
     * Gets binding results.
     *
     * @return the binding results
     */
    public List<UfpBindingResultItem> getBindingResults() {

        return bindingResults;
    }

    /**
     * Sets binding results.
     *
     * @param bindingResults the binding results
     */
    public void setBindingResults(List<UfpBindingResultItem> bindingResults) {

        this.bindingResults = bindingResults;
    }
}
