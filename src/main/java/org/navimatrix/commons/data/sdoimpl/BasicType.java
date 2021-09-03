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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

import org.navimatrix.commons.data.impl.HashList;
import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.Type;

public class BasicType implements Type, java.io.Serializable {

    public final static int DEF_SIZE = 1;

    private final static boolean m_intern_strings_default;
    static {
        String i = System.getProperty("org.navimatrix.commons.data.intern_strings_default", "false");
        m_intern_strings_default = Boolean.valueOf(i).booleanValue();
    }

    private final String    m_name;
    private final String    m_uri;
    private final String    m_prefix;
    private final Class     m_classInstance;

    private final List      m_properties;

    BasicType(String name, String uri, String prefix, Class classInstance) {
        this(name, uri, prefix, classInstance, DEF_SIZE);
    }
    BasicType(String name, String uri, String prefix, Class classInstance, int size) {

        if (uri == null) throw new NullPointerException("uri must not be null");//i18n

        if (m_intern_strings_default && name != null) {
            m_name = name.intern();
        } else {
            m_name          = name;
        }

        if (m_intern_strings_default && uri != null) {
            m_uri           = uri.intern();
        } else {
            m_uri           = uri;
        }

        if (m_intern_strings_default && prefix != null) {
            m_prefix        = prefix.intern();
        } else {
            m_prefix        = prefix;
        }

        m_classInstance = classInstance;

        m_properties = new HashList(size);
        //m_properties = new PList(size);
    }

    boolean hasProperty(String propertyName) {

        //todo: speed up?
        for (Iterator iter = m_properties.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

    public Property getProperty(String propertyName) {

        //todo: speed up?
        for (Iterator iter = m_properties.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(propertyName)) {
                return p;
            }
        }
        throw new NullPointerException("'" + propertyName + "' property not found in type '" + m_name + "'"); //not found //i18n
    }

    public String getName() {
        return m_name;
    }

    public String getURI() {
        return m_uri;
    }

    public String getURIPrefix() {
        return m_prefix;
    }

    public Class getInstanceClass() {
        return m_classInstance;
    }

    public boolean isInstance(Object object) {
        if (object == null) return false;
        if (m_classInstance == null) return false;
        return m_classInstance.isInstance(object);
    }

    public List getProperties() {

        return m_properties;
    }

    public String toString() {
        return m_name;
    }

    /* actually slower that straight HashList
    private static class PList implements java.util.List {

        private final List      m_properties;

        private final Set m_names = new HashSet();

        public PList(int size) {
            m_properties = new HashList(size);
        }
        private void addName(Object o) {
            if (o instanceof Property) {
                String name = ((Property) o).getName();
                m_names.add(o);
            }
        }

        public boolean add(Object o) {
            addName(o);
            return m_properties.add(o);
        }
        public void add(int index, Object element) {
            addName(element);
            m_properties.add(index, element);
        }
        public boolean addAll(Collection c) {
            return m_properties.addAll(c);
        }
        public boolean addAll(int index, Collection c) {
            return m_properties.addAll(index, c);
        }
        public void clear() {
            m_properties.clear();
        }
        public boolean contains(Object o) {
            if (m_names.contains(o.toString())) {
                return m_properties.contains(o); //double check
            }
            return false;
            //return m_properties.contains(o);
        }
        public boolean containsAll(Collection c) {
            return m_properties.containsAll(c);
        }
        public boolean equals(Object o) {
            return m_properties.equals(o);
        }
        public Object get(int index) {
            return m_properties.get(index);
        }
        public int hashCode() {
            return m_properties.hashCode();
        }
        public int indexOf(Object o) {
            return m_properties.indexOf(o);
        }
        public boolean isEmpty() {
            return m_properties.isEmpty();
        }
        public Iterator<Object> iterator() {
            return m_properties.iterator();
        }
        public int lastIndexOf(Object o) {
            return m_properties.lastIndexOf(o);
        }
        public ListIterator<Object> listIterator(int index) {
            return m_properties.listIterator(index);
        }
        public ListIterator<Object> listIterator() {
            return m_properties.listIterator();
        }
        public boolean remove(Object o) {
            return m_properties.remove(o);
        }
        public Object remove(int index) {
            return m_properties.remove(index);
        }
        public boolean removeAll(Collection c) {
            return m_properties.removeAll(c);
        }
        public boolean retainAll(Collection c) {
            return m_properties.retainAll(c);
        }
        public Object set(int index, Object element) {
            return m_properties.set(index,element);
        }
        public int size() {
            return m_properties.size();
        }
        public List<Object> subList(int fromIndex, int toIndex) {
            return m_properties.subList(fromIndex,toIndex);
        }
        public Object[] toArray() {
            return m_properties.toArray();
        }
        public Object[] toArray(Object[] a) {
            return m_properties.toArray(a);
        }
    }
    */
}

