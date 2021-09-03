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
package org.navimatrix.tests.commons.data;

import org.navimatrix.commons.data.*;
import org.navimatrix.commons.data.sdoimpl.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.mxp1.MXParser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(XmlTestCase.class);
    }

    // 
    // begin tests
    //

    /*
    
    todo: rewrite w/o swaping  out parser
    
    does not support reusing parsers for different streams. but DOES support restarting parser
    and managing leftovers
    */
    

    public void testUnbreakableXmlProcessing() {

    /*
        DataObject data;
        String filename;
        XmlDecoder decoder = null;
        Object state = null;
        DataObject save;
        char[] leftover = null;
        try {
            // PROCESS FILE 1
            try {
                filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "xmpp_2_part1.xml";
                char[] inputChars = getChars(filename);
                Reader input = new CharArrayReader(inputChars);
                decoder = new XmlDecoder(input);
                decoder.run();
            } catch (java.io.EOFException e) {
            }
            //state = ((MXParser) decoder.xpp).getState();
            save = decoder.data;
            leftover = decoder.getLeftOver();

            // PROCESS FILE 2
            try {
                filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "xmpp_2_part2.xml";
                char[] inputChars = getChars(filename);

                if (leftover != null) {
                    char[] newInputChars = new char[inputChars.length + leftover.length];
                    System.arraycopy(leftover, 0, newInputChars, 0, leftover.length);
                    System.arraycopy(inputChars, 0, newInputChars, leftover.length, inputChars.length);
                    inputChars = newInputChars;
                }

                //decoder = new XmlDecoder(null); //swap in NEW parser
                decoder.data = save;

                Reader input = new CharArrayReader(inputChars);
                ((MXParser) decoder.xpp).resume(input, state); //set new input
                decoder.run();
            } catch (java.io.EOFException e) {
            }
            //state = ((MXParser) decoder.xpp).getState();
            save = decoder.data;
            leftover = decoder.getLeftOver();

            // PROCESS FILE 3
            //make sure a parser can process a different doc and return to unfinished doc
            try {
                filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "xmpp.xml";
                char[] inputChars = getChars(filename);
                Reader input = new CharArrayReader(inputChars);
                ((MXParser) decoder.xpp).setInput(input);
                decoder.run();
            } catch (java.io.EOFException e) {
            }

            Assert.assertTrue("lost data", ((DataObject)events.get(1)).getString("body").indexOf("time is it") > -1);

            // PROCESS FILE 4
            try {
                filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "xmpp_2_part3.xml";
                char[] inputChars = getChars(filename);

                if (leftover != null) {
                    char[] newInputChars = new char[inputChars.length + leftover.length];
                    System.arraycopy(leftover, 0, newInputChars, 0, leftover.length);
                    System.arraycopy(inputChars, 0, newInputChars, leftover.length, inputChars.length);
                    inputChars = newInputChars;
                }

                decoder = new XmlDecoder(null); //swap in NEW parser
                decoder.data = save;

                Reader input = new CharArrayReader(inputChars);
                ((MXParser) decoder.xpp).resume(input, state); //set new input
                decoder.run();
            } catch (java.io.EOFException e) {
            }
            state = ((MXParser) decoder.xpp).getState();
            save = decoder.data;
            leftover = decoder.getLeftOver();

            Assert.assertEquals("wrong message count", 5, events.size());
            Assert.assertTrue("lost data", ((DataObject)events.get(0)).getString("body").indexOf("is this message") > -1);
            Assert.assertTrue("lost data", ((DataObject)events.get(2)).getString("body").indexOf("Part of file") > -1);
            Assert.assertTrue("lost data", ((DataObject)events.get(2)).getString("body").indexOf("top now") > -1);
            Assert.assertTrue("lost data", ((DataObject)events.get(3)).getString("body").indexOf("Part 3") == 0);
            Assert.assertTrue("lost data", ((DataObject)events.get(3)).getString("body").indexOf("middle now") > -1);
            Assert.assertTrue("lost data", ((DataObject)events.get(4)).getString("body").indexOf("Going now") > -1);

            //verify ns info not lost
            Assert.assertTrue("lost ns", ((DataObject)events.get(4)).getType().getURI().equals("jabber:client"));

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    */
    }

    // 
    // end tests
    //

    static List events = new ArrayList();

    private static class XmlDecoder {

        XmlPullParser xpp;
        char[] leftover = null;

        DataObject data;

        public XmlDecoder(Reader input) throws XmlPullParserException, IOException {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                                                                           System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();

            if (input != null) {
                xpp.setInput(input);
            }
        }

        public void run() throws XmlPullParserException, IOException {
            processDocument(xpp);
        }

        private void processDocument(XmlPullParser xpp)
        throws XmlPullParserException, IOException
        {
            try {
                int eventType = xpp.getEventType();
                do {
                    if (eventType == xpp.START_DOCUMENT) {

                    } else if (eventType == xpp.START_TAG) {

                        processStartElement(xpp);

                    } else if (eventType == xpp.END_TAG) {

                        processEndElement(xpp);

                    } else if (eventType == xpp.TEXT) {

                        processText(xpp);

                    }

                    eventType = xpp.next();

                } while (eventType != xpp.END_DOCUMENT);
            } finally {
                setLeftOver();
            }
        }

        private void processStartElement (XmlPullParser xpp)
        throws XmlPullParserException
        {
            setPosition();

            int depth = xpp.getDepth() - 1; //xpp starts at 1
            String name = xpp.getName();
            String uri = xpp.getNamespace();

            //todo: get attrs for each start
            switch (depth) {
            case 0:
                //start session
                break;

            case 1: //an xmpp stanza
                DataGraph graph = new BasicDataGraph();
                data = graph.createRootObject(uri, name);
                if (data == null) {
                    throw new java.lang.NullPointerException("graph is broken");
                }
                if (data == null) {
                    throw new java.lang.NullPointerException("dataobject factory is broken");
                }
                setAttrs(xpp, data);
                break;

            default:
                data = data.createDataObject(name, uri, "");
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
                //data.getDataObject(attrName).setBoolean("XML_ATTR", true);//api hack for rewrite
            }
        }


        private void processEndElement (XmlPullParser xpp)
        throws XmlPullParserException
        {
            setPosition();

            int depth = xpp.getDepth() - 1; //xpp starts at 1
            //String name = xpp.getName();
            //String uri = xpp.getNamespace();

            switch (depth) {
            case 0:
                //close session
                break;

            case 1:

                System.out.println("adding: " + data.getString("body"));
                events.add(data);

                //data  = null;
                break;

            default:
                data = data.getContainer();
                if (data == null) {
                    throw new java.lang.NullPointerException("def lvl dataobject has no root");
                }
            }
        }

        private void processText (XmlPullParser xpp) throws XmlPullParserException
        {
            setPosition();
            //warning, error!!!! seems to get called before all the text is read??? called multi
            //tricky to address current node...
            if (data != null) {
                String text = new String(chars, holderForStartAndLength[0], holderForStartAndLength[1]);
                if (text == null || text.trim().equals("")) {
                    return;
                }
                ((BasicDataObject) data).setValue(text);
            }
        }

        private int holderForStartAndLength[] = new int[2];
        private char[] chars = null;

        private void setPosition() {
            chars = xpp.getTextCharacters(holderForStartAndLength);
        }

        public char[] getLeftOver() {
            return leftover;
        }

        private void setLeftOver() {

            leftover = null;

            int pos = holderForStartAndLength[0] + holderForStartAndLength[1];
            int newpos = ((MXParser) xpp).getBufferEnd();

            if (newpos > pos) {
                int len = newpos - pos;
                leftover = new char[len];
                System.arraycopy(chars, pos, leftover, 0, len);
            }
        }
    }

    static char[] getChars(String filename) {
        //causes bugs with eroneous cr and lf
        try {
            File f = new File(filename);
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] chars = new char[1024];
            int cnt = isr.read(chars, 0, chars.length);
            char[] ret = new char[cnt];
            System.arraycopy(chars, 0, ret, 0, ret.length);
            return ret;
        } catch (IOException ioe) {
            return null;
        }
    }
}
