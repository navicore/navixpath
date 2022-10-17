// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package org.xmlpull.v1.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.builder.impl.XmlPullBuilderImpl;


//     private XmlPullBuilder builder = XmlPullBuilder.newInstance(); // (XmlPullParser)
//  builder.setUseAllTokens(true)  //comments, PIs, doctype

// setIgnoreComments
// setIgnoreProcessingInstructions
//  builder.setNamespaceAware(true)
//  builder.setBuildCompleteTree(true)


//  builder.setWrapCharacters(true)

// XmlDocument doc =  builder.parseUrl( url );

public abstract class XmlPullBuilder
{
    
    public static XmlPullBuilder newInstance() throws XmlBuilderException
    {
        XmlPullBuilder impl = new XmlPullBuilderImpl();
        try {
            impl.factory = XmlPullParserFactory.newInstance();
            impl.factory.setNamespaceAware(true); //TODO: allow user to override and set custom factory
            
        } catch(XmlPullParserException ex) {
            throw new XmlBuilderException("could not create XmlPull factory:"+ex, ex);
        }
        return impl;
    }
    
    protected XmlPullParserFactory factory;
    
    public XmlPullParserFactory getFactory() throws XmlBuilderException {return factory; }
    
    
    // --- create directly\
    public XmlDocument newDocument() throws XmlBuilderException {
        return newDocument(null, null, null);
    }
    
    public abstract XmlDocument newDocument(String version,
                                            Boolean standalone,
                                            String characterEncoding) throws XmlBuilderException;
    
    public abstract XmlElement newFragment(String elementName) throws XmlBuilderException;
    
    public abstract XmlElement newFragment(String elementNamespace, String elementName) throws XmlBuilderException;
    
    public abstract XmlElement newFragment(XmlNamespace elementNamespace,
                                           String elementName) throws XmlBuilderException;
    
    public abstract XmlNamespace newNamespace(String namespaceName) throws XmlBuilderException;
    
    public abstract XmlNamespace newNamespace(String prefix, String namespaceName) throws XmlBuilderException;
    
    // --- parsing
    
    //public abstract XmlElement newFragment(String elementNamespace, String elementName, XmlNamespace[] context);
    //public abstract XmlElement parse(XmlPullParser sourceForNode,boolean addAllNamespaces);
    
    /**
     * Parse document - parser must be in START_DOCUMENT state.
     */
    public abstract XmlDocument parse(XmlPullParser sourceForDocument) throws XmlBuilderException;
    
    
    /**
     * Will convert current parser state into event rerpresenting XML infoset item: <ul>
     * <li>START_Document: XmlDocument without root element
     * <li>START_TAG: XmlElement without children
     * <li>TEXT: String or XmlCHaracters depending on builder mode
     * <li>additiona states to corresponding XML infoset items (when implemented!)
     * </ul>
     */
    public abstract Object parseItem(XmlPullParser pp) throws XmlBuilderException;
    
    /**
     * Parser must be on START_TAG
     */
    public abstract XmlElement parseStartTag(XmlPullParser pp) throws XmlBuilderException;
    
    public XmlDocument parseInputStream(InputStream is) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(is, null);
            //set options ...
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input stream", e);
        }
        return parse(pp);
    }
    
    public XmlDocument parseInputStream(InputStream is, String encoding) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(is, encoding);
            //set options ...
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input stream (encoding="+encoding+")", e);
        }
        return parse(pp);
    }
    
    public XmlDocument parseReader(Reader reader) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(reader);
            //set options ...
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input from reader", e);
        }
        return parse(pp);
    }
    
    public abstract XmlDocument parseLocation(String locationUrl) throws XmlBuilderException;
    
    /**
     * Parse fragment  - parser must be on START_TAG.
     */
    public abstract XmlElement parseFragment(XmlPullParser sourceForXml) throws XmlBuilderException;
    
    
    public XmlElement parseFragmentFromInputStream(InputStream is) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(is, null);
            //set options ...
            try {
                pp.nextTag();
            } catch (IOException e) {
                throw new XmlBuilderException(
                    "IO error when starting to parse input stream", e);
            }
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input stream", e);
        }
        return parseFragment(pp);
    }
    
    public XmlElement parseFragementFromInputStream(InputStream is, String encoding) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(is, encoding);
            //set options ...
            try {
                pp.nextTag();
            } catch (IOException e) {
                throw new XmlBuilderException(
                    "IO error when starting to parse input stream (encoding="+encoding+")", e);
            }
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input stream (encoding="+encoding+")", e);
        }
        return parseFragment(pp);
    }
    
    public XmlElement parseFragmentFromReader(Reader reader) throws XmlBuilderException
    {
        XmlPullParser pp = null;
        try {
            pp = factory.newPullParser();
            pp.setInput(reader);
            //set options ...
            try {
                pp.nextTag();
            } catch (IOException e) {
                throw new XmlBuilderException(
                    "IO error when starting to parse from reader", e);
            }
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not start parsing input from reader", e);
        }
        return parseFragment(pp);
    }
    
    public void skipSubTree(XmlPullParser pp) throws XmlBuilderException
    {
        try {
            pp.require(XmlPullParser.START_TAG, null, null);
            int level = 1;
            while(level > 0) {
                int eventType = pp.next();
                if(eventType == pp.END_TAG) {
                    --level;
                } else if(eventType == pp.START_TAG) {
                    ++level;
                }
            }
        } catch (XmlPullParserException e) {
            throw new XmlBuilderException("could not skip subtree", e);
        } catch (IOException e) {
            throw new XmlBuilderException("IO error when skipping subtree", e);
        }
    }
    
    // --- serialization
    
    public abstract void serializeStartTag(XmlElement el, XmlSerializer ser)
        throws XmlBuilderException;
    public abstract void serializeEndTag(XmlElement el, XmlSerializer ser)
        throws XmlBuilderException;
    
    /**
     * Serialize XML infoset item including serializing of children.
     * If item is Collection all items in collection are serialized by
     * recursively calling this function.
     * This method  assumes that item is either interface defined in XB1 API, class String,
     * or that item implements XmlSerializable otherwise IllegalArgumentException
     * is thrown.
     */
    public abstract void serialize(Object item, XmlSerializer serializer) throws XmlBuilderException;
    //throws XmlPullParserException, IOException, IllegalArgumentException;
    
    /**
     * Serialize XML infoset item <b>without</b> serializing any of children.
     * This method  assumes that item is either interface defined in XB1 API, class String,
     * or oitem implements XmlSerializable otherwise IllegalArgumentException
     * is thrown.
     */
    public abstract void serializeItem(Object item, XmlSerializer serializer) throws XmlBuilderException;
    
    /**
     * Serialize using default UTF8 encoding.
     */
    public void serializeToOutputStream(Object item, //XmlContainer node,
                                        OutputStream os)
        throws XmlBuilderException
        //throws XmlPullParserException, IOException, IllegalArgumentException
    {
        serializeToOutputStream(item, os, "UTF8");
    }
    
    public void serializeToOutputStream(Object item, //XmlContainer node,
                                        OutputStream os,
                                        String encoding)
        throws XmlBuilderException
        //throws XmlPullParserException, IOException, IllegalArgumentException
    {
        XmlSerializer ser = null;
        try {
            ser = factory.newSerializer();
            ser.setOutput(os, encoding);
        } catch (Exception e) {
            throw new XmlBuilderException("could not serialize node to output stream"
                                              +" (encoding="+encoding+")", e);
        }
        serialize(item, ser);
        try {
            ser.flush();
        } catch (IOException e) {
            throw new XmlBuilderException("could not flush output", e);
        }
    }
    
    public void serializeToWriter(Object item, //XmlContainer node,
                                  Writer writer)
        //throws XmlPullParserException, IOException, IllegalArgumentException
        throws XmlBuilderException
    {
        XmlSerializer ser = null;
        try {
            ser = factory.newSerializer();
            ser.setOutput(writer);
        } catch (Exception e) {
            throw new XmlBuilderException("could not serialize node to writer", e);
        }
        serialize(item, ser);
        try {
            ser.flush();
        } catch (IOException e) {
            throw new XmlBuilderException("could not flush output", e);
        }
    }
    
    public String serializeToString(Object item) //XmlContainer node)
        //throws XmlPullParserException, IOException, IllegalArgumentException
        throws XmlBuilderException
    {
        StringWriter sw = new StringWriter();
        serializeToWriter(item, sw);
        return sw.toString();
    }
    
}

