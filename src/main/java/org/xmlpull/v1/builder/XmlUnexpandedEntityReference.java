package org.xmlpull.v1.builder;

/**
 * Represents
 * <a href="http://www.w3.org/TR/xml-infoset/#infoitem.rse">Unexpanded Entity Reference
 * Information Item</a>
 * .
 */

public interface XmlUnexpandedEntityReference //extends XmlContainer
{
    public String getName();
    public String getSystemIdentifier();
    public String getPublicIdentifier();
    public String getDeclarationBaseUri();
    public XmlElement getParent();
}

