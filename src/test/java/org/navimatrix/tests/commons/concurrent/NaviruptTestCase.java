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
package org.navimatrix.tests.commons.concurrent;

import org.navimatrix.commons.concurrent.Navirupt;
import org.navimatrix.commons.concurrent.NaviruptException;
import org.navimatrix.commons.data.DataFactory;
import org.navimatrix.commons.data.DataObject;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NaviruptTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {

        return new TestSuite(NaviruptTestCase.class);
    }

    // 
    // begin tests
    //

    public void testInterupt() throws Exception {

        Runme r = new Runme();

        Thread t = new Thread(r);

        t.start();

        Thread.sleep(1000);

        Navirupt.interupt(t);

        t.join();

        Assert.assertEquals("wrong exception", NaviruptException.class, r.error.getClass());
    }

    public void testInteruptedSleep() throws Exception {

        RunmeSleeper r = new RunmeSleeper();

        Thread t = new Thread(r);

        t.start();

        Thread.sleep(1000);

        Navirupt.interupt(t);

        t.join();

        Assert.assertEquals("wrong exception", java.lang.InterruptedException.class, r.error.getClass());
    }

    public void testInteruptedDataObjectSet() throws Exception {

        RunmeDataObjectSet r = new RunmeDataObjectSet();

        Thread t = new Thread(r);

        t.start();

        Thread.sleep(1000);

        Navirupt.interupt(t);

        t.join();

        Assert.assertEquals("wrong exception", NaviruptException.class, r.error.getClass());
    }

    public void testInteruptedDataObjectPath() throws Exception {

        RunmeDataObjectPath r = new RunmeDataObjectPath();

        Thread t = new Thread(r);

        t.start();

        Thread.sleep(1000);

        Navirupt.interupt(t);

        t.join();

        Assert.assertEquals("wrong exception", NaviruptException.class, r.error.getClass());
    }

    //
    // end tests
    //

    private class Runme implements Runnable {

        volatile Throwable error = null;

        public void run() {

            int count = 0;

            try {

                String msg = "";

                for (;;) {

                    count++;

                    msg = msg + "_" + count;

                    Navirupt.check();
                }

            } catch (Throwable err) {

                error = err;
            }
        }
    }

    private class RunmeDataObjectSet implements Runnable {

        volatile Throwable error = null;

        public void run() {

            int count = 0;

            try {

                DataObject data = DataFactory.createDataObject("", "naviruptme");

                for (;;) {

                    count++;

                    DataObject one = data.createDataObject("one", "", "");
                    one.setInt("id", 1);
                }

            } catch (Throwable err) {

                error = err;
            }
        }
    }

    private class RunmeDataObjectPath implements Runnable {

        volatile Throwable error = null;

        public void run() {

            int count = 0;

            try {

                DataObject data = DataFactory.createDataObject("", "naviruptme");
                DataObject one = data.createDataObject("one", "", "");
                one = data.createDataObject("one", "", "");
                one = data.createDataObject("one", "", "");

                for (;;) {

                    count++;

                    data.getList("./one");
                }

            } catch (Throwable err) {

                error = err;
            }
        }
    }

    private class RunmeSleeper implements Runnable {

        volatile Throwable error = null;

        public void run() {

            int count = 0;

            try {

                DataObject data = DataFactory.createDataObject("", "naviruptme");

                for (;;) {

                    count++;

                    Thread.sleep(10000);
                }

            } catch (Throwable err) {

                error = err;
            }
        }
    }
}

