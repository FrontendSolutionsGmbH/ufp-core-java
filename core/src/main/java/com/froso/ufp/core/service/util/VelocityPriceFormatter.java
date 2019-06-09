package com.froso.ufp.core.service.util;

/**
 * Created by ckleinhuix on 08.07.2015.
 *
 * small utility pojo to format a centprice input for output templates
 */
public class VelocityPriceFormatter {

    public String format(Integer input) {
        if (input == null) return "null";
        return String.format("%4.2f", input / 100.0f);


    }

}
