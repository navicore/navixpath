package org.xmlpull.v1.builder;

import java.io.IOException;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

// XmlNodePullParser(element)
// XmlNodeSerializer() or (XmlSerializer), serialize(XmlNode)



// "natural" namespace management

// types of elements -- follow infoset!!!!
//  ALL AVAILABLE: element, comment, text (+cdata), attribute, document, namespace, processingInstruction

// use factory: XmlComment.valueOf( "foo" ); --> doc.newComment()
// *XmlNode: parent
//   XmlDocument ...
//   * XmlElement: name, namespace, qname, children, attributes, namespaces { so called XmlPullNode)
//      translatePrefixToUri
//      getSubTreePullParser
//   * XmlAttribute: name, namespace, qname, value
//   * XmlNamespace: value(uri), prefix
//   XmlProcessingInstruction: target, data
//   XmlComment: value
//   * XmlText: value
//   XmlCDATA: value
//   XmlEntityRef: value name????

// simplified: XmlElement + String (instead of XmlText) !!!

//     EVERYTHING has a parent!!!!
//    * children - element(S) ---> top-level is always document!!!
//        name, namespace, qname
//    * attributes
//        name, namespace, qname, value
//    * text, element????, attribute, namespace (==URI), commnent : string value
//    * namespaces
//        value, prefix
//    * processing instruction
//        target, data
// String translateNamespacePrefixToUri(String prefix, Object context)
//    * can translate namespace prefix to uri

// short getNodeType
// node.getStringValue();

// Object getDocument(String uri)

// getNamespaceAxisIterator -- up to root and "xml" prefix ...
//      what about repetitions, ... namespace.asXPathResult(


//    /** Matches Element nodes */
//    public static final short ELEMENT_NODE = 1;
//    /** Matches elements nodes */
//    public static final short ATTRIBUTE_NODE = 2;
//    /** Matches elements nodes */
//    public static final short TEXT_NODE = 3;
//    /** Matches elements nodes */
//    public static final short CDATA_SECTION_NODE = 4;
//    /** Matches elements nodes */
//    public static final short ENTITY_REFERENCE_NODE = 5;
//    /** Matches elements nodes */
//    //public static final short ENTITY_NODE = 6;
//    /** Matches ProcessingInstruction */
//    public static final short PROCESSING_INSTRUCTION_NODE = 7;
//    /** Matches Comments nodes */
//    public static final short COMMENT_NODE = 8;
//    /** Matches Document nodes */
//    public static final short DOCUMENT_NODE = 9;
//    /** Matches DocumentType nodes */
//    public static final short DOCUMENT_TYPE_NODE = 10;
//    //public static final short DOCUMENT_FRAGMENT_NODE = 11;
//    //public static final short NOTATION_NODE = 12;
//
//    /** Matchs a Namespace Node - NOTE this differs from DOM */
//    // XXXX: ????
//    public static final short NAMESPACE_NODE = 13;
//
//    /** Does not match any valid node */
//    public static final short UNKNOWN_NODE = 14;
//
//    /** The maximum number of node types for sizing purposes */
//    public static final short MAX_NODE_TYPE = 14;

/**
 * This a special iterator that <b>constructs</b> content of XmlElement
 * that gives very fine control over creation of element constent
 * including ability to skip some children or bypass creation of
 *
 */
public interface XmlPullElement extends XmlElement {


    /**
     * This is not recommened method to pull children when node is not
     * finished (use nextChild() instead) as Enumeration interface
     * does not allow to throw XmlPullParserException
     * so any parsing exeption is wrapped into RuntimeException
     * making code more messy...
     *
     * @see #nextChild()
     */
    public Iterator children();

    /**
     * Is pull parsing of this node finished i.e. all children are fully constructed.
     */
    public boolean fullyConstructed();

    /**
     * Return just next child without constructing subtrees for next child
     * or null if there is no next child.
     * NOTE: if current child was not fully constructesd
     * (fullyConstructed() for current child is false)
     * then all its subchildren will be read
     * (by calling nextChild on current child until null is returned)
     * @return null if no more children available
     */
    public XmlPullElement readNextChild()
        throws XmlPullParserException, IOException;

    /**
     * Skip next child including its all scontent (essentially skips subtree)
     * return true if there was child to skip or false if this element has
     * no more children to read (tis indicates too that this elements is fully contructed).
     */
    public boolean skipNextChild()
        throws XmlPullParserException, IOException;

    /**
     * Get parser (or null if there is no more children) that is used to build this node tree
     * to allow reading next child directly instead of cinstructing XmlPullElement.
     *
     * The parser is set on next child START_TAG
     * <br>NOTE: tt is caller responsibility to move returned parser
     * to END_TAG corresponding to current child START_TAG
     * and not to call any this XmlPullElement methods
     * until parser is positioned on this END_TAG.
     * <br>NOTE: if null is returned it indicates that this element has no more children to read
     * and is now fully constructed.
     */
    public  XmlPullParser nextChildAsPullParser()
        throws IOException, XmlPullParserException;


}
