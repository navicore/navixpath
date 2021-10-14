package org.navimatrix.commons.data.util;

public class Base16 {


    public static String encode(byte[] bytes)
    {
        if (bytes == null) return null;

        StringBuffer sb = new StringBuffer();
        String hex = null;

        for (int i = 0; i < bytes.length; i++) {

            hex = Integer.toHexString(bytes[i]);

            // 2 digits only - strip off the preceding FFFFFF
            if (bytes[i] < 0) {
                hex = hex.substring(6,8);
            }

            // prepend 0 if only 1 char
            if (hex.length() < 2) {
                hex = "0" + hex;
            }

            sb.append(hex);
        }

        return sb.toString();
    }


    public static byte[] decode(String message) {

        char[] chars    = message.toCharArray();
        int size        = chars.length/2;
        byte[] bytes    = new byte[size];

        int count       = 0;
        int temp        = 0;

        for (int i = 0; i < size; i++) {

            StringBuffer sb = new StringBuffer(2);

            temp = count*2;
            sb = sb.append(chars[temp]).append(chars[temp+1]);

            bytes[count] = (byte) Integer.parseInt(sb.toString(),16);
            count++;
        }

        return bytes;
    }
}
