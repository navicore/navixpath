/*
 * Blazebot Computing
 * Licensed Materials - Property of Ed Sweeney
 * Copyright Ed Sweeney 2004, 2005, 2006, 2007. All rights reserved.
 *
 * @author Ed Sweeney <a href="http://www.edsweeney.net">
 */
package org.navimatrix.commons.concurrent;

/**
 * keep track of the version and build numbers
 */
public class Build {

    /** Current Version of VersionTracker.*/
    private static final String version = "0.24.0";

    /** Current Version of VersionTracker.*/
    private static final String build = "2006122801";


    public static void main (String[] args) {
        System.out.println("Version: " + version);
        System.out.println("Build: " + build);
    }
}
