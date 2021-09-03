package org.xmlpull.v1.builder.impl;

import org.xmlpull.v1.builder.XmlAttribute;
import org.xmlpull.v1.builder.XmlElement;
import org.xmlpull.v1.builder.XmlNamespace;
import org.xmlpull.v1.builder.XmlBuilderException;

/**
 */
public class XmlAttributeImpl implements XmlAttribute
{
    private XmlElement owner_;
    private String prefix_;
    private XmlNamespace namespace_;
    private String name_;
    private String value_;
    private String type_ = "CDATA";
    private boolean default_;

    XmlAttributeImpl(XmlElement owner, String name, String value) {
        this.owner_ = owner;
        this.name_ = name;
        if(value == null) throw new IllegalArgumentException("attribute value can not be null");
        this.value_ = value;
    }

    XmlAttributeImpl(XmlElement owner, XmlNamespace namespace,
                     String name, String value) {
        this(owner, name, value);
        this.namespace_ = namespace;
    }

    XmlAttributeImpl(XmlElement owner, String type, XmlNamespace namespace,
                     String name, String value) {
        this(owner, namespace, name, value);
        this.type_ = type;
    }

    XmlAttributeImpl(XmlElement owner,
                     String type,
                     XmlNamespace namespace,
                     String name,
                     String value,
                     boolean specified)
    {
        this(owner, namespace, name, value);
        if(type == null) {
            throw new IllegalArgumentException("attribute type can not be null");
        }
        //TODO: better checking for allowed attribute types
        this.type_ = type;
        this.default_ = !specified;
    }

    public XmlElement getOwner() {
        return owner_;
    }

    //public String getPrefix()
    //{
    //    return prefix_;
    //}

    public XmlNamespace getNamespace()
    {
        return namespace_;
    }

    public String getNamespaceName()
    {
        return namespace_ != null ? namespace_.getNamespaceName() : null;
    }

    public String getName()
    {
        return name_;
    }

    public String getValue()
    {
        return value_;
    }

    public String getType()
    {
        return type_;
    }

    public boolean isSpecified()
    {
        return !default_;
    }

    public String toString() {
        return "name=" + name_ + " value=" + value_;
    }
}

