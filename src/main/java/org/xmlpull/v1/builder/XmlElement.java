package org.xmlpull.v1.builder;

import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Represents
 * <a href="http://www.w3.org/TR/xml-infoset/#infoitem.element">Element Information Item</a>
 * except for in-scope namespaces that can be reconstructed by visiting this element parent,
 * checking its namespaces, then grandparent and so on. For convenience there are
 * methods to resolve namespace prefix for given namespace name.
 */

public interface XmlElement extends XmlContainer
{
    public static final String NO_NAMESPACE = "";
    
    
    
    /** Return Iterator<Object>  - null is never returned if there is no children
     then iteraotr over empty collection is returned */
    public Iterator children();
    /** Return Iterator<XmlElement>  -  that represents all XmlElement content.
     * When used exception will be thrown if non white space children are found
     * (as expected no mixed content!).
     */
    public Iterator requiredElementContent();
    
    /**return children content as text - if there are any no text children throw exception  */
    public String requiredTextContent();
    
    /** Return Iterator<XmlAttribute> - null is never returned if there is no children
     then iteraotr over empty collection is returned */
    public Iterator attributes();
    /** Return Iterator<XmlNamespace> - null is never returned if there is no children
     then iteraotr over empty collection is returned */
    public Iterator namespaces();
    
    public String getBaseUri();
    public void setBaseUri(String baseUri);

    /**
     *  top most container either XmlDocument or XmlElement (may be the same element!!!)
     */
    public XmlContainer getRoot();
    
    /** if current element is not child of containing parent XmlElement or XmlDocumeent
     builder exception will be thrown */
    public XmlContainer getParent();
    public void setParent(XmlContainer parent);
    
    /** Return namespace of current element - null is only returned if
     element was created without namespace */
    public XmlNamespace getNamespace();
    
    /** Return namespace name or null if element has no namespace */
    public String getNamespaceName();
    
    /**
     * Set namespace ot use for theis element. Note: namespace prefix is <b>always</b> ignored.
     */
    public void setNamespace(XmlNamespace namespace);
    
    //    public String getPrefix();
    //    public void setPrefix(String prefix);
    
    public String getName();
    public void setName(String name);
    
    //------------------------------------------------
    // attributes
    
    public XmlAttribute addAttribute(XmlAttribute attributeValueToAdd);
    public XmlAttribute addAttribute(String name, String value);
    public XmlAttribute addAttribute(XmlNamespace  namespace, String name, String value);
    public XmlAttribute addAttribute(String type, XmlNamespace namespace,
                                     String name, String value);
    public XmlAttribute addAttribute(String type, XmlNamespace namespace,
                                     String name, String value, boolean specified);
    public XmlAttribute addAttribute(String attributeType,
                                     String attributePrefix,
                                     String attributeNamespace,
                                     String attributeName,
                                     String attributeValue,
                                     boolean specified);
    
    public void ensureAttributeCapacity(int minCapacity) ;
    
    public String getAttributeValue(String attributeNamespaceName,
                                    String attributeName);
    
    public XmlAttribute attribute(String attributeName);
    public XmlAttribute attribute(XmlNamespace attributeNamespaceName,
                                      String attributeName);
    
    /**
     * Find attribute that matches given name or namespace
     * Returns null if not found.
     * NOTE: if namespace is null in this case it will match only
     * attributes that has no namespace.
     *
     */
    public XmlAttribute findAttribute(String attributeNamespaceName,
                                      String attributeName);

    
    public boolean hasAttributes();
    
    public void removeAttribute(XmlAttribute attr);
    public void removeAllAttributes();
    
    //------------------------------------------------
    // namespaces
    
    /**
     * Create new namespace with prefix and namespace name (both must be not null)
     * and add it to current element.
     */
    public XmlNamespace declareNamespace(String prefix, String namespaceName);
    
    /**
     * Add namespace to current element (both prefix and namespace name must be not null)
     */
    public XmlNamespace declareNamespace(XmlNamespace namespace);
    
    public void ensureNamespaceDeclarationsCapacity(int minCapacity);
    
    public boolean hasNamespaceDeclarations();
    
    /**
     * Find namespace (will have non empty prefix) corresponding to namespace prefix
     * checking first current elemen and if not found continue in parent (if element has parent)
     * and so on.
     */
    public XmlNamespace lookupNamespaceByPrefix(String namespacePrefix);
    
    /**
     * Find namespace (will have non empty prefix) corresponding to namespace name
     * checking first current elemen and if not found continue in parent (if element has parent).
     * and so on.
     */
    public XmlNamespace lookupNamespaceByName(String namespaceName);
    
    /**
     * Create new namespace with null prefix (namespace name must be not null).
     */
    public XmlNamespace newNamespace(String namespaceName);
    
    /**
     * Create new namespace with prefix and namespace name (both must be not null).
     */
    public XmlNamespace newNamespace(String prefix, String namespaceName);
    
    public void removeAllNamespaceDeclarations();
    
    //------------------------------------------------
    // children
    
    /**
     * NOTE: =child added is _not_ checked if it XmlContainer, caller must manually fix
     * parent in child by calling setParent() !!!!
     */
    public void addChild(Object child);
    public void addChild(int pos, Object child);
    
    /**
     * NOTE: the child element must unattached to be added
     * (it is atttached if it is XmlContainer of recognized type and getParent() != null)
     */
    public XmlElement addElement(XmlElement el);
    public XmlElement addElement(int pos, XmlElement child);

    public XmlElement addElement(String name);
    public XmlElement addElement(XmlNamespace namespace, String name);
    
    public boolean hasChildren();
    public boolean hasChild(Object child);
    
    public void ensureChildrenCapacity(int minCapacity);
    
    public XmlElement findElementByName(String name);
    public XmlElement findElementByName(String namespaceName, String name);
    public XmlElement findElementByName(String name,
                                        XmlElement elementToStartLooking);
    public XmlElement findElementByName(String namespaceName, String name,
                                        XmlElement elementToStartLooking);
    
    public XmlElement element(int position);
    //int count()
    //int countElement()
    //XmlElement element(String name) //return first element matching, null if not found!
    public XmlElement element(XmlNamespace n, String name);
    public XmlElement element(XmlNamespace n, String name, boolean create);
    //Iterable elements(String name);
    //Iterable elements(XmlNamespace n, String name);

    /** Return all elements that has namespace and name (null is never returned but empty iteraotr) */
    public Iterable elements(XmlNamespace n, String name);
    
    
    //public Iterable elementsContent();
    //public Iterable elementsContent(String name);
    //public Iterable elementsContent(XmlNamespace n String name);

    //String text() //children must map to text only nodes!!!
    //public String requiredTextContent();

    
    //selectNodes(String xpath)
    
    //public XmlNamespace getNamespacePrefix(String namespaceName, boolean generate)
    //public XmlNamespace findNamespace(String prefix, String namespace)
    
    /** it may need to reconsruct whole subtree to get count ... */
    //public int getChildrenCount();
    
    //public Object getFirstChild() throws XmlPullParserException;
    //public Object getNextSibling() throws XmlPullParserException;
    
    //public Object getChildByName(String namespace, String name);
    
    
    public void insertChild(int pos, Object childToInsert);
    
    /**
     * Create unattached element
     */
    public XmlElement newElement(String name);
    public XmlElement newElement(XmlNamespace namespace, String name);
    public XmlElement newElement(String namespaceName, String name);
    
    /**
     * Removes all children - every child that was
     * implementing XmlNode will have set parent to null.
     */
    public void removeAllChildren();
    
    public void removeChild(Object child);
    
    public void replaceChild(Object newChild, Object oldChild);
    
    //public void remove(int pos);
    //public void set(int index, Object child);
    
}

