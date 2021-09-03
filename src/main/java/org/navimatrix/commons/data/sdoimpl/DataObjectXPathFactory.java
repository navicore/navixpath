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

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.navimatrix.jaxen.BaseXPath;
import org.navimatrix.jaxen.JaxenException;

public final class DataObjectXPathFactory {


    private static boolean m_cache_xpaths = true;
    static {
        String i = System.getProperty("org.navimatrix.commons.data.cache_xpaths", "true");
        m_cache_xpaths = Boolean.valueOf(i).booleanValue();
    }


    private static final Map m_cache = Collections.synchronizedMap(new WeakHashMap());


    public static final BaseXPath getXPath(String xpath) throws JaxenException {

        if (xpath == null) throw new java.lang.NullPointerException("xpath is null");

        if (!m_cache_xpaths) return new DataObjectXPath(xpath);

        Object path_instance = m_cache.get(xpath);

        if (path_instance == null) {
            path_instance = new DataObjectXPath(xpath);
            m_cache.put(xpath, path_instance);
        }

        return (BaseXPath) path_instance;
    }
}

