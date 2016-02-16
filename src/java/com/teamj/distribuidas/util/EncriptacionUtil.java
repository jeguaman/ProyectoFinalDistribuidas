package com.teamj.distribuidas.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author hendrix
 */
public class EncriptacionUtil {

    public static String encriptarMD5(String cadena) {
        byte[] hashedBytes;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            hashedBytes = digest.digest(cadena.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(
                    "Could not generate hash from String", ex);
        }

        return EncriptacionUtil.convertByteArrayToHexString(hashedBytes);
    }

    public static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

}
