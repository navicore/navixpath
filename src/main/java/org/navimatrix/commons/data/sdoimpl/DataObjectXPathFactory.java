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

