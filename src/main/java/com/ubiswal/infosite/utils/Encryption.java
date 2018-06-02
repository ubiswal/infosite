package com.ubiswal.infosite.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    // Source: https://stackoverflow.com/questions/39355241/compute-hmac-sha512-with-secret-key-in-java
    public static String hmac(final String message, final String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] byteKey = key.getBytes("UTF-8");
        final String HMAC_SHA512 = "HmacSHA512";
        Mac sha512_HMAC = Mac.getInstance(HMAC_SHA512);
        SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
        sha512_HMAC.init(keySpec);
        byte[] mac_data = sha512_HMAC.doFinal(message.getBytes("UTF-8"));
        // result = Base64.encode(mac_data);
        String result = bytesToHex(mac_data);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
