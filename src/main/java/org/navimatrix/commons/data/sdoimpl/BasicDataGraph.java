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

