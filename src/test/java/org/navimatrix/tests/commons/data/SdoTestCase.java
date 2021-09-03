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

import org.junit.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.navimatrix.commons.data.*;
import org.navimatrix.commons.data.impl.HashList;
import org.navimatrix.commons.data.sdoimpl.BasicDataGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SdoTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(SdoTestCase.class);
    }

    // 
    // begin tests
    //

    public void testMap() {

        // root
        //   intfld (int)           0
        //   record (data object)   0
        //     intfld (int)i        0


        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            //root.createDataObject("intfld");
            root.setInt("intfld", 99);
            int r1 = root.getInt("intfld");
            Assert.assertEquals("bad int value: " + r1, 100, r1 + 1);

            DataObject record = root.createDataObject("rec1");
            record.setInt("intfld", 2);

            DataObject r = root.getDataObject("rec1");
            int r2 = r.getInt("intfld");
            Assert.assertEquals("bad int sum: " + r2, 101, r1 + r2);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testProperty() {
        // root
        //   intfld (int)           0
        //   record (data object)   0
        //     intfld (int)i        0

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            root.setInt("intfld", 99);

            DataObject record = root.createDataObject("rec1");
            record.setInt("intfld", 2);

            List props = graph.getRootObject().getType().getProperties();
            Property p0 = (Property) props.get(0);
            Assert.assertEquals("wrong prop names", p0.getName(), "intfld");
            Property p1 = (Property) props.get(1);
            Assert.assertEquals("wrong prop names", p1.getName(), "rec1");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testToString() {
        // root
        //   intfld (int)           0
        //   record (data object)   1
        //     intfld (int)i        0

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");

            DataObject r1;
            r1 = root.createDataObject("record");
            Assert.assertTrue("record not a data object", root.get("record") instanceof DataObject);
            r1.setInt("intfld", 101);
            r1.setInt("intfld2", 202);

            Assert.assertTrue(root.toString().indexOf("101") > -1);
            Assert.assertTrue(root.toString().indexOf("202") > -1);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testDelete() {

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            root.setInt("f1", 99);
            root.setInt("f2", 99);
            root.createDataObject("f3");

            Assert.assertTrue("bad set", root.isSet("f1"));
            Assert.assertTrue("bad set", root.isSet("f3"));
            Assert.assertEquals("bad delete",  3, root.getType().getProperties().size());

            root.delete();

            Assert.assertFalse("bad delete", root.isSet("f1"));
            Assert.assertFalse("bad delete", root.isSet("f3"));
            Assert.assertEquals("bad delete",  0, root.getType().getProperties().size());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testPropertyIndex() {
        // root
        //   intfld (int)           0
        //   record (data object)   0
        //     intfld (int)i        0

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            root.setInt("intfld", 99);

            DataObject record = root.createDataObject("rec1");
            record.setInt("intfld", 3);
            //record.getDataObject("intfld").delete(); //else it turns into isMany
            record.unset("intfld");
            record.setInt(0, 4); //reset by idx

            int r1 = root.getInt(0);
            DataObject d1 = root.getDataObject(1);
            int r2 = d1.getInt(0);

            Assert.assertTrue("bad int sum", r1 + r2 == 103);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //assumes old auto ismany
    ///*
    public void xestIsMany() {
        // root
        //   record (data object)   1
        //     intfld (int)i        0

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            List l = new ArrayList();
            root.setList("records", l);
            //warning, this is a hack because the sdo api doesn't seem to support
            //  a DataObject factory for isMany situations
            //  perhaps Sequence is for this stuff too?  But sequence doesn't have a DataObject factory
            //  either.  mystery...
            //todo: too big an oversight... must have to redo with sequence
            root.createDataObject("records");
            root.createDataObject("records");
            root.createDataObject("records").setString("msg", "hi");

            List recs = root.getList("records");

            Assert.assertEquals("missing records", recs.size(), 3);
            List rl = root.getList(0);
            DataObject dobj = (DataObject) rl.get(2);
            String msg = dobj.getString(0);
            Assert.assertEquals("wrong record", msg, "hi");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void xestAutoIsMany() {
        // root
        //   record (data object)   1
        //     intfld (int)i        1

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            root.setLong("long", 100);
            root.setLong("long", 200);
            root.setLong("long", 300);
            root.setLong("long", 400);

            int l1 = DataUtil.getInteger(root.getList("long").get(0)).intValue();
            int l2 = DataUtil.getInteger(root.getList("long").get(1)).intValue();
            int l3 = DataUtil.getInteger(root.getList("long").get(2)).intValue();
            int l4 = DataUtil.getInteger(root.getList("long").get(3)).intValue();

            boolean works = false;
            try {
                int l5 = DataUtil.getInteger(root.getList("long").get(4)).intValue(); //array oob
            } catch (java.lang.IndexOutOfBoundsException e) {
                works = true;
            }

            Assert.assertTrue("auto array failed", works);

            Assert.assertEquals("wrong long l1: ", l1 + l2 + l3 + l4, 1000);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    //*/

    public void testGetListIsMany() {
        // root
        //   record (data object)   1
        //     intfld (int)i        1

        try {
            DataGraph graph = new BasicDataGraph();
            DataObject root = graph.createRootObject("myuri", "testroot");
            root.setLong("longer", 80);
            root.setLong("longer", 90);
            root.setLong("long", 100);
            root.setLong("long", 200);
            root.setLong("long", 300);
            root.setLong("long", 400);
            root.setLong("longer", 500);
            root.setLong("long", 600);
            root.setLong("long", 700);
            root.setLong("longer", 800);
            root.setLong("long", 900);
            root.setLong("long", 1000);
            root.setLong("longer", 1100);

            List l = root.getList("long");

            Assert.assertEquals("missing items: " + l.size(), l.size(), 8);

            l = root.getList("longer");

            Assert.assertEquals("missing items: " + l.size(), l.size(), 5);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testXml2Sdo() {

        try {
            InputStream res = this.getClass().getResourceAsStream("/test/test1.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            String subject = data.getDataObject("message").getString("subject");
            Assert.assertEquals("Hi", subject);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testXmlAttrs() {

        try {
            InputStream res = this.getClass().getResourceAsStream("/test/test1.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            String subject = data.getDataObject("message").getString("subject");
            Assert.assertEquals("Hi", subject);

            String from = data.getDataObject("message").getString("@from");
            Assert.assertEquals("ed@edsweeney.net", from);

            String interesting = data.getDataObject("message").getDataObject("subject").getString("@interesting");
            //String interesting = data.getString("/message/subject");
            Assert.assertEquals("not interesting: " + data, "yes", interesting);

            //make sure no funny biz, that it works twice!
            subject = data.getDataObject("message").getString("subject");
            Assert.assertEquals("Hi", subject);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testSdoToXml() {

        try {

            InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            String subject = data.getDataObject("message").getString("subject");
            Assert.assertEquals("Hi", subject);

            StringWriter sw = new StringWriter();
            XmlCoder.encode(data, sw);

            //System.out.println("--- xml ---");
            //System.out.println(sw.toString());

            //test for element and attribute of subject
            Assert.assertTrue("xml wrong: " + sw.toString(), sw.toString().indexOf("interesting=\"yes\">Hi</subject>") > -1);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }

        //todo: problem will be rewriting attributes...
    }

    public void testPathQuery() {
        try {
            InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            Assert.assertNotNull("xpath returned null", data.get("/message/subject"));

            Assert.assertEquals("wrong root node name",
                                "stream",
                                data.getDataObject("/").getType().getName());

            Assert.assertEquals("wrong . node",
                                data,
                                data.getDataObject("."));

            Assert.assertEquals("wrong . node",
                                data,
                                data.getDataObject("/message/.."));

            Assert.assertEquals("wrong xpath return data", "Hi", data.getString("/message/subject"));

            ///*
            Assert.assertEquals("position() broken",
                                "filethree.txt",
                                data.getString("/message/attatchments/filename[position() = 3]"));

            Assert.assertEquals("reletive path broken",
                                "filethree.txt",
                                data.getDataObject("./message/attatchments").getString("./filename[position() = 3]"));

            Assert.assertEquals("attribute path broken",
                                1001,
                                data.getLong("/message/attatchments/filename[position() = 2]/@age"));

            Assert.assertEquals("attribute path broken",
                                "ed@edsweeney.net",
                                data.getString("/message/@from"));

            DataObject leaf = data.getDataObject("./message/attatchments/filename[position() = 1]");
            Assert.assertEquals("attribute path broken",
                                "ed@edsweeney.net",
                                leaf.getString("/message/@from"));

            leaf = data.getDataObject("message/attatchments/filename[position() = 1]");
            Assert.assertEquals("attribute path broken",
                                "ed@edsweeney.net",
                                leaf.getString("/message/@from"));

            Assert.assertEquals("attribute path broken",
                                "filethree.txt",
                                data.getString("/message/attatchments/filename[@age = 2]"));

            Assert.assertEquals("attribute path broken",
                                "filethree.txt",
                                data.getString("/message[@to = 'ed@blazebot.com']/attatchments/filename[@age = 2]"));

            Assert.assertEquals("attribute path broken",
                                "filetwo.txt",
                                data.getString("//filename[@age = 1001]"));

            Assert.assertNull("attribute path broken",
                              data.getString("//filename[@age = 9999]"));

            List list = data.getList("//filename");
            Assert.assertEquals("wrong count of filename(s)", 3, list.size());

            Assert.assertEquals("count() broken", 3,
                                (long)data.getDouble("count(//filename)"));

            Assert.assertEquals("count() broken",
                                1,
                                (long)data.getDouble("count(//subject)"));
            //*/

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testUnset() {

        DataObject data = DataFactory.createDataObject("test", "data");

        //set fields
        data.setString("/my/node/is/here", "seeme");
        data.setString("/my/node/is/alsohere", "seeme too");
        Assert.assertTrue("bad", data.isSet("/my/node/is/here"));
        Assert.assertTrue("bad", data.isSet("/my/node/is/alsohere"));

        //unset fields
        data.unset("/my/node/is/*");
        Assert.assertFalse("bad", data.isSet("/my/node/is/here"));
        Assert.assertFalse("bad", data.isSet("/my/node/is/alsohere"));

        //set fields again
        data.setString("/my/node/is/here", "seeme");
        data.setString("/my/node/is/alsohere", "seeme too");
        Assert.assertTrue("bad", data.isSet("/my/node/is/here"));
        Assert.assertTrue("bad", data.isSet("/my/node/is/alsohere"));
    }

    ///*
    public void testList() {

        try {
            InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            Assert.assertEquals("position() broken",
                                "3.0",
                                data.getString("count(/message/attatchments/filename)"));

            Assert.assertEquals("position() broken", 3.0, 3.0, data.getDouble("count(/message/attatchments/filename)"));

            Assert.assertEquals("position() broken", 3, data.getInt("count(/message/attatchments/filename)")); // bug

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    //*/


    public void testRootPathQuery() {
        try {

            InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            Assert.assertTrue("path did not work", data.isSet("/@id"));

            Assert.assertTrue("path did not work", data.isSet(".[@id = '3C0FB738']"));

            //npe!
            //Assert.assertTrue("path did not work", data.isSet(".[. = 'stream']"));

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testPathUpdate() {
        try {
            //String filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "test2.xml";

            //load xml
            //DataObject data = XmlCoder.decode(new FileReader(filename));

            InputStream res = this.getClass().getResourceAsStream("/test/test2.xml");
            Assert.assertNotNull(res);
            DataObject data = XmlCoder.decode(new InputStreamReader(res));

            //update existing fields with new values
            data.setString("//filename[@age = 1001]", "someNewName.txt");
            ///*
            data.setInt("//filename[. = 'someNewName.txt']/@age", 9);

            Assert.assertEquals("set broken",
                                "someNewName.txt",
                                data.getString("//filename[position() = 2]"));

            Assert.assertEquals("set broken",
                                9,
                                data.getInt("//filename[. = 'someNewName.txt']/@age"));

            StringWriter sw = new StringWriter();
            XmlCoder.encode(data, sw);

            //System.out.println("--- xml ---");
            //System.out.println(sw.toString());

            Assert.assertTrue("xml wrong",
                              sw.toString().indexOf("age=\"9\">someNewName.txt</filename>") > -1);
            //*/

            /*
            boolean success = false;
            try {
                data.setString("/message", "error");
            } catch (java.lang.IllegalAccessError e) {
                success = true;
            }
            Assert.assertTrue("non-leaf node update did not fail properly", success);
            */

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testPathCreate() {
        try {

            DataObject data = DataFactory.createDataObject("jabber:client", "iq");
            data.set("/username", "edwared");

            data.set("/query/some/deep/field/@height", "44");
            data.set("/query/some/deep/field/one", "1");
            data.set("/query/some/deep/field/two", "2");
            data.set("/query/some/deep/field/two/@color", "orange");
            data.set("/query/some/deep/@weight", "100");
            data.set("/query/somemore/value", "1");
            DataObject root = data.getDataObject("/query/somemore");

            //bad inconsistency in api.  xpath set updates but non xpath set adds
            // best way to do an add is to query for a List, then add.  makes code understandable
            // and lets the api stay like this, which is good for auto isMany while processing
            // arbitrary xml.
            root.set("value", "2");
            root.set("value", "3");
            root.set("value", "4");
            ///*
            data.set("/query/somemore/value[. = '3']", "3300");

            Assert.assertEquals("bad data construction", 3300, root.getLong("/query/somemore/value[position() = 3]"));

            //System.out.println("data: " + data);

            StringWriter sw = new StringWriter();
            XmlCoder.encode(data, sw);

            //System.out.println("--- xml from path ---");
            //System.out.println(sw.toString());

            Assert.assertTrue("bad xml construction", sw.toString().indexOf("<field height=\"44\">") > -1);
            Assert.assertTrue("bad xml construction", sw.toString().indexOf("<value>3300</value>") > -1);
            //*/

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testCloneable() {
        try {

            DataObject orig = DataFactory.createDataObject("", "Dr_Evil");
            orig.setString("/some/deep/field/@height", "tall");

            DataObject minime = (DataObject) orig.clone();

            String origxml = XmlCoder.print(orig);
            String minimexml = XmlCoder.print(minime);
            Assert.assertEquals("bad clone", origxml, minimexml);

            minime.setString("/some/deep/field/@height", "short");

            origxml = XmlCoder.print(orig);
            minimexml = XmlCoder.print(minime);
            Assert.assertNotSame("shallow clone", origxml, minimexml);

            //System.out.println("dr evil:\n" + origxml);
            //System.out.println("minime:\n" + minimexml);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testEquals() {

        DataObject one = DataFactory.createDataObject("edland", "see");
        one.setString("who", "me");

        DataObject two = DataFactory.createDataObject("edland", "see");
        two.setString("who", "me");

        Assert.assertNotSame("should be !=", one, two);
        Assert.assertEquals("should be equal", one, two);
    }

    public void testEqualsWithList() {

        DataObject one = DataFactory.createDataObject("edland", "see");
        one.setString("who", "me");

        DataObject two = DataFactory.createDataObject("edland", "see");
        two.setString("who", "me");

        Assert.assertEquals("should be equal", one, two);

        two.getList("who");

        Assert.assertEquals("should be equal", one, two);

        Assert.assertNotSame("should be !=", one, two);

        //double check to make sure everything is not always equals
        DataObject three = DataFactory.createDataObject("edland", "see");
        three.setString("who", "me too");

        Assert.assertNotSame("should be !=", one, three);
        Assert.assertNotSame("should be !=", two, three);
    }

    public void xestCoderIfc() {

        Coder coder = new XmlCoder();

        try {
            String filename = System.getProperty("org.navimatrix.testXmlFileDir")  + File.separator + "test2.xml";
            InputStream is = new FileInputStream(new File(filename));

            DataObject data = coder.decodeSdo(is);

            String subject = data.getDataObject("message").getString("subject");

            Assert.assertEquals("Hi", subject);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            coder.encodeSdo(data, bos);
            //?????????? bug ??????????
            byte[] bytes = bos.toByteArray();
            Assert.assertTrue("not enough bytes", bytes.length > 0);
            String result = new String(bytes, "utf8");
            System.out.println("result: " + result);

            Assert.assertTrue("xml wrong", result.indexOf("interesting=\"yes\">Hi</subject>") > -1);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }


    public void testNumericSort() {

        List list = new ArrayList();

        DataObject d0 = DataFactory.createDataObject("", "");
        d0.setString("/some/othervalue", "0000000");
        list.add(d0);

        DataObject d1 = DataFactory.createDataObject("", "");
        d1.setString("/some/value", "0000001");
        list.add(d1);

        DataObject d2 = DataFactory.createDataObject("", "");
        d2.setLong("/some/value", 5);
        list.add(d2);

        DataObject d3 = DataFactory.createDataObject("", "");
        d3.setString("/some/value", "0005");
        list.add(d3);

        DataObject d4 = DataFactory.createDataObject("", "");
        d4.setDouble("/some/value", 2);
        list.add(d4);

        List result = DataUtil.sortByNumbers(list, "/some/value");

        Assert.assertEquals("bad sort", d0, result.get(0));
        Assert.assertEquals("bad sort", d1, result.get(1));
        Assert.assertEquals("bad sort", d4, result.get(2));
        Assert.assertEquals("bad sort", d2, result.get(3));
        Assert.assertEquals("bad sort", d3, result.get(4)); //nulls to the end
    }

    public void testStringSort() {

        List list = new ArrayList();

        DataObject d0 = DataFactory.createDataObject("", "");
        d0.setString("/some/othervalue", "0000000");
        list.add(d0);

        DataObject d1 = DataFactory.createDataObject("", "");
        d1.setString("/some/value", "0000001");
        list.add(d1);

        DataObject d2 = DataFactory.createDataObject("", "");
        d2.setLong("/some/value", 5);
        list.add(d2);

        DataObject d3 = DataFactory.createDataObject("", "");
        d3.setString("/some/value", "0005");
        list.add(d3);

        DataObject d4 = DataFactory.createDataObject("", "");
        d4.setDouble("/some/value", 2);
        list.add(d4);

        List result = DataUtil.sortByStrings(list, "/some/value");

        Assert.assertEquals("bad sort", d0, result.get(0));
        Assert.assertEquals("bad sort", d1, result.get(1));
        Assert.assertEquals("bad sort", d3, result.get(2));
        Assert.assertEquals("bad sort", d4, result.get(3));
        Assert.assertEquals("bad sort", d2, result.get(4));//nulls to the end
    }

    public void testCopy() {

        DataObject orig = DataFactory.createDataObject("", "orig");
        orig.setString("/one", "Once");
        orig.setString("/two", "Twice");
        orig.setInt("/three", 3);

        DataObject copy = DataFactory.createDataObject("", "copy");

        DataUtil.copyDataObject(orig, copy);

        Assert.assertTrue("bad copy", orig.getString("/two").equals(copy.getString("/two")));

        orig.setString("/two", "break me");

        Assert.assertFalse("copy not deep", orig.getString("/two").equals(copy.getString("/two")));
    }

    public void testRoundTripWithNS() {

        Coder coder = new XmlCoder(false);
        Coder pcoder = new XmlCoder(true);

        try {

            InputStream is = this.getClass().getResourceAsStream("/test/soap.xml");
            DataObject data = coder.decodeSdo(is);
            is.close();

            //System.out.println("data: " + data);

            is = this.getClass().getResourceAsStream("/test/soap.xml");
            DataObject pdata = pcoder.decodeSdo(is);
            is.close();

            //System.out.println("data w/p: " + XmlCoder.print(pdata));

            Assert.assertEquals("not equal", data, pdata);

            pdata.setString("msg", "hi");

            Assert.assertFalse("should not be equal", data.equals(pdata));

            //can't do a compair of xml round trip because sdo impl eats bad and stupid ns declairations

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }


    public void testPropList() {

        DataObject orig = DataFactory.createDataObject("", "orig");
        orig.setString("b", "Once");
        orig.setString("c", "Twice");
        orig.setInt("a", 3);
        orig.setInt("D", 4);
        orig.setInt("E", 5);
        orig.setInt("FA", 6);
        orig.setInt("Dcc", 7);
        orig.setInt("Dbb", 0);
        orig.setInt("Daa", 9);
        orig.setInt("D00", 8);
        orig.setInt("DDDDD", 1000);
        orig.setInt("DDDD", 100);

        List items = new ArrayList();
        List props = orig.getType().getProperties();
        for (Iterator iter = props.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (orig.isSet(p)) {
                String item = orig.getString(p);
                items.add(item);
            }
        }

        Assert.assertEquals("error", items.get(0), "Once");
        Assert.assertEquals("error", items.get(1), "Twice");
        Assert.assertEquals("error", items.get(2), "3");
        Assert.assertEquals("error", items.get(9), "8");

        items = orig.getList("./*");

        //todo: bug in sdo.  order should be by properties order, not the map that backs sdo

        //getList sorts these in reverse alphabetical order ?!!
        Assert.assertEquals("path error", "Once", items.get(0).toString());
        //Assert.assertEquals("path error", "Twice", items.get(1).toString());
        //Assert.assertEquals("path error", "3", items.get(2).toString());
        //Assert.assertEquals("path error", "8", items.get(9).toString());
    }

    public void testPropListIterator() {

        DataObject orig = DataFactory.createDataObject("", "orig");
        orig.setString("/color", "red");
        orig.setString("/item", "one");
        orig.setString("/all/item", "two");
        orig.getDataObject("/all").setString("item", "three");
        orig.getDataObject("/all").setString("item", "four");

        Assert.assertTrue("missing one", orig.toString().indexOf(">one<") > -1);
        Assert.assertTrue("missing three", orig.toString().indexOf(">three<") > -1);

        List list = orig.getList("//item");

        for (Iterator iter = list.iterator(); iter.hasNext();) {

            Object item = iter.next();

            if (
               item.toString().equals("one") ||
               item.toString().equals("three")
               ) {
                iter.remove();
            }
        }

        Assert.assertTrue("missing one", orig.toString().indexOf(">one<") == -1);
        //Assert.assertTrue("missing three", orig.toString().indexOf(">three<") == -1);
    }

    public void testNoBytesNoNPE() {

        DataObject orig = DataFactory.createDataObject("", "orig");
        byte[] iamnull = orig.getBytes("/bytes");
        Assert.assertNull("should be null, not npe", iamnull);
    }

    public void testOrder() {

        DataObject data = DataFactory.createDataObject("eds", "data");

        data.setInt("one", 11);
        data.setInt("two", 22);
        data.setInt("one", 33);
        data.setString("one", "44");

        String str = data.toString();

        int pos1 = str.indexOf("11");
        int pos2 = str.indexOf("22");
        int pos3 = str.indexOf("33");
        int pos4 = str.indexOf("44");

        Assert.assertTrue("bad order", pos1 < pos2);
        Assert.assertTrue("bad order", pos2 < pos3);
        Assert.assertTrue("bad order", pos3 < pos4);

        List list = data.getList("//one");
        Assert.assertEquals("bad list", list.size(), 3);
        list.remove(2); //remove 3rd one, '33'
        list = data.getList("//one");

        Assert.assertEquals("bad list", list.size(), 2);

        list = data.getList("//one[. = '44']");
        Assert.assertNotNull(list);
        Assert.assertEquals("bad list", list.size(), 1);

        int res = data.getInt("//one[position() = 2]");
        Assert.assertEquals("bad result: " + res, 44, res);

        res = data.getInt("/*[position() = 3]");
        Assert.assertEquals("bad result: " + res, 44, res);
    }

    private static String XML =
        "<root>\n"+
        "    <result>\n"+
        "        <foo>\n"+
        "            <item>a</item>\n"+
        "            <item>b</item>\n"+
        "        </foo>\n"+
        "        <bar>\n"+
        "            <item>c</item>\n"+
        "            <item>d</item>\n"+
        "        </bar>\n"+
        "        <foo>\n"+
        "            <item>e</item>\n"+
        "            <item>f</item>\n"+
        "        </foo>\n"+
        "        <bar>\n"+
        "            <item>g</item>\n"+
        "            <item>h</item>\n"+
        "        </bar>\n"+
        "    </result>\n"+
        "</root>\n";


    public void testDeepOrder() throws Exception {

        Reader in = new StringReader(XML);

        DataObject data = XmlCoder.decode(in);

        //System.out.println(XmlCoder.print(data));

        List list = data.getList("//item");

        for (Iterator iter = list.iterator(); iter.hasNext();) {

            DataObject fld = (DataObject) iter.next();
            //System.out.println("got: " + fld);
        }

    }

    public void testIsDirty() throws Exception {

        DataObject data = DataFactory.createDataObject("eds", "data");

        Assert.assertFalse(data.getDataGraph().isChanged());

        data.setInt("one", 11);
        data.setInt("two", 22);

        Assert.assertTrue(data.getDataGraph().isChanged());

        data.getDataGraph().setChanged(false);

        Assert.assertFalse(data.getDataGraph().isChanged());

        DataObject fld = data.createDataObject("fld");
        Assert.assertFalse(data.getDataGraph().isChanged());
        fld.setString("msg", "see me");
        Assert.assertTrue(data.getDataGraph().isChanged());

        data.getDataGraph().setChanged(false);
        Assert.assertFalse(data.getDataGraph().isChanged());

        fld.setString("./msg", "see me now?");
        Assert.assertTrue(data.getDataGraph().isChanged());
        data.getDataGraph().setChanged(false);
        fld.setString("./msg", "see me now?");
        Assert.assertFalse(data.getDataGraph().isChanged()); //don't mark == or equals objects as changed
        fld.setString("./msg", "see me really change?");
        Assert.assertTrue(data.getDataGraph().isChanged());
    }

    public void testHashList() throws Exception {

        List oldl = new ArrayList();

        for (int i = 0; i < 20000; i++) {

            oldl.add(new Thing(String.valueOf(i)));
        }

        long starttime = System.currentTimeMillis();
        for (int i = 20000; i > 0; i--) {

            oldl.contains(new Thing(String.valueOf(i)));
        }
        long stoptime = System.currentTimeMillis();


        List newl = new HashList();

        for (int i = 0; i < 20000; i++) {

            newl.add(new Thing(String.valueOf(i)));
        }

        starttime = System.currentTimeMillis();
        for (int i = 20000; i > 0; i--) {

            newl.contains(new Thing(String.valueOf(i)));
        }
        stoptime = System.currentTimeMillis();

    }

    public void testGraphCharCount() throws Exception {

        DataObject data = DataFactory.createDataObject("", "test");

        Assert.assertEquals("not empty", data.getDataGraph().charCount(), (long) 0);

        InputStream res = this.getClass().getResourceAsStream("/test/test1.xml");
        Assert.assertNotNull(res);
        data = XmlCoder.decode(new InputStreamReader(res));

        String xml = data.toString();

        System.out.println("xml1: " + xml);
        xml = xml.replaceAll("</", "");
        xml = xml.replaceAll("<", "");
        xml = xml.replaceAll("/>", "");
        xml = xml.replaceAll(">", "");
        xml = xml.replaceAll("/n", "");
        System.out.println("xml2: " + xml);

        Assert.assertTrue("not equal", data.getDataGraph().charCount() > 80);
    }



    //
    // end tests
    //

    //
    // begin helper methods and classes
    //
    private static class Thing {

        public final String m_field;

        public Thing(String field) {

            m_field = field;
        }
    }
}

