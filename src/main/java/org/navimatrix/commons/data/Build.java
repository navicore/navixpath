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
