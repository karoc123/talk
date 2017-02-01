package de.othr.sw.talk.entity.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * This class is from the OTHBib, not from me
 */
public class EntityUtils {

    public static String hashPassword(String password, String salt, String algorithm) throws EntityUtilException {
        try {
            MessageDigest hashAlgo = MessageDigest.getInstance(algorithm);
            String toHash = salt + "#" + password;
            byte[] output = hashAlgo.digest(toHash.getBytes("UTF-8"));
            StringBuilder passwordBuilder = new StringBuilder();
            for(byte b : output)
                passwordBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return passwordBuilder.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new EntityUtilException("Could not hash password", ex);
        }
    }
    
    public static String createRandomString(int byteLength) {
        SecureRandom zufallsgenerator = new SecureRandom();
        byte[] bytes = new byte[byteLength];
        zufallsgenerator.nextBytes(bytes);
        BigInteger zufallszahl = new BigInteger(bytes);
        return zufallszahl.abs().toString(32).toUpperCase();
    }
    
    public static String createRandomUUID() {
        return UUID.randomUUID().toString();
    }
    
    public static class EntityUtilException extends Exception {
        public EntityUtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
