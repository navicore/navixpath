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
