// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package org.xmlpull.v1.builder.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.builder.XmlBuilderException;
import org.xmlpull.v1.builder.XmlComment;
import org.xmlpull.v1.builder.XmlDoctype;
import org.xmlpull.v1.builder.XmlDocument;
import org.xmlpull.v1.builder.XmlElement;
import org.xmlpull.v1.builder.XmlNamespace;
import org.xmlpull.v1.builder.XmlNotation;
import org.xmlpull.v1.builder.XmlProcessingInstruction;

public class XmlDocumentImpl implements XmlDocument
{
    private List children = new ArrayList();
    private XmlElement root;
    private String version;
    private Boolean standalone;
    private String characterEncoding;

    public XmlDocumentImpl(String version, Boolean standalone, String characterEncoding) {
        this.version = version;
        this.standalone = standalone;
        this.characterEncoding = characterEncoding;
    }


    /**
     * Method getVersion
     *
     * @return   a String
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Method isStandalone
     *
     * @return   a Boolean
     *
     */
    public Boolean isStandalone() {
        return standalone;
    }


    /**
     * Method getCharacterEncodingScheme
     *
     * @return   a String
     *
     */
    public String getCharacterEncodingScheme() {
        return characterEncoding;
    }

    public void setCharacterEncodingScheme(String characterEncoding)
    {
        this.characterEncoding = characterEncoding;
    }

    /**
     * Method newProcessingInstruction
     *
     * @param    target              a  String
     * @param    content             a  String
     *
     * @return   a XmlProcessingInstruction
     *
     */
    public XmlProcessingInstruction newProcessingInstruction(String target, String content)
    {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method addProcessingInstruction
     *
     * @param    target              a  String
     * @param    content             a  String
     *
     * @return   a XmlProcessingInstruction
     *
     */
    public XmlProcessingInstruction addProcessingInstruction(String target, String content) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * An ordered list of child information items, in document order.
     * The list contains exactly one element information item.
     * The list also contains one processing instruction information item
     * for each processing instruction outside the document element,
     * and one comment information item for each comment outside the document element.
     * Processing instructions and comments within the DTD are excluded.
     * If there is a document type declaration,
     * the list also contains a document type declaration information item.
     */
    public Iterator children() {
        // TODO
        //throw new XmlBuilderException("not implemented");
        return children.iterator();
    }

    /**
     * Method remocveAllUnparsedEntities
     *
     */
    public void remocveAllUnparsedEntities() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method setDocumentElement
     *
     * @param    rootElement         a  XmlElement
     *
     */
    public void setDocumentElement(XmlElement rootElement) {
        // replace with existing root element
        boolean replaced = false;
        for (int i = 0; i < children.size(); i++)
        {
            Object element = children.get(i);
            if(element == this.root) {
                children.set(i, rootElement);
                replaced = true;
            }
        }
        if(!replaced) {
            children.add(rootElement);
        }
        this.root = rootElement;
        rootElement.setParent(this);
        //TOOD: nice assertion that htere is only one XmlElement in children ...
    }

    /**
     * Method insertChild
     *
     * @param    pos                 an int
     * @param    child               an Object
     *
     */
    public void insertChild(int pos, Object child) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method addComment
     *
     * @param    content             a  String
     *
     * @return   a XmlComment
     *
     */
    public XmlComment addComment(String content) {
        // TODO
        //throw new XmlBuilderException("not implemented");
        XmlComment comment = new XmlCommentImpl(this, content);
        children.add(comment);
        return comment;
    }

    /**
     * Method newDoctype
     *
     * @param    systemIdentifier    a  String
     * @param    publicIdentifier    a  String
     *
     * @return   a XmlDoctype
     *
     */
    public XmlDoctype newDoctype(String systemIdentifier, String publicIdentifier) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * An unordered set of unparsed entity information items,
     * one for each unparsed entity declared in the DTD.
     */
    public Iterator unparsedEntities() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method removeAllChildren
     *
     */
    public void removeAllChildren() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method newComment
     *
     * @param    content             a  String
     *
     * @return   a XmlComment
     *
     */
    public XmlComment newComment(String content) {
        //throw new XmlBuilderException("not implemented");
        return new XmlCommentImpl(null, content);
    }

    /**
     * Method removeAllNotations
     *
     */
    public void removeAllNotations() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }
    /**
     * Method addDoctype
     *
     * @param    systemIdentifier    a  String
     * @param    publicIdentifier    a  String
     *
     * @return   a XmlDoctype
     *
     */
    public XmlDoctype addDoctype(String systemIdentifier, String publicIdentifier) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method addChild
     *
     * @param    child               an Object
     *
     */
    public void addChild(Object child) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method addNotation
     *
     * @param    name                a  String
     * @param    systemIdentifier    a  String
     * @param    publicIdentifier    a  String
     * @param    declarationBaseUri  a  String
     *
     * @return   a XmlNotation
     *
     */
    public XmlNotation addNotation(String name, String systemIdentifier, String publicIdentifier, String declarationBaseUri) {
        // TODO
        throw new XmlBuilderException("not implemented");
    }


    //    public XmlElement newDocumentElement(String name) {
    //        // TODO
    //        return null;
    //    }

    /**
     * Method getBaseUri
     *
     * @return   a String
     *
     */
    public String getBaseUri() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * An unordered set of notation information items,
     * one for each notation declared in the DTD.
     */
    public Iterator notations() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method addDocumentElement
     *
     * @param    name                a  String
     *
     * @return   a XmlElement
     *
     */
    public XmlElement addDocumentElement(String name) {
        return addDocumentElement(null, name);
    }

    public XmlElement addDocumentElement(XmlNamespace namespace, String name)
    {
        XmlElement el = new XmlElementImpl(namespace, name);
        if(getDocumentElement() != null) {
            throw new XmlBuilderException("document already has root element");
        }
        setDocumentElement(el);
        return el;

    }

    /**
     * Method isAllDeclarationsProcessed
     *
     * @return   a boolean
     *
     */
    public boolean isAllDeclarationsProcessed() {
        // TODO
        throw new XmlBuilderException("not implemented");
    }

    /**
     * Method getDocumentElement
     *
     * @return   a XmlElement
     *
     */
    public XmlElement getDocumentElement() {
        // TODO
        return root;
    }

}

