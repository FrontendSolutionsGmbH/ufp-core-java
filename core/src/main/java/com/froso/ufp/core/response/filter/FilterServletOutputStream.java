package com.froso.ufp.core.response.filter;

import java.io.*;
import javax.servlet.*;

/**
 * The type Filter servlet output stream.
 */
public class FilterServletOutputStream extends ServletOutputStream {

    private DataOutputStream output;

    /**
     * Instantiates a new Filter servlet output stream.
     *
     * @param output the output
     */
    public FilterServletOutputStream(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    @Override
    public void write(int arg0) throws IOException {
        output.write(arg0);
    }

    @Override
    public void write(byte[] arg0, int arg1, int arg2) throws IOException {
        output.write(arg0, arg1, arg2);
    }

    @Override
    public void write(byte[] arg0) throws IOException {
        output.write(arg0);
    }


    public boolean isReady() {
        return true;
    }

    public void setWriteListener(WriteListener listener) {

    }
}
