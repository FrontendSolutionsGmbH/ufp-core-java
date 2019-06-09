package com.froso.ufp.core.service.util;

import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Created by ckleinhuix on 08.07.2015.
 *
 * small utility pojo to format a centprice input for output templates
 */
public class VelocityDateFormatter {

    public String format(DateTime input) {

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");

        return dtf.print(input);


    }

    public String format(DateTime input, String format) {

        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);

        return dtf.print(input);


    }

}
