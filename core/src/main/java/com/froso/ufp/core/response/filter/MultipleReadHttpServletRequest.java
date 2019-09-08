package com.froso.ufp.core.response.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.io.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
class MultipleReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    /**
     * Constructor Multiple read http servlet request.
     *
     * @param request the request
     */
    public MultipleReadHttpServletRequest(HttpServletRequest request) {

        super(request);
    }

    /**
     * Gets input stream.
     *
     * @return the input stream
     * @throws IOException the iO exception
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (null == cachedBytes) {
            cacheInputStream();
        }
        return new CachedServletInputStream();
    }

    private void cacheInputStream() throws IOException {
    /* Cache the inputstream in order to read it multiple times. parallelFor
     * convenience, I use apache.commons IOUtils
     */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /**
     * The type Cached servlet input stream.
     */
/* An inputstream which reads the cached request body */
    public class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream input;

        /**
         * Constructor Cached servlet input stream.
         */
        public CachedServletInputStream() {
      /* create a new input stream from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }

        public boolean isFinished() {
            throw new RuntimeException("Not yet implemented isFinished");
            //return false;
        }

        public boolean isReady() {
            throw new RuntimeException("Not yet implemented isReady");
            //return false;
        }

        public void setReadListener(ReadListener readListener) {

            throw new RuntimeException("Not yet implemented setReadListener");
        }

        /**
         * Read int.
         *
         * @return the int
         */
        @Override
        public int read() {

            return input.read();
        }
    }
}
