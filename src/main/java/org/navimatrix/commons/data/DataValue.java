package org.navimatrix.commons.data;

/**
 * an object can contain be a proxy for a value object
 */
public interface DataValue {

    public Object getValue();

    public void setValue(Object dataObjectValue);

    public boolean hasValue();
}
