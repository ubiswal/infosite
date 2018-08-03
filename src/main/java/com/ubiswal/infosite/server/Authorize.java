package com.ubiswal.infosite.server;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class Authorize {
    private final static Logger LOGGER = Logger.getLogger(Authorize.class.getName());
    private final String secret;
    
    public Authorize(final String secret) {
        this.secret = Objects.requireNonNull(secret);
    }
    
    public boolean authenticate(String operationStr, String hmacStr) {
        String construct = operationStr + ":" + secret;
        try {
            String calculatedMac = hmac(construct, secret);
            LOGGER.info("Successfully authorized");
            return calculatedMac.toLowerCase().equals(hmacStr.toLowerCase());
        } catch (InvalidKeyException e) {
            LOGGER.error("Failed to authorize " + e);
            return false;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Failed to authorize " + e);
            return false;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Failed to authorize " + e);
            return false;
        }
    }
    
    public static String hmac(final String message, final String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        LOGGER.debug("Message : " + message);
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
