package br.com.ajenterprice.sgg_api.util;

import br.com.ajenterprice.sgg_api.exception.ServiceException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografiaUtil {

    public static String criptografarSHA256(String valor) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                    valor.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
