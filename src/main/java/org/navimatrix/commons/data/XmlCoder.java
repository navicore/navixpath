package org.navimatrix.commons.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.navimatrix.commons.concurrent.Navirupt;
import org.navimatrix.commons.data.sdoimpl.BasicDataGraph;
import org.navimatrix.commons.data.sdoimpl.BasicDataObject;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlCoder implements Coder {

    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    /////                                              /////
    /////    TODO: construct coders with properties    /////
    /////                                              /////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////

    private static final boolean debug = false;

    private final boolean m_repair_errors;

    ////////////////////////////////////////////////////////
    // note, string interning is now managed by type and  //
    //  property impls                                    //
    ////////////////////////////////////////////////////////


    private final static boolean m_preserve_prefix_default;

    static {
        String i = System.getProperty("org.navimatrix.commons.data.preserve_prefix_default", "true");
        m_preserve_prefix_default = Boolean.valueOf(i).booleanValue();
    }

    private final boolean m_preserve_prefix;

    private final Map m_prefix_map;

    public XmlCoder() {

        this(m_preserve_prefix_default, null, false);
    }

    public XmlCoder(boolean preserve_prefix) {

        this(preserve_prefix, null, false);

    }

    public XmlCoder(boolean preserve_prefix, boolean repair_errors) {

        this(preserve_prefix, null, repair_errors);

    }

    public XmlCoder(boolean preserve_prefix, Map prefix_map) {

        this(preserve_prefix, prefix_map, false);
    }

    public XmlCoder(boolean preserve_prefix, Map prefix_map, boolean repair_errors) {

        m_preserve_prefix   = preserve_prefix;

        m_prefix_map        = prefix_map;

        m_repair_errors     = repair_errors;
    }

    //
    // begin Coder ifc
    //

    public DataObject decodeSdo(InputStream input) throws IOException {

        try {

            InputStreamReader isr = new InputStreamReader(input, "UTF8");

            return decode(isr);

        } catch (XmlPullParserException xppe) {

            throw new IOException(xppe.toString());
        }
    }


    public void encodeSdo(DataObject data, OutputStream output) throws IOException {

        try {

            OutputStreamWriter writer = new OutputStreamWriter(output, "UTF8");

            encode(data, writer, false, m_preserve_prefix, m_repair_errors);

        } catch (XmlPullParserException xppe) {

            throw new IOException(xppe.toString());
        }
    }


    private static class Singleton {
        private static final XmlCoder instance = new XmlCoder();
    }


    /**
     * 
     * @return 
     * @deprecated these are cheap to construct.  make your own.
     */
    public static final XmlCoder getInstance() {
        return Singleton.instance;
    }


    /**
     * returns a buffered input stream.  implementations that are not
     * naturally backed by bytes should implement a buffered approach
     * where each read ensures the buffer is fed from a checkpoint.
     * 
     * @return 
     * @exception IOException
     */
    public InputStream getInputStream(DataObject data) throws IOException {

        //todo: make this a small buffer approach, using concurrent channel to control flow

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        encodeSdo(data, bos);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return bis;
    }


    /**
     * handle writes that are prepared for partial fields
     * 
     * @return 
     * @exception IOException
     */
    public OutputStream getOutputStream(DataObject data) throws IOException {

        throw new java.lang.UnsupportedOperationException();
    }



    //
    // end Coder ifc
    //

    //
    //begin api
    //

    /**
     * 
     * @param input
     * 
     * @return 
     * @exception XmlPullParserException
     * @exception IOException
     */
    public static DataObject decode(Reader input) throws XmlPullParserException, IOException {

        Navirupt.check();

        XmlDecoder d = new XmlDecoder(input);
        d.run();

        return d.data;
    }

    /**
     * 
     * @param data
     * @param writer
     * 
     * @exception XmlPullParserException
     * @exception IOException
     */
    public static void encode(DataObject data, Writer writer)
    throws XmlPullParserException, IOException
    {
        encode(data, writer, false, m_preserve_prefix_default, false);
    }

    /**
     * 
     * @param data
     * @param writer
     * @param whitespace
     * 
     * @exception XmlPullParserException
     * @exception IOException
     */
    public static void encode(DataObject data, Writer writer, boolean whitespace)
    throws XmlPullParserException, IOException
    {
        encode(data, writer, whitespace, m_preserve_prefix_default, false);
    }

    public static void encode(DataObject data, Writer writer, boolean whitespace, boolean preserve_prefix)
    throws XmlPullParserException, IOException
    {
        encode(data, writer, whitespace, preserve_prefix, false);
    }

    /**
     * 
     * @param data
     * @param writer
     * @param whitespace
     * @param preserve_prefix
     * 
     * @exception XmlPullParserException
     * @exception IOException
     */
    public static void encode(DataObject data, Writer writer, boolean whitespace, boolean preserve_prefix, boolean repair_errors)
    throws XmlPullParserException, IOException
    {
        Navirupt.check();

        XmlEncoder xe = new XmlEncoder(writer, whitespace, preserve_prefix, null, repair_errors);
        xe.run(data);
    }

    //try to only use print for debugging

    public static String print(DataObject data) {
        try {
            StringWriter sw = new StringWriter();
            encode(data, sw, true, true);
            return sw.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    //
    //end api
    //

    /**
     */
    private static final class XmlDecoder {

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

                ((BasicDataObject) data).setValue(text);
            }
        }
    }


    private static class XmlEncoder {

        //String DEFAULT_NS = "";//this probabably can't easily be calculated.  set it
        //String DEFAULT_NS = "jabber:client";//this probabably can't easily be calculated.  set it
        // and the prefix will be ""

        final XmlSerializer serializer;

        String currentUri = null;

        final boolean m_preserve_pref;

        final boolean m_repair;

        final Map m_prefix_map;

        Writer writer;

        public XmlEncoder(final Writer writer, boolean whitespace, boolean preserve_prefix, Map prefix_map, boolean repair_errors)
        throws XmlPullParserException, IOException {

            m_repair = repair_errors;

            m_prefix_map = prefix_map;

            m_preserve_pref = preserve_prefix;

            this.writer = writer;

            serializer = DataFactory.getSerializer();

            serializer.setOutput(writer);

            if (whitespace) {
                serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-indentation", "  ");
                serializer.setProperty("http://xmlpull.org/v1/doc/properties.html#serializer-line-separator", "\n");
            }

            //uncomment this to prepend the proper xml document info ?xml, encoding, standalone...
            /*
            serializer.startDocument("UTF8", true);
            if (whitespace) {
                writer.write("\n");
            }
            */
        }

        public void run(final DataObject data) throws XmlPullParserException, IOException {

            final String uri = data.getType().getURI();

            String prefix;

            if (uri != null && m_prefix_map != null && m_prefix_map.containsKey(uri)) {

                prefix = (String) m_prefix_map.get(uri);

            } else {

                prefix = m_preserve_pref ? data.getType().getURIPrefix() : "";
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

                        serializer.text(v.toString());

                    } catch (java.lang.IllegalStateException ile) {

                        if (m_repair) {

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

