/*  
 * partial implementation of ibm's and bea's draft sdo spec.  will implement the spec
 * once they release a finished *working* recomendation
 */
package org.navimatrix.commons.data;

import java.io.Serializable;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Date;
import java.util.List;

/**
 * DataObject supports a disconnected programming model.
 * Connect, get the data, disconnect, process data, connect, store
 * 
 * See IBM and BEA's whitepaper and spec for full explaination.  This implementation
 * is not compliant with their draft spec, but future versions will comply when they
 * finish the spec.  (note, some articles from IBM and the "1.0" in the spec give the
 * false impression the spec is implementable... it isn't yet, 7/2004
 */
public interface DataObject extends Serializable, Cloneable {

  public Object clone();

  public Object get(String path);

  public void set(String path, Object value);

  public boolean isSet(String path);

  public void unset(String path);

  public boolean getBoolean(String path);

  public byte getByte(String path);

  public char getChar(String path);

  public double getDouble(String path);

  public float getFloat(String path);

  public int getInt(String path);

  public long getLong(String path);

  public short getShort(String path);

  public byte[] getBytes(String path);

  public BigDecimal getBigDecimal(String path);

  public BigInteger getBigInteger(String path);

  public DataObject getDataObject(String path);

  public Date getDate(String path);

  public String getString(String path);

  public List getList(String path);

  public void setBoolean(String path, boolean value);

  public void setByte(String path, byte value);

  public void setChar(String path, char value);

  public void setDouble(String path, double value);

  public void setFloat(String path, float value);

  public void setInt(String path, int value);

  public void setLong(String path, long value);

  public void setShort(String path, short value);

  public void setBytes(String path, byte[] value);

  public void setBigDecimal(String path, BigDecimal value);

  public void setBigInteger(String path, BigInteger value);

  public void setDataObject(String path, DataObject value);

  public void setDate(String path, Date value);

  public void setString(String path, String value);

  public void setList(String path, List value);

  public Object get(int propertyIndex);

  public void set(int propertyIndex, Object value);

  public boolean isSet(int propertyIndex);

  public void unset(int propertyIndex);

  public boolean getBoolean(int propertyIndex);

  public byte getByte(int propertyIndex);

  public char getChar(int propertyIndex);

  public double getDouble(int propertyIndex);

  public float getFloat(int propertyIndex);

  public int getInt(int propertyIndex);

  public long getLong(int propertyIndex);

  public short getShort(int propertyIndex);

  public byte[] getBytes(int propertyIndex);

  public BigDecimal getBigDecimal(int propertyIndex);

  public BigInteger getBigInteger(int propertyIndex);

  public DataObject getDataObject(int propertyIndex);

  public Date getDate(int propertyIndex);

  public String getString(int propertyIndex);

  public List getList(int propertyIndex);

  public void setBoolean(int propertyIndex, boolean value);

  public void setByte(int propertyIndex, byte value);

  public void setChar(int propertyIndex, char value);

  public void setDouble(int propertyIndex, double value);

  public void setFloat(int propertyIndex, float value);

  public void setInt(int propertyIndex, int value);

  public void setLong(int propertyIndex, long value);

  public void setShort(int propertyIndex, short value);

  public void setBytes(int propertyIndex, byte[] value);

  public void setBigDecimal(int propertyIndex, BigDecimal value);

  public void setBigInteger(int propertyIndex, BigInteger value);

  public void setDataObject(int propertyIndex, DataObject value);

  public void setDate(int propertyIndex, Date value);

  public void setString(int propertyIndex, String value);

  public void setList(int propertyIndex, List value);

  public Object get(Property property);

  public void set(Property property, Object value);

  public boolean isSet(Property property);

  public void unset(Property property);

  public boolean getBoolean(Property property);

  public byte getByte(Property property);

  public char getChar(Property property);

  public double getDouble(Property property);

  public float getFloat(Property property);

  public int getInt(Property property);

  public long getLong(Property property);

  public short getShort(Property property);

  public byte[] getBytes(Property property);

  public BigDecimal getBigDecimal(Property property);

  public BigInteger getBigInteger(Property property);

  public DataObject getDataObject(Property property);

  public Date getDate(Property property);

  public String getString(Property property);

  public List getList(Property property);

  public void setBoolean(Property property, boolean value);

  public void setByte(Property property, byte value);

  public void setChar(Property property, char value);

  public void setDouble(Property property, double value);

  public void setFloat(Property property, float value);

  public void setInt(Property property, int value);

  public void setLong(Property property, long value);

  public void setShort(Property property, short value);

  public void setBytes(Property property, byte[] value);

  public void setBigDecimal(Property property, BigDecimal value);

  public void setBigInteger(Property property, BigInteger value);

  public void setDataObject(Property property, DataObject value);

  public void setDate(Property property, Date value);

  public void setString(Property property, String value);

  public void setList(Property property, List value);

  public DataObject createDataObject(String propertyName);

  public DataObject createDataObject(String propertyName, String namespaceURI, String prefix);

  //public DataObject createDataObject(int propertyIndex);

  //public DataObject createDataObject(Property property);

  //public DataObject createDataObject(String propertyName, String namespaceURI, String typeName);

  //public DataObject createDataObject(int propertyIndex, String namespaceURI, String typeName);

  //public DataObject createDataObject(Property property, Type type);

  public void delete();

  public DataObject getContainer();

  public Property getContainmentProperty();

  public DataGraph getDataGraph();

  public Type getType();
}
