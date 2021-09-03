package org.xmlpull.v1.builder;

/**
 * This is <b>immutable</b> value object that represents
 * <a href="http://www.w3.org/TR/xml-infoset/#infoitem.attribute">Attribute
 * Information Item</a>
 * with exception of <b>references</b> property.
 * Note: namespace and prefix properties are folded into XmlNamespace value object.
 *
 * @version $Revision: 1.1 $
 * @author <a href="http://www.extreme.indiana.edu/~ochipara/">Octav Chipara</a>
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public interface XmlAttribute
{
    public XmlElement getOwner();
    //public XmlElement setOwner(XmlElement newOwner);
    //public String getPrefix();

    /** return namespaceName form getNamespace() o null if attribute has no namespace */
    public String getNamespaceName();
    public XmlNamespace getNamespace();
    public String getName();
    public String getValue();
    public String getType();
    public boolean isSpecified();
}

