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

/**
 * LIFE CYCLE bean
 * 
 * implement in things like dispatchers and queues.
 * 
 * you should be able to run start and stop over and over safely
 * and noop rather than throw and invalid state type of error.
 * 
 * consequently, there is no isStarted() stuff to worry about before
 * calling start/stop.
 */
public interface LBean {

    //
    // begin keys for LBEAN_INFO
    //
    public static final String LBEAN_NAME         ="NAME";

    public static final String LBEAN_ERROR        ="ERROR";

    public static final String LBEAN_ERROR_REASON ="ERROR_REASON";

    public static final String LBEAN_ERROR_THREAD_ID ="ERROR_THREAD_ID";

    public static final String LBEAN_ERROR_THREAD_HC ="ERROR_THREAD_HC";

    public static final String LBEAN_STARTED      ="LBEAN_STARTED"; //boolean
                                                                    // 
    public static final String LBEAN_START_TIME   ="LBEAN_START_TIME"; //Date

    public static final String LBEAN_ITEMS        ="LBEAN_ITEMS"; //for queues, int

    //
    // end keys for LBEAN_INFO
    //

    //use as type of DataObject returned from getLBeanInfo
    public static final String LBEAN_INFO_TYPE ="LBEAN_INFO";
    public static final String LBEAN_INFO_URI = "";

    /**
     * normally should run once and noop if accidentially run twice or more, unless
     * there is a real reason to throw an error.
     * 
     * some LBeans should of coarse throw errors if start / stop are run
     * incorrectly, start/stop/start may throw java.lang.IllegalStateException.
     * a good example would be calling start on a TimedHandler that has already
     * expired and had stop called.  the start is harmless except that it is a
     * good hint that something is trying to re-register the handler after is
     * has been burned up and discarded.
     */
    public void start();

    /**
     * run once.  ok if called more than once.  noop if already stopped.
     */
    public void stop();

    /**
     * free form.  publish schema in implementation docs.
     * 
     * @return anything interesting.  example, dispatcher will have a field
     *         SCHED_PROCESS_DELTA that is the diff between the last schedule
     *         and the last invoke of a handler's process method.
     *         
     *         also, 2 well known fields: NAME, ERROR, and ERROR_REASON.  ERROR can
     *         be a boolean so clients can just to isSet(ERROR)
     */
    public DataObject getLBeanInfo();
}

