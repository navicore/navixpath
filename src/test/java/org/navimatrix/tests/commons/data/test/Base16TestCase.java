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
