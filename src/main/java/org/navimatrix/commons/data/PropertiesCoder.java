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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesCoder implements Coder {

    private static final boolean debug = true;

    public static final String COMMENT_PROP_PREFIX = "navi_comments_";

    private static final String DEFAULT_HEADER = "# Created by Navicore Properties Coder.";

    private final Log m_log = LogFactory.getLog(getClass().getName());

    private final String m_header;

    public PropertiesCoder() {

        this(DEFAULT_HEADER);
    }

    public PropertiesCoder(String header) {

        m_header = header;
    }


    //
    // begin Coder ifc
    //

    /**
     * 
     * @param input
     * 
     * @return 
     * @exception IOException
     */
    public DataObject decodeSdo(InputStream input) throws IOException {

        DataObject data = DataFactory.createDataObject("org.navimatrix.commons.data", "properties");

        Reader r = new java.io.InputStreamReader(input);
        LineNumberReader lnr = new LineNumberReader(r);

        StringBuffer comment = new StringBuffer();

        while (lnr.ready()) {

            int line_number = lnr.getLineNumber();

            String s = lnr.readLine();

            if (s == null || s.trim().equals("")) {
                //data.setString(COMMENT_PROP_PREFIX + line_number, "");
                comment.append("\n");
                continue;
            }

            if (s.trim().startsWith(m_header)) {
                continue;
            }

            if (s.trim().startsWith("#")) {
                //data.setString(COMMENT_PROP_PREFIX + line_number, s);
                comment.append(s);
                comment.append("\n");
                continue;
            }

            int pos = s.indexOf("=");

            if (pos < 1) {
                m_log.warn("PropertiesCoder.decodeSdo illegal syntax: " + s);
                continue;
            }

            String key = s.substring(0, pos).trim();
            key = "./" + key.replace('.', '/');
            String value = s.substring(pos +1).trim();

            data.set(key, value);

            if (comment.length() > 0) {

                String ckey = key + "/@comment";
                data.set(ckey, comment.toString());
                comment = new StringBuffer();
            }
        }

        return data;
    }


    /**
     * 
     * @param data
     * @param output
     * 
     * @exception IOException
     */
    public void encodeSdo(DataObject data, OutputStream output) throws IOException {

        output.write(m_header.getBytes());

        output.write("  Updated: ".getBytes());

        output.write(new Date().toString().getBytes());

        output.write("\n".getBytes());

        encodeSdo(data, output, "");
    }

    private void encodeSdo(DataObject data, OutputStream output, String prop_prefix) throws IOException {

        List properties = data.getType().getProperties();

        for (Iterator iter = properties.iterator(); iter.hasNext();) {

            Property p = (Property) iter.next();

            if ("@comment".equals(p.getName())) {
                continue; //already got this
            }

            DataValue val = (DataValue) data.getDataObject(p);

            DataObject dval = (DataObject) val;

            if (dval.isSet("@comment")) {

                String c = dval.getString("@comment");

                output.write(c.getBytes());
            }

            String key = prop_prefix + p.toString();

            if (val.hasValue()) {

                String v = val.getValue().toString();

                if (key.startsWith(COMMENT_PROP_PREFIX) || v.trim().startsWith("#")) {

                    output.write(v.getBytes());

                    output.write("\n".getBytes());

                } else {

                    output.write(key.getBytes());

                    output.write("=".getBytes());

                    output.write(v.getBytes());

                    output.write("\n".getBytes());
                }

            }

            encodeSdo((DataObject) val, output, key + ".");
        }
    }

    //
    // end Coder ifc
    //
}

