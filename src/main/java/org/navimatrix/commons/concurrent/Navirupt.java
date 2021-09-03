/*
 * Blazebot Computing
 * Licensed Materials - Property of Ed Sweeney
 * Copyright Ed Sweeney 2004, 2005, 2006, 2007. All rights reserved.
 *
 * @author Ed Sweeney <a href="http://www.edsweeney.net">
 */
package org.navimatrix.commons.concurrent;

/**
 * used for cooperative interuption of threads.
 * 
 * a manager app can decide a thread should end and if that
 * thread is periodically checking this class to see if it
 * is hated, that thread will get a runtime exception.
 * 
 */
public class Navirupt {


    /**
     * hints / tells other thread to quit
     * 
     * @param thread
     */
    public static void interupt(Thread thread) {

        if (thread == null) throw new java.lang.NullPointerException("thread is null");

        thread.interrupt();
    }


    /**
     * threads participating in cooperative thread killing will call this
     * method at safe kill points.  if the thread is in 'interrupt' state,
     * ussually because another thead has called 'interupt' or
     * Navirupt.interrupt, then a NaviruptException will be thrown
     * (RuntimeException).
     */
    public static void check() {

        Thread t = Thread.currentThread();

        boolean bad = t.interrupted();

        if (bad) {

            throw new NaviruptException();
        }
    }
}

