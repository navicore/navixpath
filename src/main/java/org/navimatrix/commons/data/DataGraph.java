package org.navimatrix.commons.data;

public interface DataGraph
{
  public DataObject getRootObject();

  public DataObject createRootObject(String namespaceURI, String typeName);

  public DataObject createRootObject(String namespaceURI, String prefix, String typeName);

  public DataObject createRootObject(Type type);

  public Type getType(String uri, String typeName);

  public boolean isChanged();

  //assemblers can use this to say a DataObject
  //  is representative of the store/src.  if the sdo comes
  //  back with isChanged false, the obj is dirty and needs
  //  to be synced with store/src
  public void setChanged(boolean changed);

  //number of java char(s) that make up all properties and values in graph
  public long charCount();

}

