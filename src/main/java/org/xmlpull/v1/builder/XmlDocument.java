package org.xmlpull.v1.builder;

import java.util.Iterator;
import java.io.OutputStream;

/**
 * Represents
 * <a href="http://www.w3.org/TR/xml-infoset/#infoitem.document">Document Information Item</a>
 * .
 */
public interface XmlDocument extends XmlContainer
{

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
    public Iterator children();
    public XmlElement getDocumentElement();
    /**
     * An unordered set of notation information items,
     * one for each notation declared in the DTD.
     */
    public Iterator notations();
    /**
     * An unordered set of unparsed entity information items,
     * one for each unparsed entity declared in the DTD.
     */
    public Iterator unparsedEntities();

    public String getBaseUri();
    public String getCharacterEncodingScheme();
    public void setCharacterEncodingScheme(String characterEncoding);
    public Boolean isStandalone();
    public String getVersion();
    //public String setVersion();
    public boolean isAllDeclarationsProcessed();

    // manipulate children
    public void setDocumentElement(XmlElement rootElement);

    public void addChild(Object child);
    public void insertChild(int pos, Object child);

    //removeChild
    public void removeAllChildren();

    public XmlComment newComment(String content);
    public XmlComment addComment(String content);
    public XmlDoctype newDoctype(String systemIdentifier, String publicIdentifier);
    public XmlDoctype addDoctype(String systemIdentifier, String publicIdentifier);
    //public XmlElement newDocumentElement(String name);
    public XmlElement addDocumentElement(String name);
    public XmlElement addDocumentElement(XmlNamespace namespace, String name);
    public XmlProcessingInstruction newProcessingInstruction(String target, String content);
    public XmlProcessingInstruction addProcessingInstruction(String target, String content);

    //addDoctype

    // manipulate unparsed entities
    //addUnparsedEntity
    public void remocveAllUnparsedEntities();


    // manipulate notations
    public XmlNotation addNotation(String name,
                                   String systemIdentifier,
                                   String publicIdentifier,
                                   String declarationBaseUri);
    public void removeAllNotations();
}

