package org.navimatrix.commons.data.sdoimpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.navimatrix.jaxen.JaxenException;
import org.navimatrix.jaxen.XPath;
import org.navimatrix.commons.data.DataGraph;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.Type;

/**
 * This is a synchronized wrapper for BasicDataObject.
 */
public class SynchronizedBasicDataObject implements BasicDataObject {

    private final BasicDataObject data;

    public SynchronizedBasicDataObject(BasicDataObject data) {
        this.data = data;
    }

    public synchronized boolean getBoolean(String path) {
        return data.getBoolean(path);
    }

    public synchronized Object clone() {
        BasicDataObject clone = (BasicDataObject) data.clone();
        return new SynchronizedBasicDataObject(clone);
    }

    public synchronized Object get(String path) {
        return data.get(path);
    }

    public synchronized byte getByte(String path) {
        return data.getByte(path);
    }

    public synchronized void set(String path, Object value) {
        data.set(path,value);
    }

    public synchronized boolean isSet(String path) {
        return data.isSet(path);
    }

    public synchronized void unset(String path) {
        data.unset(path);
    }


    public synchronized float getFloat(String path) {
        return data.getFloat(path);
    }

    public synchronized int getInt(String path) {
        return data.getInt(path);
    }

    public synchronized char getChar(String path) {
        return data.getChar(path);
    }

    public synchronized double getDouble(String path) {
        return data.getDouble(path);
    }

    public synchronized byte[] getBytes(String path) {
        return data.getBytes(path);
    }

    public synchronized BigDecimal getBigDecimal(String path) {
        return data.getBigDecimal(path);
    }

    public synchronized long getLong(String path) {
        return data.getLong(path);
    }

    public synchronized short getShort(String path) {
        return data.getShort(path);
    }

    public synchronized Date getDate(String path) {
        return data.getDate(path);
    }

    public synchronized String getString(String path) {
        return data.getString(path);
    }

    public synchronized BigInteger getBigInteger(String path) {
        return data.getBigInteger(path);
    }

    public synchronized DataObject getDataObject(String path) {
        return data.getDataObject(path);
    }

    public synchronized void setByte(String path, byte value) {
        data.setByte(path,value);
    }

    public synchronized void setChar(String path, char value) {
        data.setChar(path,value);
    }

    public synchronized List getList(String path) {
        return data.getList(path);
    }

    public synchronized void setBoolean(String path, boolean value) {
        data.setBoolean(path,value);
    }

    public synchronized void setInt(String path, int value) {
        data.setInt(path,value);
    }

    public synchronized void setLong(String path, long value) {
        data.setLong(path,value);
    }

    public synchronized void setDouble(String path, double value) {
        data.setDouble(path,value);
    }

    public synchronized void setFloat(String path, float value) {
        data.setFloat(path,value);
    }

    public synchronized void setBigDecimal(String path, BigDecimal value) {
        data.setBigDecimal(path, value);
    }

    public synchronized void setBigInteger(String path, BigInteger value) {
        data.setBigInteger(path, value);
    }

    public synchronized void setShort(String path, short value) {
        data.setShort(path,value);
    }

    public synchronized void setBytes(String path, byte[] value) {
        data.setBytes(path,value);
    }

    public synchronized void setString(String path, String value) {
        data.setString(path,value);
    }

    public synchronized void setDataObject(String path, DataObject value) {
        data.setDataObject(path,value);
    }

    public synchronized void setDate(String path, Date value) {
        data.setDate(path,value);
    }

    public synchronized boolean isSet(int propertyIndex) {
        return data.isSet(propertyIndex);
    }

    public synchronized void setList(String path, List value) {
        data.setList(path,value);
    }

    public synchronized Object get(int propertyIndex) {
        return data.get(propertyIndex);
    }

    public synchronized void set(int propertyIndex, Object value) {
        data.set(propertyIndex,value);
    }

    public synchronized void unset(int propertyIndex) {
        data.unset(propertyIndex);
    }

    public synchronized boolean getBoolean(int propertyIndex) {
        return data.getBoolean(propertyIndex);
    }

    public synchronized byte getByte(int propertyIndex) {
        return data.getByte(propertyIndex);
    }

    public synchronized char getChar(int propertyIndex) {
        return data.getChar(propertyIndex);
    }

    public synchronized double getDouble(int propertyIndex) {
        return data.getDouble(propertyIndex);
    }

    public synchronized float getFloat(int propertyIndex) {
        return data.getFloat(propertyIndex);
    }

    public synchronized int getInt(int propertyIndex) {
        return data.getInt(propertyIndex);
    }

    public synchronized long getLong(int propertyIndex) {
        return data.getLong(propertyIndex);
    }

    public synchronized void setBytes(int propertyIndex, byte[] value) {
        data.setBytes(propertyIndex,value);
    }

    public synchronized short getShort(int propertyIndex) {
        return data.getShort(propertyIndex);
    }

    public synchronized byte[] getBytes(int propertyIndex) {
        return data.getBytes(propertyIndex);
    }

    public synchronized BigDecimal getBigDecimal(int propertyIndex) {
        return data.getBigDecimal(propertyIndex);
    }

    public synchronized BigInteger getBigInteger(int propertyIndex) {
        return data.getBigInteger(propertyIndex);
    }

    public synchronized DataObject getDataObject(int propertyIndex) {
        return data.getDataObject(propertyIndex);
    }

    public synchronized Date getDate(int propertyIndex) {
        return data.getDate(propertyIndex);
    }

    public synchronized String getString(int propertyIndex) {
        return data.getString(propertyIndex);
    }

    public synchronized List getList(int propertyIndex) {
        return data.getList(propertyIndex);
    }

    public synchronized void setBoolean(int propertyIndex, boolean value) {
        data.setBoolean(propertyIndex,value);
    }

    public synchronized void setByte(int propertyIndex, byte value) {
        data.setByte(propertyIndex,value);
    }

    public synchronized void setChar(int propertyIndex, char value) {
        data.setChar(propertyIndex,value);
    }

    public synchronized void setDouble(int propertyIndex, double value) {
        data.setDouble(propertyIndex,value);
    }

    public synchronized void setFloat(int propertyIndex, float value) {
        data.setFloat(propertyIndex,value);
    }

    public synchronized void setInt(int propertyIndex, int value) {
        data.setInt(propertyIndex,value);
    }

    public synchronized void setLong(int propertyIndex, long value) {
        data.setLong(propertyIndex,value);
    }

    public synchronized void setShort(int propertyIndex, short value) {
        data.setShort(propertyIndex,value);
    }

    public synchronized void setBigDecimal(int propertyIndex, BigDecimal value) {
        data.setBigDecimal(propertyIndex, value);
    }

    public synchronized void setBigInteger(int propertyIndex, BigInteger value) {
        data.setBigInteger(propertyIndex, value);
    }

    public synchronized void setDataObject(int propertyIndex, DataObject value) {
        data.setDataObject(propertyIndex,value);
    }

    public synchronized void setDate(int propertyIndex, Date value) {
        data.setDate(propertyIndex, value);
    }

    public synchronized void setString(int propertyIndex, String value) {
        data.setString(propertyIndex,value);
    }

    public synchronized void setList(int propertyIndex, List value) {
        data.setList(propertyIndex,value);
    }

    public synchronized DataObject createDataObject(String propertyName) {
        BasicDataObject o = (BasicDataObject) data.createDataObject(propertyName);
        return new SynchronizedBasicDataObject(o);
    }

    public DataObject createDataObject(String propertyName, String namespaceURI, String prefix) {
        BasicDataObject o = (BasicDataObject) data.createDataObject(propertyName, namespaceURI, prefix);
        return new SynchronizedBasicDataObject(o);
    }

    public synchronized void delete() {
        data.delete();
    }

    public synchronized DataObject getContainer() {
        return data.getContainer();
    }

    public synchronized Property getContainmentProperty() {
        return data.getContainmentProperty();
    }

    public synchronized DataGraph getDataGraph() {
        return data.getDataGraph();
    }

    public synchronized Type getType() {
        return data.getType();
    }

    public synchronized Object getValue() {
        return data.getValue();
    }

    public synchronized void setValue(Object dataObjectValue) {
        data.setValue(dataObjectValue);
    }

    public synchronized boolean hasValue() {
        return data.hasValue();
    }

    public synchronized Object get(Property property) {
        return data.get(property);
    }

    public synchronized void set(Property property, Object value) {
        data.set(property,value);
    }

    public synchronized boolean isSet(Property property) {
        return data.isSet(property);
    }

    public synchronized void unset(Property property) {
        data.unset(property);
    }

    public synchronized boolean getBoolean(Property property) {
        return data.getBoolean(property);
    }

    public synchronized byte getByte(Property property) {
        return data.getByte(property);
    }

    public synchronized char getChar(Property property) {
        return data.getChar(property);
    }

    public synchronized double getDouble(Property property) {
        return data.getDouble(property);
    }

    public synchronized float getFloat(Property property) {
        return data.getFloat(property);
    }

    public synchronized int getInt(Property property) {
        return data.getInt(property);
    }

    public synchronized long getLong(Property property) {
        return data.getLong(property);
    }

    public synchronized short getShort(Property property) {
        return data.getShort(property);
    }

    public synchronized byte[] getBytes(Property property) {
        return data.getBytes(property);
    }

    public synchronized BigDecimal getBigDecimal(Property property) {
        return data.getBigDecimal(property);
    }

    public synchronized BigInteger getBigInteger(Property property) {
        return data.getBigInteger(property);
    }

    public synchronized DataObject getDataObject(Property property) {
        return data.getDataObject(property);
    }

    public synchronized Date getDate(Property property) {
        return data.getDate(property);
    }

    public synchronized String getString(Property property) {
        return data.getString(property);
    }

    public synchronized List getList(Property property) {
        return data.getList(property);
    }

    public synchronized void setBoolean(Property property, boolean value) {
        data.setBoolean(property,value);
    }

    public synchronized void setByte(Property property, byte value) {
        data.setByte(property, value);
    }

    public synchronized void setChar(Property property, char value) {
        data.setChar(property, value);
    }

    public synchronized void setDouble(Property property, double value) {
        data.setDouble(property, value);
    }

    public synchronized void setFloat(Property property, float value) {
        data.setFloat(property,value);
    }

    public synchronized void setInt(Property property, int value) {
        data.setInt(property,value);
    }

    public synchronized void setLong(Property property, long value) {
        data.setLong(property,value);
    }

    public synchronized void setShort(Property property, short value) {
        data.setShort(property,value);
    }

    public synchronized void setBytes(Property property, byte[] value) {
        data.setBytes(property,value);
    }

    public synchronized void setBigDecimal(Property property, BigDecimal value) {
        data.setBigDecimal(property,value);
    }

    public synchronized void setBigInteger(Property property, BigInteger value) {
        data.setBigInteger(property,value);
    }

    public synchronized void setDataObject(Property property, DataObject value) {
        data.setDataObject(property,value);
    }

    public synchronized void setDate(Property property, Date value) {
        data.setDate(property, value);
    }

    public synchronized void setString(Property property, String value) {
        data.setString(property,value);
    }

    public synchronized void setList(Property property, List value) {
        data.setList(property,value);
    }

    //basicdataobjec impl
    public synchronized Iterator getChildren() {
        return data.getChildren();
    }

    //basicdataobjec impl
    public synchronized Iterator getAttrs() {
        return data.getAttrs();
    }

    public synchronized void setContainmentProperty(Property prop) {
        data.setContainmentProperty(prop);
    }

    //used for clone()
    public synchronized void setContainer(DataObject container) {
        data.setContainer(container);
    }


    public synchronized boolean equals(Object obj) {

        return data.equals(obj);
    }


    /**
     * hc is calculated by adding up the hc of each of the entries
     * and each of the entries' names and then adding 5000 for nonesense
     */
    public synchronized int hashCode() {

        return data.hashCode();
    }


    public synchronized XPath getXPath(String xpath) throws JaxenException {

        return data.getXPath(xpath);
    }

    public synchronized Map getMap() {
        return data.getMap();
    }

    public synchronized String toString() {

        return data.toString();
    }
}
