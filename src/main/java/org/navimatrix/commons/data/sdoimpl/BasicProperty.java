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

import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.Type;

public class BasicProperty implements Property, java.io.Serializable {

    private final static boolean m_intern_strings_default;
    static {
        String i = System.getProperty("org.navimatrix.commons.data.intern_strings_default", "false");
        m_intern_strings_default = Boolean.valueOf(i).booleanValue();
    }

    private final String m_name;
    private final Type   m_type;

    private Object  m_defaultValue  = null;
    private boolean m_isMany        = false;
    private boolean m_isContainer   = false;

    //use for fast searches only
    BasicProperty(String name) {

        this(false, name, null);
    }

    BasicProperty(boolean isMany, String name, Type type) {

        if (m_intern_strings_default && name != null) {
            m_name = name.intern();
        } else {
            m_name = name;
        }

        m_type = type;

        m_isMany = isMany;
    }

    void setIsMany(boolean isMany) {

        m_isMany = isMany;
    }

    void setIsContainer(boolean isContainer) {
        //todo: this method gets called several times for each obj.  fixme
        m_isContainer = isContainer;
    }

    //
    // begin Property ifc
    //
    public boolean isMany() {

        return m_isMany;
    }

    public String getName() {
        return m_name;
    }

    public Type getType() {
        return m_type;
    }

    public Object getDefault() {

        return m_defaultValue;
    }

    public boolean isContainer() {

        return m_isContainer;
    }

    public String toString() {
        return m_name;
    }

    /*
    public boolean equals(Object o) {

        if (o == this) {

            return true;
        }

        if (o instanceof Property) {

            return m_name.equals(((Property) o).getName());
        }

        return false;
    }
    */

    public int hashCode() {

	/*  this causes toString to return an infinite byte[].  must cause a circular walk... graph bug
        if (m_name != null) {

            return m_name.hashCode();

        } else {

            return super.hashCode();
        }
	*/

        //must have a unique hc for each p because of the insane requirement not to reorder xml when there is
        // an implicit isMany
        return super.hashCode();
    }
}

