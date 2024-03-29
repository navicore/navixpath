package org.xmlpull.v1.builder.impl;

import org.xmlpull.v1.builder.XmlNamespace;
import org.xmlpull.v1.builder.XmlBuilderException;

public class XmlNamespaceImpl implements XmlNamespace
{
        
    //final static XmlNamespace NULL_NAMESPACE = new XmlNamespaceImpl(null, null);

    private String namespaceName;
    private String prefix;

    XmlNamespaceImpl(String prefix, String namespaceName) {
        this.prefix = prefix;
        if(namespaceName == null) {
            throw new XmlBuilderException("namespace name can not be null");
        }

        this.namespaceName = namespaceName;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getNamespaceName()
    {
        return namespaceName;
    }

    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null) return false;
        if(!(other instanceof XmlNamespace)) return false;
        XmlNamespace otherNamespace = (XmlNamespace) other;
        return getNamespaceName().equals(otherNamespace.getNamespaceName());
    }
    
}

