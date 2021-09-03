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

import org.navimatrix.commons.data.impl.BasicData;
import org.navimatrix.commons.data.sdoimpl.BasicDataGraph;
import org.navimatrix.commons.data.sdoimpl.BasicDataObject;
import org.navimatrix.commons.data.sdoimpl.SynchronizedBasicDataObject;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class DataFactory {

    private static XmlPullParserFactory factory = null;

    public static Data createData() {

        return new BasicData();
    }

    public static DataObject createDataObject(String uri, String name) {

        DataGraph g = new BasicDataGraph();

        return g.createRootObject(uri, name);
    }

    public static DataObject createSynchronizedDataObject(String uri, String name) {

        DataObject data = createDataObject(uri,name);
        return new SynchronizedBasicDataObject((BasicDataObject) data);
    }

    private static XmlPullParserFactory getFactory() throws XmlPullParserException {

        if (factory == null) {

            factory = XmlPullParserFactory.newInstance(
                                                      System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            factory.setNamespaceAware(true);
        }

        return factory;
    }

    public static XmlSerializer getSerializer() throws XmlPullParserException {
        //XmlSerializer s = getFactory().newSerializer();
        //s.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        return getFactory().newSerializer();
    }

    public static MXParser getParser() throws XmlPullParserException {

        return(MXParser) getFactory().newPullParser();
    }
}
