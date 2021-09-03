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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.navimatrix.commons.data.Coder;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.PropertiesCoder;
import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.XmlCoder;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PropertiesCoderTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(PropertiesCoderTestCase.class);
    }

    // 
    // begin tests
    //

    public void testLoad() throws Exception {

        InputStream fis = this.getClass().getResourceAsStream("/test/infile.properties");
        org.junit.Assert.assertNotNull(fis);

        Coder coder = new PropertiesCoder();

        DataObject data = coder.decodeSdo(fis);

        Assert.assertTrue("badload no xml", data.toString().indexOf("<properties xmlns=") > -1);
        Assert.assertTrue("badload no comment 0: " + data, data.toString().indexOf("<p1 comment=\"# i am a test file") > -1);
        Assert.assertTrue("badload no p1: " + data, data.toString().indexOf(">property number one</p1>") > -1);
        //Assert.assertTrue("badload no comment 3: " + data, data.toString().indexOf("<navi_comments_3></navi_comments_3></properties>") > -1);
    }

    public void testSave() throws Exception {

        Coder coder = new PropertiesCoder();

        InputStream fis = this.getClass().getResourceAsStream("/test/infile.properties");
        org.junit.Assert.assertNotNull(fis);

        DataObject data = coder.decodeSdo(fis);

        fis.close();

        data.setString("p2", "property number two");
        data.setString("p3", "property number three");
        data.setString("pfour", "property number 4");
        data.setString("p5", "property number five");
        data.setString("p0", "property 2nd last");
        data.setString("pa_comment", "#pa should be the last property in the file");
        data.setString("pa", "property last");

        File f = new File("/tmp/outfile.properties");

        if (f.exists()) {

            f.delete();
        }

        OutputStream fos = new FileOutputStream(f);

        coder.encodeSdo(data, fos);

        fos.close();

        fis = new FileInputStream(f);

        data = coder.decodeSdo(fis);

        //Assert.assertEquals("badload no pa_comment\n" + XmlCoder.print(data), data.toString().indexOf("<pa comment=\"#pa should be the last property in the file"), 260);

        Assert.assertTrue("badload no pa\n" + XmlCoder.print(data), data.toString().indexOf(">property last</pa>") > -1);
    }


    public void testUpdate() throws Exception {

        Coder coder = new PropertiesCoder();

        File f = new File("/tmp/outfile.properties");

        Assert.assertTrue("missing file", f.exists());

        InputStream fis = new FileInputStream(f);

        DataObject data = coder.decodeSdo(fis);

        fis.close();

        data.setString("./pfour", "property number four");
        data.setString("./pb", "new last property");

        OutputStream fos = new FileOutputStream(f);

        coder.encodeSdo(data, fos);

        fos.close();

        fis = new FileInputStream(f);

        data = coder.decodeSdo(fis);

        //Assert.assertEquals("badload no pa_comment: " + data, data.toString().indexOf("#pa should be the last property in the file"), 276);

        Assert.assertFalse("badload found pfour: " + data, data.toString().indexOf("<pfour>property number 4</pfour>") > -1);

        Assert.assertTrue("badload no pfour: " + data, data.toString().indexOf("<pfour>property number four</pfour>") > -1);

        //Assert.assertTrue("badload no comment 4: " + data, data.toString().indexOf("<navi_comments_4></navi_comments_4>") > -1);

        Assert.assertTrue("badload no pb: " + data, data.toString().indexOf("</pa><pb>new last property</pb></properties>") > -1);
    }

    // 
    // end tests
    //
}

