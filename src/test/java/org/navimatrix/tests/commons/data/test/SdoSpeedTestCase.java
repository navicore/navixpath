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
package org.navimatrix.tests.commons.data.test;

import org.navimatrix.commons.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.navimatrix.commons.data.sdoimpl.*;

public class SdoSpeedTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(SdoSpeedTestCase.class);
    }

    // 
    // begin tests
    //

    public String m_str = null;
    public DataObject m_newdata = null;
    public static String m_newstr = null;


    public void testSpeed1() {

        //if (true) { return; }

        try {

            InputStream res = this.getClass().getResourceAsStream("/test/xmpp_bigger.xml");
            org.junit.Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            long starttime = System.currentTimeMillis();

            for (int i = 0; i < 500; i++) {

                m_str = data.toString();

		Assert.assertNotNull("bad toString", m_str);
		//Assert.assertTrue("big toString:\n" + m_str.substring(m_str.length() - 100), m_str.length() < 100000);

                data = XmlCoder.decode(new java.io.StringReader(m_str));

                data.setLong("./fld_" + i + "/item/time", System.currentTimeMillis());
                data.setString("./fld_" + i + "/item/message", "i am iteration " + i);

                m_newstr = data.toString();
            }

            long stoptime = System.currentTimeMillis();

            long dur = stoptime - starttime;

	    String d = XmlCoder.print(data);
            //System.out.println("last iter of new data: " + d);
            System.out.println("test took (millis): " + dur + " to processes size: " + d.length());

            Assert.assertTrue("too slow: " + dur, dur < 25000); //ave is 3500 millis with normal java collections

        } catch (java.lang.OutOfMemoryError ooe) {
            ooe.printStackTrace();
            fail(ooe.toString());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testSpeed2() {

        //xpath lookup

        //if (true) { return; }

        try {
            Assert.assertNotNull("nul m_str", m_newstr);

            DataObject data = XmlCoder.decode(new java.io.StringReader(m_newstr));

            long starttime = System.currentTimeMillis();

            for (int i = 0; i < 500; i++) {

		Assert.assertTrue("missing time", data.isSet("./fld_" + i + "/item/time"));
		Assert.assertFalse("found something not there", data.isSet("./fld_" + i + "/item/date"));
		Assert.assertTrue("missing iter " + i, data.isSet("./fld_" + i + "/item/message[. = 'i am iteration " + i + "']"));

                //data.setLong("./fld_" + i + "/item/time", System.currentTimeMillis());
                //data.setString("./fld_" + i + "/item/message", "i am iteration " + i);
            }

            long stoptime = System.currentTimeMillis();

            long dur = stoptime - starttime;

	    String d = XmlCoder.print(data);
            //System.out.println("last iter of new data: " + d);
            System.out.println("test took (millis): " + dur + " to processes size: " + d.length());

            Assert.assertTrue("too slow: " + dur, dur < 25000); //ave is 3500 millis with normal java collections

        } catch (java.lang.OutOfMemoryError ooe) {
            ooe.printStackTrace();
            fail(ooe.toString());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testSpeed3() {

	 try {

         InputStream res = this.getClass().getResourceAsStream("/test/xmpp_biggest.xml");
         org.junit.Assert.assertNotNull(res);
         DataObject data = XmlCoder.decode(new InputStreamReader(res));

	     long starttime = System.currentTimeMillis();

	     for (int i = 0; i < 500; i++) {

		 int hc = data.hashCode();

		 Assert.assertTrue("bad hc: " + hc, hc != -1);
	     }

	     long stoptime = System.currentTimeMillis();

	     long dur = stoptime - starttime;

	     String d = XmlCoder.print(data);

	     System.out.println("hc test took (millis): " + dur + " to processes size: " + d.length());

	     Assert.assertTrue("hc too slow: " + dur, dur < 1000);

	     int oldhc = data.hashCode();
	     data.setString("./my/test/field", "hiya");
	     int newhc = data.hashCode();
	     Assert.assertTrue("hc did not change", oldhc != -1);
	     Assert.assertTrue("hc did not change", newhc != -1);
	     Assert.assertTrue("hc did not change", oldhc != newhc);

	 } catch (java.lang.OutOfMemoryError ooe) {
	     ooe.printStackTrace();
	     fail(ooe.toString());

	 } catch (Exception e) {
	     e.printStackTrace();
	     fail(e.toString());
	 }
     }
}

