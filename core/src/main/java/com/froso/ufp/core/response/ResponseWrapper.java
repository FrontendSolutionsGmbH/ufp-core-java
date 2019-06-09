package com.froso.ufp.core.response;

import com.froso.ufp.core.response.filter.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by ckleinhuix on 29.05.2015.
 * <p>
 * super important response wrapper filter, which enables us to add a response header to EVERY response,
 * main problem was the forward() that is executed by the tokentranslators, wrapping getWriter() AND getOutputStream()
 * finally resolved that issue!
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream output;
    private FilterServletOutputStream filterOutput;
    private PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (filterOutput == null) {
            filterOutput = new FilterServletOutputStream(output);
        }
        return filterOutput;
    }

    @Override
    public PrintWriter getWriter() {
        if (writer == null) {
            writer = new PrintWriter(output);
        }
        return writer;
    }

    public byte[] getDataStream() {
        return output.toByteArray();
    }
}
