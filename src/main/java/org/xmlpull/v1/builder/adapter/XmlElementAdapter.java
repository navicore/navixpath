package org.xmlpull.v1.builder.adapter;

//import java.util.IdentityHashMap;
import java.util.Iterator;
import org.xmlpull.v1.builder.Iterable;
import org.xmlpull.v1.builder.XmlAttribute;
import org.xmlpull.v1.builder.XmlContainer;
import org.xmlpull.v1.builder.XmlDocument;
import org.xmlpull.v1.builder.XmlElement;
import org.xmlpull.v1.builder.XmlNamespace;

public class XmlElementAdapter implements XmlElement
{
    
    
    private XmlElement target;
    private XmlContainer parent;
    
    public XmlElementAdapter(XmlElement target) {
        this.target = target;
        if(target.getParent() != null) {
            //
            //throw new XmlBuilderException("element to wrap must have no parent to be wrapped");
            XmlContainer parent = target.getParent();
            if(parent instanceof XmlDocument) {
                XmlDocument doc = (XmlDocument) parent;
                doc.setDocumentElement(this);
            } if(parent instanceof XmlElement) {
                XmlElement parentEl = (XmlElement) parent;
                parentEl.replaceChild(this, target);
            }
        }
        // new "wrapping" parent replaces old parent for children
        Iterator iter = target.children();
        while (iter.hasNext())
        {
            Object child = iter.next();
            fixImportedChildParent(child);
        }
        //target.setParent(null);
        //IdentityHashMap id = nul;
    }
    
    private void fixImportedChildParent(Object child) {
        if(child instanceof XmlElement) {
            XmlElement childEl = (XmlElement) child;
            XmlContainer childElParent = childEl.getParent();
            if(childElParent == target) {
                childEl.setParent(this);
            }
        }
    }
    
    private XmlElement fixElementParent(XmlElement el) {
        el.setParent(this);
        return el;
    }
    
    public XmlContainer getRoot() {
        XmlContainer root = target.getRoot();
        if(root == target) {
            root = this;
        }
        return root;
    }
    
    public XmlContainer getParent() {
        return parent; //target.getParent();
    }
    
    public void setParent(XmlContainer parent) {
        this.parent = parent;
        //target.setParent(parent);
    }
    
    public XmlNamespace newNamespace(String prefix, String namespaceName) {
        return target.newNamespace(prefix, namespaceName);
    }
    
    public XmlAttribute attribute(String attributeName) {
        return target.attribute(attributeName);
    }
    
    public XmlAttribute attribute(XmlNamespace attributeNamespaceName,
                                  String attributeName)
    {
        return target.attribute(attributeNamespaceName, attributeName);
    }
    
    
    public XmlAttribute findAttribute(String attributeNamespaceName, String attributeName) {
        return target.findAttribute(attributeNamespaceName, attributeName);
    }
    
    public Iterator attributes() {
        return target.attributes() ;
    }
    
    public void removeAllChildren() {
        target.removeAllChildren();
    }
    
    public XmlAttribute addAttribute(String attributeType,
                                     String attributePrefix,
                                     String attributeNamespace,
                                     String attributeName,
                                     String attributeValue,
                                     boolean specified)
    {
        return target.addAttribute(attributeType,
                                   attributePrefix,
                                   attributeNamespace,
                                   attributeName,
                                   attributeValue,
                                   specified);
    }
    
    public String getAttributeValue(String attributeNamespaceName,
                                    String attributeName)
    {
        return target.getAttributeValue(attributeNamespaceName, attributeName);
    }
    
    
    public XmlNamespace lookupNamespaceByPrefix(String namespacePrefix) {
        return target.lookupNamespaceByPrefix(namespacePrefix);
    }
    
    public XmlAttribute addAttribute(XmlNamespace namespace, String name, String value) {
        return target.addAttribute(namespace, name, value);
    }
    
    public String getNamespaceName() {
        return target.getNamespaceName();
    }
    
    public void ensureChildrenCapacity(int minCapacity) {
        target.ensureChildrenCapacity(minCapacity);
    }
    
    public Iterator namespaces() {
        return target.namespaces();
    }
    
    public void removeAllAttributes() {
        target.removeAllAttributes();
    }
    
    public XmlNamespace getNamespace() {
        return target.getNamespace();
    }
    
    public String getBaseUri() {
        return target.getBaseUri();
    }
    
    public void removeAttribute(XmlAttribute attr) {
        target.removeAttribute(attr);
    }
    
    public XmlNamespace declareNamespace(String prefix, String namespaceName) {
        return target.declareNamespace(prefix, namespaceName);
    }
    
    public void removeAllNamespaceDeclarations() {
        target.removeAllNamespaceDeclarations();
    }
    
    public boolean hasAttributes() {
        return target.hasAttributes();
    }
    
    public XmlAttribute addAttribute(String type, XmlNamespace namespace, String name, String value, boolean specified) {
        return target.addAttribute(type, namespace, name, value, specified);
    }
    
    public XmlNamespace declareNamespace(XmlNamespace namespace) {
        return target.declareNamespace(namespace);
    }
    
    public XmlAttribute addAttribute(String name, String value) {
        return target.addAttribute(name, value);
    }
    
    public boolean hasNamespaceDeclarations() {
        return target.hasNamespaceDeclarations();
    }
    
    public XmlNamespace lookupNamespaceByName(String namespaceName) {
        return target.lookupNamespaceByName(namespaceName);
    }
    
    public XmlNamespace newNamespace(String namespaceName) {
        return target.newNamespace(namespaceName);
    }
    
    public void setBaseUri(String baseUri) {
        target.setBaseUri(baseUri);
    }
    
    public void setNamespace(XmlNamespace namespace) {
        target.setNamespace(namespace);
    }
    
    public void ensureNamespaceDeclarationsCapacity(int minCapacity) {
        target.ensureNamespaceDeclarationsCapacity(minCapacity);
    }
    
    //    public String getPrefix() {
    //        return target.getPrefix();
    //    }
    //
    //    public void setPrefix(String prefix) {
    //        target.setPrefix(prefix);
    //    }
    
    public String getName() {
        return target.getName();
    }
    
    public void setName(String name) {
        target.setName(name);
    }
    
    public XmlAttribute addAttribute(String type, XmlNamespace namespace, String name, String value) {
        return target.addAttribute(type, namespace, name, value);
    }
    
    public void ensureAttributeCapacity(int minCapacity) {
        target.ensureAttributeCapacity(minCapacity);
    }
    
    public XmlAttribute addAttribute(XmlAttribute attributeValueToAdd) {
        return target.addAttribute(attributeValueToAdd);
    }
    
    // --- children
    
    public XmlElement element(int position) {
        return target.element(position);
    }
    
    public XmlElement element(XmlNamespace n, String name) {
        return target.element(n, name);
    }
    
    public XmlElement element(XmlNamespace n, String name, boolean create) {
        return target.element(n, name, create);
    }
    
    public Iterable elements(XmlNamespace n, String name) {
        return target.elements(n, name);
    }
    
    public XmlElement findElementByName(String name, XmlElement elementToStartLooking) {
        return target.findElementByName(name, elementToStartLooking);
    }
    
    public XmlElement newElement(XmlNamespace namespace, String name) {
        return target.newElement(namespace, name);
    }
    
    public XmlElement addElement(XmlElement child)
    {
        return fixElementParent( target.addElement(child) );
    }
    
    public XmlElement addElement(int pos, XmlElement child) {
        return fixElementParent( target.addElement(pos, child) );
    }
    
    
    public XmlElement addElement(String name) {
        return fixElementParent( target.addElement(name) );
    }
    
    public XmlElement findElementByName(String namespaceName, String name) {
        return target.findElementByName(namespaceName, name);
    }
    
    public void addChild(Object child) {
        target.addChild(child);
        fixImportedChildParent(child);
    }
    
    public void insertChild(int pos, Object childToInsert) {
        target.insertChild(pos, childToInsert);
        fixImportedChildParent(childToInsert);
    }
    
    public XmlElement findElementByName(String name) {
        return target.findElementByName(name);
    }
    
    
    public XmlElement findElementByName(String namespaceName, String name, XmlElement elementToStartLooking) {
        return target.findElementByName(namespaceName, name, elementToStartLooking);
    }
    
    public void removeChild(Object child) {
        target.removeChild(child);
    }
    
    public Iterator children() {
        return target.children();
    }
    
    public Iterator requiredElementContent() {
        return target.requiredElementContent();
    }
    
    public String requiredTextContent() {
        return target.requiredTextContent();
    }
    
    public boolean hasChild(Object child) {
        return target.hasChild(child);
    }
    
    public XmlElement newElement(String namespaceName, String name) {
        return target.newElement(namespaceName, name);
    }
    
    public XmlElement addElement(XmlNamespace namespace, String name) {
        return fixElementParent( target.addElement(namespace, name) );
    }
    
    public boolean hasChildren() {
        return target.hasChildren();
    }
    
    public void addChild(int pos, Object child) {
        target.addChild(pos, child);
        fixImportedChildParent(child);
    }
    
    public void replaceChild(Object newChild, Object oldChild) {
        target.replaceChild(newChild, oldChild);
        fixImportedChildParent(newChild);
    }
    
    public XmlElement newElement(String name) {
        return target.newElement(name);
    }
    
}

