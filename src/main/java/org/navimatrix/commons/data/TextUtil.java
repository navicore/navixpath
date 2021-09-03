/* 
 * Blazebot Computing
 * Licensed Materials - Property of Ed Sweeney
 * Copyright Ed Sweeney 2004, 2005, 2006, 2007. All rights reserved.
 *
 * The contents of this file are subject to the GNU Lesser General Public License
 * Version 2.1 (the "License"). You may not copy or use this file, in either
 * source code or executable form, except in compliance with the License. You
 * may obtain a copy of the License at http://www.fsf.org/licenses/lgpl.txt or
 * http://www.opensource.org/. Software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express
 * or implied. See the License for the specific language governing rights and
 * limitations under the License.
 *
 * @author Ed Sweeney <a href="mailto:ed.edsweeney.net">
 */
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
