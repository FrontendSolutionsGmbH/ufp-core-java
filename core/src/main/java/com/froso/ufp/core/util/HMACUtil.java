package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.nio.charset.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.slf4j.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 06.03.14 Time: 11:52 To change this template use File | Settings | File
 * Templates.
 */
public final class HMACUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HMACUtil.class);

    /**
     * private constructor...
     */
    private HMACUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Compute hmac.
     *
     * @param baseString the base string
     * @param key        the key
     * @return string string
     */
    public static String computeHmac(String baseString,
                                     String key) {

        String returnString = "";
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
            returnString = bytesToHex(digest);
        } catch (NoSuchAlgorithmException ignored) {
            LOGGER.error(ignored.getMessage(), ignored);
        } catch (InvalidKeyException ignored) {
            LOGGER.error(ignored.getMessage(), ignored);
        } catch (Exception ignored) {
            LOGGER.error(ignored.getMessage(), ignored);
        }
        return returnString;
    }

    /**
     * @param in
     * @return
     */
    private static String bytesToHex(byte[] in) {

        StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
