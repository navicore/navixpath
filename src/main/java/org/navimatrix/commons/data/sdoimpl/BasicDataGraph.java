package org.navimatrix.commons.data.sdoimpl;

import org.navimatrix.commons.data.DataGraph;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.Type;

public class BasicDataGraph implements DataGraph, java.io.Serializable {

    protected DataObject root = null;

    private boolean m_changed = false;
    private boolean m_hc_changed = true;

    private long m_chars = 0;

    public DataObject createRootObject(Type type) {
        throw new UnsupportedOperationException();
    }

    public DataObject createRootObject(String namespaceURI, String typeName) {
        return createRootObject(namespaceURI, "", typeName); 
    }

    public DataObject createRootObject(String namespaceURI, String prefix, String typeName) {
        if (root != null) {
            throw new java.lang.IllegalStateException("root already set");
        }
        root = new BasicDataObjectImpl(this, namespaceURI, prefix, typeName, null, null, null); 
        return root;
    }

    public DataObject getRootObject() {
        return root;
    }

    public Type getType(String uri, String typeName) {
        throw new UnsupportedOperationException();
    }

    public boolean isChanged() {

        return m_changed;
    }

    public void setChanged(boolean changed) {

        m_changed = changed;
	if (changed) {
	    m_hc_changed = changed;
	}
    }

    public boolean isHcChanged() {

        return m_hc_changed;
    }

    public void setHcChanged(boolean changed) {

        m_hc_changed = changed;
    }

    void incrementChars(long count) {

        m_chars += count;
    }

    void decrementChars(long count) {

        m_chars -= count;
    }

    public long charCount() {

        return m_chars;
    }
}

