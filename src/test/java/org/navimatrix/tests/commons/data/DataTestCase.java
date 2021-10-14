package org.navimatrix.tests.commons.data;

//import org.navimatrix.commons.data.Data;
import org.navimatrix.commons.data.DataUtil;
//import org.navimatrix.commons.data.DataFactory;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import junit.framework.Test;
//import junit.framework.Assert;
import java.io.IOException;
import java.net.URL;

public class DataTestCase extends TestCase {

    protected void tearDown() {
    }

    protected void setUp() {
    }

    public static Test suite() {
        return new TestSuite(DataTestCase.class);
    }

    // 
    // begin tests
    //

    public void testResource2String() {

        try {

            URL res = DataTestCase.class.getResource("test/test.txt");
            String ts = DataUtil.resource2String(res);

            if (ts != null && !ts.startsWith("see me?")) {
                fail("wrong text in resource");
            }

        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    //
    // end tests
    //

    //
    // begin helper methods and classes
    //

}
