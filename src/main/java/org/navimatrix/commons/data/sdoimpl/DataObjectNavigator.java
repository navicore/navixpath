/* 
 * Blazebot Computing
 * Licensed Materials - Property of Ed Sweeney
 * Copyright Ed Sweeney 2004, 2005, 2006, 2007. All rights reserved.
 *
 * The contents of this file are subject to the GNU Lesser General Public License
 * Version 2.1 (the "License"). You may not copy or use this file, in either
 * source code or executable form, except in compliance with the License. You
 * may obtain a copy of the License at http://www.fsf.org/licenses/lgpl.txt or
 * http://www.opensource.org/. Software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express
 * or implied. See the License for the specific language governing rights and
 * limitations under the License.
 *
 * @author Ed Sweeney <a href="mailto:ed.edsweeney.net">
 */

package org.navimatrix.commons.data.sdoimpl;

import java.util.Iterator;

import org.navimatrix.jaxen.DefaultNavigator;
import org.navimatrix.jaxen.NamedAccessNavigator;
import org.navimatrix.jaxen.Navigator;
import org.navimatrix.jaxen.XPath;
import org.navimatrix.jaxen.saxpath.SAXPathException;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.DataUtil;
import org.navimatrix.commons.data.DataValue;

public class DataObjectNavigator extends DefaultNavigator implements NamedAccessNavigator {

    private static final boolean debug = false;

    private static class Singleton {
        private static DataObjectNavigator instance = new DataObjectNavigator();
    }

    public static Navigator getInstance()
    {
        return Singleton.instance;
    }

    public boolean isElement(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isElement " + obj);

        return !DataUtil.isAttribute(obj);
    }

    public boolean isComment(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isComment " + obj);

        return false;
    }

    public boolean isText(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isText " + obj);

        return false; //?? leaf test?
    }

    public boolean isAttribute(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isAttribute " + obj);

        return DataUtil.isAttribute(obj);
    }

    public boolean isProcessingInstruction(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isProcessingInstruction " + obj);

        return false;
    }

    public boolean isDocument(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isDocument " + obj);

        //return true if this is a root
        return DataUtil.getRoot(obj) == obj;

    }

    public boolean isNamespace(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.isNamespace " + obj);
        return false;
    }

    public String getElementName(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getElementName " + obj);

        return DataUtil.getName(obj);
    }

    public String getElementNamespaceUri(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getElementNamespaceUri " + obj);

        return DataUtil.getURI(obj);
    }

    public String getElementQName(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getElementQName " + obj);
        //todo: look up prefix somewhere
        return DataUtil.getURI(obj);
    }

    public String getAttributeName(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getAttributeName " + obj);

        return DataUtil.getAttributeName(obj);
    }

    public String getAttributeNamespaceUri(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getAttributeNamespaceUri " + obj);

        return DataUtil.getURI(obj);
    }

    public String getAttributeQName(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getAttributeQName " + obj);

        //todo: look up prefix somewhere
        return DataUtil.getURI(obj);
    }

    public Iterator getChildAxisIterator(Object contextNode)
    {
        if (debug) System.out.println("DataObjectNavigator.getChildAxisIterator " + contextNode);

        if ( contextNode instanceof SynchronizedBasicDataObject ) {
            return((SynchronizedBasicDataObject) contextNode).getChildren();
        }

        if ( contextNode instanceof BasicDataObject ) {
            return((BasicDataObject) contextNode).getChildren();
        }

        return null;
    }

    public Iterator getChildAxisIterator(Object contextNode,
                                         String localName,
                                         String namespacePrefix,
                                         String namespaceURI) {
        //todo: process ns somehow??

        if (contextNode instanceof DataObject) {
            DataObject data = (DataObject) contextNode;
            //boolean hasvalue = ((DataValue) data).hasValue();
            //if (!hasvalue && data.isSet(localName)) { //won't see attributes
            if (data.isSet(localName)) { //will see attributes
                if (debug) {
                    Object o1 = data.get(localName);
                    String cn = o1.getClass().getName();
                    System.out.println("\nDataObjectNavigator.getChildAxisIterator 3 " + localName + " class: " + cn);
                }
                return DataUtil.getIterator(data.get(localName));
            }
        }

        if (debug) System.out.println("DataObjectNavigator.getChildAxisIterator 2 null!!!! localName: " + localName +
                                      " class: " + contextNode.getClass().getName()+ " str:" + contextNode);
        return null;
    }

    public Iterator getParentAxisIterator(Object contextNode)
    {
        if (debug) System.out.println("DataObjectNavigator.getParentAxisIterator " + contextNode);

        if ( contextNode instanceof DataObject ) {
            DataObject data = (DataObject) contextNode;
            Object parent = data.getContainer();
            return DataUtil.getIterator(parent);
        }

        return null;
    }

    public Iterator getAttributeAxisIterator(Object contextNode)
    {
        if (debug) System.out.println("DataObjectNavigator.getAttributeAxisIterator " + contextNode);

        if (!(contextNode instanceof BasicDataObject)) {
            throw new java.lang.IllegalArgumentException("contextNode is not supported sdo impl");
        }

        if ( contextNode instanceof SynchronizedBasicDataObject ) {
            return((SynchronizedBasicDataObject) contextNode).getAttrs();
        }

        if ( contextNode instanceof BasicDataObject ) {
            return((BasicDataObject) contextNode).getAttrs();
        }

        return null;
    }

    public Iterator getAttributeAxisIterator(Object contextNode,
                                             String localName,
                                             String namespacePrefix,
                                             String namespaceURI) {

        if (debug) System.out.println("DataObjectNavigator.geAttributedAxisIterator 2 " + contextNode);

        if ( contextNode instanceof DataObject ) {
            DataObject data = (DataObject) contextNode;
            String fldname = "@" + localName;
            if (data.isSet(fldname)) {
                Object fld = data.get(fldname);
                return DataUtil.getIterator(fld);
            }
        }
        return null;
    }

    public Object getDocumentNode(Object contextNode)
    {
        if (debug) System.out.println("\nDataObjectNavigator.getDocumentNode " + contextNode);

        return DataUtil.getRoot(contextNode);
    }

    public XPath parseXPath (String xpath) throws SAXPathException
    {
        if (debug) System.out.println("DataObjectNavigator.parseXPath " + xpath);

        //return new DataObjectXPath(xpath);
        return DataObjectXPathFactory.getXPath(xpath);
    }

    public Object getParentNode(Object contextNode)
    {
        if (debug) System.out.println("DataObjectNavigator.getParentNode " + contextNode);

        return DataUtil.getParent(contextNode);
    }

    public String getTextStringValue(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getTextStringValue " + obj);

        if (obj instanceof DataValue) {
            Object v = ((DataValue) obj).getValue();
            if (v != null) {
                return v.toString();
            } else {
                return null;
            }
        }

        return obj.toString();
    }

    public String getElementStringValue(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getElementStringValue " + obj);

        if (obj instanceof DataValue) {
            Object v = ((DataValue) obj).getValue();
            if (v != null) {
                return v.toString();
            } else {
                return null;
            }
        }

        return obj.toString();
    }

    public String getAttributeStringValue(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getAttributeStringValue " + obj);

        if (obj instanceof DataValue) {
            Object v = ((DataValue) obj).getValue();
            if (v != null) {
                return v.toString();
            } else {
                return null;
            }
        }

        return obj.toString();
    }

    public String getNamespaceStringValue(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getNamespaceStringValue " + obj);

        return DataUtil.getURI(obj);
    }

    public String getNamespacePrefix(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getNamespacePrefix " + obj);
        //todo: fixme
        return "";
    }

    public String getCommentStringValue(Object obj)
    {
        if (debug) System.out.println("DataObjectNavigator.getCommentStringValue " + obj);

        return ""; //todo: fixme
    }
}
