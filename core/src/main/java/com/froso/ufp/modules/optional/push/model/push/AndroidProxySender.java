package com.froso.ufp.modules.optional.push.model.push;

import com.google.android.gcm.server.*;
import java.io.*;
import java.net.*;

/**
 * The type Android proxy sender.
 */
public class AndroidProxySender extends Sender {

    private String proxyHostname;
    private Integer proxyPort;

    /**
     * Constructor Android proxy sender.
     *
     * @param key           the key
     * @param proxyHostname the proxy hostname
     * @param proxyPort     the proxy port
     */
    public AndroidProxySender(String key,
                              String proxyHostname,
                              Integer proxyPort) {

        super(key, Endpoint.FCM);
        this.proxyHostname = proxyHostname;
        this.proxyPort = proxyPort;
    }

    /**
     * Constructor Android proxy sender.
     *
     * @param key the key
     */
    public AndroidProxySender(String key) {

        super(key, Endpoint.FCM);
    }

    /**
     * Gets connection.
     *
     * @param url the url
     * @return the connection
     * @throws IOException the iO exception
     */
    @Override
    protected HttpURLConnection getConnection(String url) throws IOException {

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHostname, proxyPort));
        return (HttpURLConnection) new URL(url).openConnection(proxy);
    }
}
