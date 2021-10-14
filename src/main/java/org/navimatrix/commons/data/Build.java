package org.navimatrix.commons.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * keep track of the version and build numbers
 */
public class Build {

    /** Current Version of VersionTracker.*/ 
    private static final String version = "0.24.0";

    /** Current Version of VersionTracker.*/
    private static final String build = "2006122801";


    public static void main (String[] args) {
        //System.out.println("Version: " + version);
        //System.out.println("Build: " + build);
	Log log = LogFactory.getLog(Build.class.getName());
	log.info("Navicore serverbase version " + version + " build " + build);
    }
}
