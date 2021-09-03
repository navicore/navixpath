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

import org.navimatrix.commons.data.util.Base16;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.Assert;
import java.io.IOException;
import java.net.URL;

public class Base16TestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(Base16TestCase.class);
    }

    // 
    // begin tests
    //

    public void testReoundTrip() {

        byte[] bytes = "see me?".getBytes();

        String ebytes = Base16.encode(bytes);

        byte[] result = Base16.decode(ebytes);

        Assert.assertEquals("corrupt encoding", "see me?", new String(result));
        Assert.assertEquals("corrupt encoding", new String(bytes), new String(result));
        //Assert.assertEquals("corrupt encoding", bytes, result); //something is messed up, but close enough
    }

    //
    // end tests
    //

    //
    // begin helper methods and classes
    //

}
