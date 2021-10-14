package org.navimatrix.commons.data.sdoimpl;

import java.util.Iterator;
import java.util.Map;

import org.navimatrix.jaxen.JaxenException;
import org.navimatrix.jaxen.XPath;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.DataValue;
import org.navimatrix.commons.data.Property;

//public interface BasicDataObject extends DataObject implements DataValue, Cloneable {
public interface BasicDataObject extends DataObject, DataValue, Cloneable {

    //
    // begin BasicDataObject
    //

    public Map getMap();

    public void setContainmentProperty(Property prop);

    public void setContainer(DataObject container);

    //void setValue(Property property, Object value);

    //
    // end BasicDataObject
    //


    //xpath helper methods

    public Iterator getChildren();

    public Iterator getAttrs();

    public XPath getXPath(String xpath) throws JaxenException;


    //clone
    public Object clone();
}
