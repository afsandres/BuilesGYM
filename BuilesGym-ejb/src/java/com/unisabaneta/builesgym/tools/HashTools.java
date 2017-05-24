/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.tools;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;

/**
 *
 * @author Andres
 */
@Stateless
public class HashTools {

    /**
     * Calcula Hash MD5 del texto ingresado
     *
     * @param txt
     * @return
     */
    public String calculateMD5(String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            md5hash = md.digest();
            return convertToHex(md5hash);
        } catch (Exception ex) {
            return null;
        }
    }

    public String calculateHmacMD5(String msg, String keyString) {
        String result = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);
            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
            result = convertToHex(bytes);
        } catch (UnsupportedEncodingException  | NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
        return result;
    }

    /**
     * Convierte de hash a una cadena HExadecimal
     *
     * @param data
     * @return
     */
    private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}
