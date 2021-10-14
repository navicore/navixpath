package org.navimatrix.commons.data;

public interface Property {

    public String getName();

    public Type getType();

    public boolean isMany();

    public Object getDefault();

    public boolean isContainer();
}
