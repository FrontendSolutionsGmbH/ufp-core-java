package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.nio.charset.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 08.03.14.
 */
public final class Encryptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private Encryptor() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Enrypt string.
     *
     * @param input the input
     * @param key   the key
     * @return string string
     */
    public static String enrypt(String input,
                                String key) {

        return enrypt(input.getBytes(Charset.forName("UTF-8")), key);
    }

    /**
     * @param input
     * @param key
     * @return
     */
    private static String enrypt(byte[] input,
                                 String key) {

        String result = "";
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(input);
            result = new String(encrypted, Charset.forName("UTF-8"));
            result = Base64Util.base32Encode(encrypted);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Decrypt string.
     *
     * @param textIn the text in
     * @param key    the key
     * @return string string
     */
    public static String decrypt(byte[] textIn,
                                 String key) {

        return decrypt(new String(textIn, Charset.forName("UTF-8")), key);
    }

    /**
     * Decrypt string.
     *
     * @param textIn the text in
     * @param key    the key
     * @return string string
     */
    public static String decrypt(String textIn,
                                 String key) {

        byte[] baseDecoded = Base64Util.base32Decode(textIn);
        String result = "";
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            result = new String(cipher.doFinal(baseDecoded), Charset.forName("UTF-8"));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Date to encryption date string.
     *
     * @param date the date
     * @return string string
     */
    public static String dateToEncryptionDateString(DateTime date) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("DDDyyyyHHmmss");
        return fmt.print(date);
    }

    /**
     * Encryption date string to date time.
     *
     * @param date the date
     * @return date time
     */
    public static DateTime encryptionDateStringToDateTime(String date) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("DDDyyyyHHmmss");
        return fmt.parseDateTime(date);
    }
}
