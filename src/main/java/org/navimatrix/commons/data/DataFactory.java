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
