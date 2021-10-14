package org.navimatrix.commons.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TextUtil {

    public static String createSha1Digest(String msg) {

        try {

            MessageDigest digester = MessageDigest.getInstance("SHA-1");
            byte[] bytes = digester.digest(msg.getBytes("UTF8"));
            return getHexString(bytes);

        } catch (java.io.UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
        return null;
    }


    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getHexString(byte[] bytes) {

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            sb.append(digits[(b& 0xf0) >> 4]);
            sb.append(digits[b& 0x0f]);
        }
        return sb.toString();
    }


    public static String pad(String src, String pad) {
        if (src.length() >= pad.length()) {
            return src;
        }
        return pad.substring(0, pad.length() - src.length()) + src;
    }
}
