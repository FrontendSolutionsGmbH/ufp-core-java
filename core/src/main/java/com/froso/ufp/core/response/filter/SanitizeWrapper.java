package com.froso.ufp.core.response.filter;

import javax.servlet.http.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
public class SanitizeWrapper extends HttpServletRequestWrapper {


    public SanitizeWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public String getParameter(String name) {

        /**
         * sanitize request before it is handed down the chain ...
         */


        String result = super.getParameter(name);
        if (result != null) {
            //result = result.replace("a", "HALLO1");
        }
        return result;
    }

}
