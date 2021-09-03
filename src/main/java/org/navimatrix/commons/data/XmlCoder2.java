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

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.navimatrix.commons.data.sdoimpl.BasicDataGraph;
import org.navimatrix.commons.data.sdoimpl.BasicDataObject;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlCoder2 implements Coder {

    private static final Log m_log = LogFactory.getLog(XmlCoder.class);

    public static final String PROP_DEBUG               = "debug";
    public static final String PROP_REPAIR_ERRORS       = "repair_errors";
    public static final String PROP_PRESERVE_PREFIX     = "preserve_prefix";
    public static final String PROP_WHITESPACE          = "whitespace";
    public static final String PROP_URL_ENCODE          = "url_encode";
    public static final String PROP_XML_PREAMBLE_OFF    = "xml_preamble_off";
    public static final String PROP_USE_APOSTROPHE      = "use_apostrophe";

    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    /////                                              /////
    /////    TODO: construct coders with properties    /////
    /////                                              /////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

    private final boolean debug;
    private final boolean m_repair_errors;
    private final boolean m_url_encode;
    private final boolean m_preserve_prefix;
    private final boolean m_whitespace;
    private final boolean m_xml_preamble_off;
    private final boolean m_use_apostrophe;

    ////////////////////////////////////////////////////////
    // note, string interning is now managed by type and  //
    //  property impls                                    //
    ////////////////////////////////////////////////////////

    //ejs todo: rewrite w/o the static stuff. this is a mess

    private static boolean m_preserve_prefix_default = false;

    static {
        String i = System.getProperty("org.navimatrix.commons.data.preserve_prefix_default", "true");
        m_preserve_prefix_default = Boolean.valueOf(i).booleanValue();
    }

    private final Map m_prefix_map;

    //
    // begin constructors
    //

    public XmlCoder2(Map properties, Map prefix_map) {

        debug               = properties.containsKey(PROP_DEBUG);
        m_repair_errors     = properties.containsKey(PROP_REPAIR_ERRORS);
        m_whitespace        = properties.containsKey(PROP_WHITESPACE);
        m_url_encode        = properties.containsKey(PROP_URL_ENCODE);
        m_preserve_prefix   = properties.containsKey(PROP_PRESERVE_PREFIX);
        m_xml_preamble_off  = properties.containsKey(PROP_XML_PREAMBLE_OFF);
        m_use_apostrophe    = properties.containsKey(PROP_USE_APOSTROPHE);
        m_prefix_map = prefix_map;
    }

    public XmlCoder2(Map properties) {

        this(properties, null);
    }


    public XmlCoder2() {

        this(new Properties(), null);
    }

    //
    // end constructors
    //

    //
    // begin Coder ifc
    //

    public DataObject decodeSdo(InputStream input) throws IOException {

        try {

            InputStreamReader isr = new InputStreamReader(input, "UTF8");

            XmlDecoder d = new XmlDecoder(isr);

            d.run();

            return (d.data);

        } catch (XmlPullParserException xppe) {

            throw new IOException(xppe.toString());
        }
    }


    public void encodeSdo(DataObject data, OutputStream output) throws IOException {

        try {

            OutputStreamWriter writer = new OutputStreamWriter(output, "UTF8");

            XmlEncoder xe = new XmlEncoder(writer);
            xe.run(data);

        } catch (XmlPullParserException xppe) {

            throw new IOException(xppe.toString());
        }
    }


    //
    // end Coder ifc
    //


    /**
     */
    private final class XmlDecoder {

        private final MXParser xpp;

        DataObject data;

        public XmlDecoder(Reader input) throws XmlPullParserException, IOException {

            xpp = DataFactory.getParser();

            xpp.setInput(input);
        }

        public void run() throws XmlPullParserException, IOException {
            processDocument(xpp);
        }

        private void processDocument(XmlPullParser xpp)
        throws XmlPullParserException, IOException
        {
            int eventType = xpp.getEventType();
            do {

                if (eventType == xpp.START_DOCUMENT) {
                } else if (eventType == xpp.END_DOCUMENT) {
                } else if (eventType == xpp.START_TAG) {
                    processStartElement(xpp);
                } else if (eventType == xpp.END_TAG) {
                    processEndElement(xpp);
                } else if (eventType == xpp.TEXT) {
                    processText(xpp);
                }
                eventType = xpp.next();
            } while (eventType != xpp.END_DOCUMENT);
        }

        private void processStartElement (XmlPullParser xpp)
        throws XmlPullParserException
        {
            int depth = xpp.getDepth() - 1; //xpp starts at 1

            String name = xpp.getName();

            String uri = xpp.getNamespace();

            String prefix = xpp.getPrefix();

            switch (depth) {
            case 0:
                //start session
                DataGraph graph = new BasicDataGraph();
                data = graph.createRootObject(uri, prefix, name);
                if (data == null) {
                    throw new java.lang.NullPointerException("graph is broken");
                }
                setAttrs(xpp, data);
                break;

            default:
                data = data.createDataObject(name, uri, prefix);
                if (data == null) {
                    throw new java.lang.NullPointerException("dataobject factory is broken");
                }
                setAttrs(xpp, data);
            }
        }

        private void setAttrs(XmlPullParser xpp, DataObject data) {

            for (int i = 0; i < xpp.getAttributeCount(); i++) {

                String attrName = xpp.getAttributeName(i);

                String attrValue = xpp.getAttributeValue(i);

                data.setString("@" + attrName, attrValue);
            }
        }

        private void processEndElement (XmlPullParser xpp)
        throws XmlPullParserException
        {
            int depth = xpp.getDepth() - 1; //xpp starts at 1

            switch (depth) {
            case 0:
                //close session
                break;

            default:
                data = data.getContainer();
                if (data == null) {
                    throw new java.lang.NullPointerException("def lvl dataobject has no root");
                }
            }
        }

        //private int holderForStartAndLength[] = new int[2];
        private void processText (XmlPullParser xpp) throws XmlPullParserException
        {
            //warning, error!!!! seems to get called before all the text is read??? called multi
            //tricky to address current node...
            if (data != null) {
                String text = xpp.getText();
                if (text == null || text.trim().equals("")) {
                    return;
                }

                String decodedText = null;

                try {

                    if (m_url_encode) {
                        decodedText = URLDecoder.decode(text, "UTF8");
                    } else {
                        decodedText = text;
                    }
                } catch (java.io.UnsupportedEncodingException use) {

                    m_log.warn(use.toString());

                    decodedText = text;
                }

                ((BasicDataObject) data).setValue(decodedText);
            }
        }
    }


    private final class XmlEncoder {

        //String DEFAULT_NS = "";//this probabably can't easily be calculated.  set it
        //String DEFAULT_NS = "jabber:client";//this probabably can't easily be calculated.  set it
        // and the prefix will be ""

        final XmlSerializer serializer;

        String currentUri = null;

        Writer writer;

        public XmlEncoder(final Writer writer) throws XmlPullParserException, IOException {

            this.writer = writer;

            serializer = DataFactory.getSerializer();

            serializer.setOutput(writer);

            if (m_whitespace) {

                serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  ");
                serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");
            }

            if (m_use_apostrophe) {
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#serializer-attvalue-use-apostrophe", true);
            }

            if (!m_xml_preamble_off) {

                serializer.startDocument("UTF8", true);
                if (m_whitespace) {
                    writer.write("\n");
                }
            }
        }

        public void run(final DataObject data) throws XmlPullParserException, IOException {

            final String uri = data.getType().getURI();

            String prefix;

            if (uri != null && m_prefix_map != null && m_prefix_map.containsKey(uri)) {

                prefix = (String) m_prefix_map.get(uri);

            } else {

                prefix = m_preserve_prefix ? data.getType().getURIPrefix() : "";
            }

            currentUri = uri;

            if (uri == null) {
                throw new NullPointerException("uri can not be null: " + data); //i18n
            }

            serializer.setPrefix(prefix, uri);

            serializer.startTag(uri, data.getType().getName());

            processAttrs(data);

            processData(data, null);

            serializer.endTag(uri, data.getType().getName());
        }

        private void processData(final DataObject data, final DataObject parent) throws XmlPullParserException, IOException {

            final List props = data.getType().getProperties();

            final Object grandparent = (parent != null ? parent.getContainer() : null);

            //process elements
            for (Iterator iter = props.iterator(); iter.hasNext();) {

                final Property p = (Property) iter.next();

                if (isAttr(p)) {

                    //don't process attributes here
                    continue;
                }

                if (data.isSet(p)) {

                    Object fld = data.get(p);

                    if (fld == data) continue; //circ
                    if (fld == parent) continue; //circ
                    if (fld == grandparent) continue; //circ

                    if (fld instanceof List) {

                        //process isMany
                        List lst = (List) fld;
                        for (int i = 0; i < lst.size(); i++) {

                            final Object lfld = lst.get(i);

                            if (lfld instanceof BasicDataObject) {

                                if (fld == data) continue; //circ
                                if (fld == parent) continue; //circ
                                if (fld == grandparent) continue; //circ
                                processElement((BasicDataObject) lfld, data);
                            }
                        }

                    } else if (fld instanceof BasicDataObject) {


                        if (fld == data) {
                            fld = "_this_";
                        }

                        //process !isMany elements
                        processElement((BasicDataObject) fld, data);
                    }
                }
            }
        }

        private void processElement(final BasicDataObject data, final DataObject parent) throws XmlPullParserException, IOException {

            final Object grandparent = (parent != null ? parent.getContainer() : null);

            String uri = data.getType().getURI();
            String prefix = data.getType().getURIPrefix();

            //if (!uri.equals(currentUri) && serializer.getPrefix(uri, false) == null) {
            if (!uri.equals(currentUri)) {

                boolean skip = false;

                //see if a prefix for this uri already exists
                if (uri != null         &&
                    !uri.equals("")     &&
                    serializer.getPrefix(uri, false) != null) {

                    skip = true;
                }
                if (!skip) {
                    //if this is a new uri, we don't want to use prefixes
                    serializer.setPrefix(prefix, uri);
                }
            }
            if (!uri.equals("")) {
                currentUri = uri;
            }

            /*
            serializer.setPrefix(prefix, uri);
            */

            final String name = data.getType().getName();

            serializer.startTag(uri, name);

            processAttrs(data);

            /////////////////////
            /// there is a bug here in that an element with attrs can't have a value, only
            /// children.  xml2data works fine, but data2xml loses values! todo: fixme
            ////////////////////

            if (!data.hasValue()) {
                processData(data, parent);
            } else {
                //serializer.text(data.toString());
                final Object v = data.getValue();
                if (v == parent || v == grandparent) {
                    return; //protect against circular refs
                }
                if (v != null) {
                    try {

                        //serializer.text(v.toString());
                        serializer.text(URLEncoder.encode(v.toString(),"UTF8"));

                    } catch (java.lang.IllegalStateException ile) {

                        if (m_repair_errors) {

                            serializer.text("_ERROR_BAD_DATA_");

                        } else {

                            throw ile;
                        }
                    }
                }
            }
            serializer.endTag(uri, name);
        }

        private void processAttrs(final Object object) throws IOException {
            if (!(object instanceof DataObject)) {
                return;
            }
            final DataObject data = (DataObject) object;
            final List props = data.getType().getProperties();

            for (int i = 0; i < props.size(); i++) {

                final Property p = (Property) props.get(i);

                if (isAttr(p) && data.isSet(p)) {

                    final String flddata = data.getString(p);
                    final String fldname = p.getName().substring(1, p.getName().length());

                    if (fldname != null && flddata != null) {
                        serializer.attribute(null, fldname, flddata);
                    }
                }
            }
        }

        private boolean isAttr(Property p) {
            return p.getName().charAt(0) == '@';
        }
    }
}

