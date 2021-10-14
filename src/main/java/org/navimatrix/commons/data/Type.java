package org.navimatrix.commons.data;

import java.util.List;

public interface Type
{
  public String getName();
  
  public String getURI();

  public String getURIPrefix();

  public Class getInstanceClass();

  public boolean isInstance(Object object);

  public List getProperties();
  
  public Property getProperty(String propertyName);
}
