package org.navimatrix.commons.data.sdoimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.navimatrix.commons.concurrent.Navirupt;
import org.navimatrix.commons.data.Coder;
import org.navimatrix.commons.data.DataFactory;
import org.navimatrix.commons.data.DataGraph;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.DataUtil;
import org.navimatrix.commons.data.DataValue;
import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.Type;
import org.navimatrix.commons.data.XmlCoder;
import org.navimatrix.jaxen.JaxenException;
import org.navimatrix.jaxen.XPath;

public class BasicDataObjectImpl extends AbstractDataObject implements BasicDataObject {

    private final static Log m_log = LogFactory.getLog(BasicDataObjectImpl.class);

    //scalability issues: todo: analyze obj creation patterns and consider custom collections
    // for List and Map impls

    private final Map       m_map;

    private final BasicType m_type;

    private final DataGraph m_data_graph;

    private final BasicDataGraph m_basic_data_graph;

    private final boolean   m_auto_prop;

    private DataObject      m_container;

    private BasicProperty   m_containmentProperty;

    private Object          m_data_value = null;

    private static final boolean m_warn_not_serializable = System.getProperties().containsKey("org.navimatrix.warn_not_serializable");


    protected BasicDataObjectImpl(DataGraph     dataGraph,
                                  Type          type,
                                  DataObject    container,
                                  Property      containmentProperty,
                                  Collection    preDefProps) {

        if (dataGraph instanceof BasicDataGraph) {
            m_basic_data_graph = (BasicDataGraph) dataGraph;
            //m_basic_data_graph = null;
        } else {
            m_basic_data_graph = null;
        }

        //using LinkedHashMap because jaxen doesn't return getList stuff in property order otherwise (a buggy hack since it is really order of insert that you get with LinkedHashMap)
        int size = 2;
        if (preDefProps != null) {
            size = preDefProps.size();
        }
        m_map           = new java.util.LinkedHashMap(size);
        m_data_graph    = dataGraph;
        m_container     = container;
        m_type          = (BasicType) type;

        setContainmentProperty(containmentProperty);
        if (preDefProps != null) {
            m_auto_prop = false;
            m_type.getProperties().addAll(preDefProps);
        } else {
            m_auto_prop = true;
        }
    }


    protected BasicDataObjectImpl(DataGraph     dataGraph,
                                  String        uri,
                                  String        prefix,
                                  String        typeName,
                                  DataObject    container,
                                  Property      containmentProperty,
                                  Collection    preDefProps) {

        this(dataGraph, new BasicType(typeName, uri, prefix, BasicDataObject.class, preDefProps == null ? BasicType.DEF_SIZE : preDefProps.size()), container, containmentProperty, preDefProps);
    }


    public Map getMap() {

        return m_map;
    }

    //
    // begin DataValue methods
    //

    // the value field is an optimization for data mediators
    //   implemented in natively for BasicDataObject(s).  Avoids
    //   the map construction and lookup of the DataObject is a leaf

    public Object getValue() {

        return m_data_value;
    }

    public void setValue(Object dataObjectValue) {


        if (m_basic_data_graph != null) {
            long vlen = 0;
            if (dataObjectValue instanceof DataObject) {
                DataGraph g = ((DataObject) dataObjectValue).getDataGraph();
                if (g != m_basic_data_graph) { //BUG graph is broken if this is getting hit.  todo: fix graph (maybe it is really a tree btw?)
                    vlen = ((BasicDataGraph) g).charCount();
                }
            } else {
                vlen = dataObjectValue == null ? 0 : dataObjectValue.toString().length();
            }
            m_basic_data_graph.incrementChars(vlen);
        }


        if (
           (dataObjectValue == null && m_data_value != null) ||
           (dataObjectValue != null && !dataObjectValue.equals(m_data_value)) //it is unfortunate (expensive) to call equals all the time, but isChanged accuracy can have huge perf benefits
           ) {
            m_data_graph.setChanged(true);
        }

        m_data_value = dataObjectValue;

        if (m_containmentProperty != null) {
            m_containmentProperty.setIsContainer(false);
        }
    }


    public boolean hasValue() {

        return m_data_value != null;
    }

    //
    // end DataValue methods
    //


    public void setContainmentProperty(Property prop) {

        m_containmentProperty = (BasicProperty) prop;

        if (m_containmentProperty != null) {
            m_containmentProperty.setIsContainer(getValue() == null);
        }
    }


    //used for clone()
    public void setContainer(DataObject container) {

        m_container = container;
    }


    //
    // begin DataObject ifc
    //


    public DataObject createDataObject(String propertyName) {

        return createDataObject(propertyName, m_type.getURI(), "");
    }


    public DataObject createDataObject(String propertyName, String uri, String prefix) {

        BasicDataObject bdo = new BasicDataObjectImpl(m_data_graph,
                                                      uri,
                                                      prefix,
                                                      propertyName,
                                                      this,
                                                      null,
                                                      null);
        set(propertyName, bdo);

        return bdo;
    }


    public DataObject getContainer() {

        return m_container;
    }


    public Property getContainmentProperty() {

        return m_containmentProperty;
    }


    public DataGraph getDataGraph() {

        return m_data_graph;
    }


    public Type getType() {

        return m_type;
    }


    public boolean isSet(String path) {

        if (isPath(path)) {
            return get(path) != null;
        }

        //
        // ejs todo: speed up!

        List l = m_type.getProperties();
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(path) && getMap().containsKey(p)) {
                return true;
            }
        }
        return false;
    }


    public void unset(String path) {

        if (isPath(path)) {

            Object o = processPath(path);

            if (o == null) {

                m_log.warn("BasicDataObjectImpl.unset path to a null/unset value: " + path);

            } else if (o instanceof DataObject) {

                DataObject fld = (DataObject) o;
                fld.getContainer().unset(fld.getContainmentProperty());

            } else if (o instanceof List && !((List) o).isEmpty()) {

                List list = (List) o;
                for (int i = 0; i < list.size(); i++) {
                    Object f1 = list.get(i);
                    if (f1 instanceof DataObject) {
                        DataObject d1 = (DataObject) f1;
                        d1.getContainer().unset(d1.getContainmentProperty());
                    }
                }
            } else {
                //note, this causes all hell.  somehow something can be pathed to that is
                //  not a data object.  todo: find out how and stop it.  this is not the place to fix it.  this exception is correct!
                throw new java.lang.IllegalAccessError("data not DataObject");
            }
        } else {
            //getMap().remove(path);
            //ejs 10/7/2005
            List l = m_type.getProperties();
            for (Iterator iter = l.iterator(); iter.hasNext();) {
                Property p = (Property) iter.next();
                if (p.getName().equals(path) && getMap().containsKey(p)) {
                    getMap().remove(p);
                }
            }
        }
        //m_data_graph.setChanged(true);
    }


    public boolean isSet(Property property) {

        //String pname = property.getName();
        //return getMap().containsKey(pname);
        //ejs 10/7/2005
        return getMap().containsKey(property);
    }


    public void unset(Property property) {
        //String pname = property.getName();
        //getMap().remove(pname);
        //ejs 10/7/2005
        getMap().remove(property);
        //m_data_graph.setChanged(true);
    }


    public void delete() {

        getMap().clear();
        super.delete();
        //m_data_graph.setChanged(true);
    }


    public DataObject getDataObject(Property property) {
        //String pname = property.getName();
        //DataObject fld = (DataObject) getMap().get(pname);
        //ejs 10/7/2005
        DataObject fld = (DataObject) getMap().get(property);
        return fld;
    }


    public DataObject getDataObject(String path) {

        if (isPath(path)) {

            Object o = processPath(path);

            if (o instanceof DataObject) {
                return(DataObject) processPath(path);
            } else {
                throw new java.lang.IllegalAccessError("data not DataObject");
            }
        }

        //ejs 10/7/2005
        //Property prop = m_type.getProperty(path);
        //return getDataObject(prop);
        List l = m_type.getProperties();
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(path) && getMap().containsKey(p)) {
                return getDataObject(p);
            }
        }
        return null;
    }


    public Object get(Property property) {

        Navirupt.check();

        //String pname = property.getName();
        //Object fld = getMap().get(pname);

        //ejs 10/7/2005
        Object fld = getMap().get(property);

        if (m_auto_prop) {
            if (fld instanceof List) {
                List l = (List) fld;
                if (l.size() == 0) {
                    ((BasicProperty) property).setIsMany(false);
                    unset(property);
                    fld = null;
                }
                if (l.size() == 1) {
                    ((BasicProperty) property).setIsMany(false);
                    unset(property);
                    fld = l.get(0);
                    set(property, fld);
                }
            }
        }
        return fld;
    }


    protected Object getValue(Property property) {
        //String pname = property.getName();
        //Object fld = getMap().get(pname);
        //ejs 10/7/2005
        Object fld = getMap().get(property);

        if (fld instanceof DataValue) {
            DataValue v = (DataValue) fld;
            if (v.hasValue()) {
                return v.getValue();
            }
        }
        return fld;
    }


    private Object processPath(String path) {

        Navirupt.check();

        try {
            XPath xp = getXPath(path);
            //todo: check to see of xp is cloning, evaluate() does not clone
            List l = xp.selectNodes(this);
            //System.out.println("ejs xp sel: " + l);
            if (l.size() == 1) {
                return l.get(0);
            } else if (l.isEmpty()) {
                return null;
            } else {
                //todo: this is a problem.  this list is read-only.  itereator.remove won't work
                //  need to hack jaxen to return lists backed by sdo somehow.

                return l;
            }
        } catch (JaxenException je) {
            throw new java.lang.IllegalArgumentException(je.toString());
        }
    }


    public Object get(String path) {

        if (isPath(path)) {
            return processPath(path);
        }

        // ejs 10/9/2005 todo: return a sdo list
        Object ret = null;
        List list = null;

        List l = m_type.getProperties();
        for (Iterator iter = l.iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(path) && getMap().containsKey(p)) {
                Object item = get(p);
                if (ret == null) {
                    ret = item;
                } else {
                    if (list == null) {
                        list = new DataObjectList();
                        if (ret instanceof List) {
                            list.addAll((List) ret);
                        } else {
                            list.add(ret);
                        }
                    }
                    if (item instanceof List) {
                        list.addAll((List) item);
                    } else {
                        list.add(item);
                    }
                }
            }
        }
        if (list != null) return list;
        return ret;
    }


    private boolean updatePathTarget(String path, Object value) {

        //if path points to a leaf
        //  and value is not a container,
        //  replace value held
        //  by leaf with new value
        Object o = get(path);
        //System.out.println("ejs update: " + o);
        if (o instanceof BasicDataObject &&
            !(value instanceof DataObject) &&
            !(value instanceof List)) {
            BasicDataObject target = (BasicDataObject) o;
            if (target.hasValue()) {
                target.setValue(value);
                //System.out.println("ejs update done true: " + o);
                return true;
            }
        }
        //System.out.println("ejs update done false: " + o);
        return false;
    }

    int sleep_count = 0;
    void sleep() {
        sleep_count++;
        if (sleep_count > 1000) {
            throw new java.lang.IllegalStateException("ejs");
        }
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
    }

    private boolean createPathTarget(String path, Object value) {

        StringTokenizer st = new StringTokenizer(path, "/");


        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        //                                                           //
        // THIS IS BROKEN.  path parts have indexes... what's up???? //
        //                                                           //
        //  idea... why is update going down this path??? that's the //
        // bug.                                                      //
        //                                                           //
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////

        //System.out.println("ejs create");
        //sleep();//ejs

        List l = new ArrayList(2);
        while (st.hasMoreElements()) {
            l.add(st.nextElement());
        }
        BasicDataObject currentNode = this;
        int last = l.size() - 1;
        for (int i = 0; i < l.size(); i++) {

            String nodeName = (String) l.get(i);

            //System.out.println("ejs create node: " + nodeName);
            //sleep();//ejs

            if (currentNode.isSet("./" + nodeName)) {

                currentNode = (BasicDataObject) currentNode.get("./" + nodeName);

            } else if (i == last && value instanceof DataObject) {

                //don't create new node if addding a node
                currentNode.setDataObject(nodeName, (DataObject) value);
                return true;

            } else {

                currentNode = (BasicDataObject) currentNode.createDataObject(nodeName);
            }

            if (i == last) {

                currentNode.setValue(value);
            }
        }
        return true;
    }


    // the set(xxx) methods need to automagically create value objects because when
    //  parsing xml, the caller of set (the parser) doesn't know whether it is
    //  working on a leaf or not.

    public void set(String path, Object value) {

        //null value is allowed

        if (path == null) {
            throw new java.lang.NullPointerException("path is null"); //i18n
        }

        //todo: clean up :(

        Navirupt.check();

        if (isPath(path)) {

            //System.out.println("ejs set: " + path);
            if (updatePathTarget(path, value)) {
                //m_data_graph.setChanged(true);
                return;
            } else if (createPathTarget(path,value)) {
                //m_data_graph.setChanged(true);
                return;
            } else {
                throw new java.lang.IllegalAccessError("only updates of values of existing leaf nodes allowed with xpath");//i18n
            }
        }

        Property prop = null;

        /////////////////////////////////////////
        /////////////////////////////////////////
        //                                     //
        //   TURN THIS OFF TO FIX ORDERING     //
        //                                     //
        /////////////////////////////////////////
        /////////////////////////////////////////

        /* ejs 10/7/2005 turned off for ordering
        for (Iterator iter = m_type.getProperties().iterator(); iter.hasNext();) {
            Property p = (Property) iter.next();
            if (p.getName().equals(path)) {
                prop = p;
                break;
            }
        }
        */
        if (prop == null) {

            Class instanceClass = null;
            if (value != null) {
                instanceClass = value.getClass();
            }
            boolean isMany = value instanceof Collection;
            if (isMany) {
                DataObjectList v = new DataObjectList();
                v.addAll((Collection) value);
                value = v;
                //todo: get instanceClass id from collection contents
            }

            //should a type be created or is this a ref to the type of the fld?
            Type ptype = null;
            if (value instanceof DataObject) {
                ptype = ((DataObject) value).getType();
            } else {
                int size = BasicType.DEF_SIZE;
                if (isMany) {
                    size = ((Collection) value).size();
                }
                ptype = new BasicType(path, m_type.getURI(), m_type.getURIPrefix(), instanceClass, size);
            }

            prop = new BasicProperty(isMany, path, ptype);
            m_type.getProperties().add(prop);

            if (value instanceof DataObjectList) {
                DataObjectList dol = (DataObjectList) value;
                dol.setProperty(prop);
            }
        }

        // at this point, why not call set(pro for the rest of the processing
        set(prop, value);
    }


    // the set(xxx) methods need to automagically create value objects because when
    //  parsing xml, the caller of set (the parser) doesn't know whether it is
    //  working on a leaf or not.

    public void set(Property property, Object value) {

        //increment graph char count here
        if (m_basic_data_graph != null) {
            long plen = property == null ? 0 : property.getName().length();
            m_basic_data_graph.incrementChars(plen);
        }

        if (value instanceof BasicDataObject) ((BasicDataObject)value).setContainer(this);
        if (value instanceof BasicDataObject) ((BasicDataObject) value).setContainmentProperty(property);

        if (value != null && value == this) throw new java.lang.IllegalArgumentException("creating a circular ref with node");
        //if (value != null && value == getContainer()) throw new java.lang.IllegalArgumentException("creating a circular ref with parent");


        if (m_warn_not_serializable && value != null && !(value instanceof java.io.Serializable)) {

            m_log.warn("property '" + property + "' in type '" + m_type + "' value '" + value + "' is not serializable.  impl: " + value.getClass().getName());
        }

        Navirupt.check();

        if (value instanceof DataObject || value instanceof List) {

            //ejs 10/7/2005
            //String pname = property.getName();

            //check for isMany
            //if (!getMap().containsKey(pname)) {
            //ejs 10/7/2005
            if (!getMap().containsKey(property)) {

                //not isMany

                //getMap().put(pname, value);
                //ejs 10/7/2005
                getMap().put(property, value);

            } else {

                //process isMany
                //Object fld = getMap().get(pname);
                //ejs 10/7/2005
                Object fld = getMap().get(property);

                if (fld instanceof List) {
                    ((List)fld).add(value);

                } else {

                    if (!m_auto_prop) {
                        throw new IllegalArgumentException("property is not defined as isMany and autoprop is off"); //i18n
                    }
                    //convert to isMany

                    if (!m_auto_prop) {
                        throw new IllegalArgumentException("property is not defined as isMany and autoprop is off"); //i18n
                    }

                    List l;
                    if (property instanceof BasicProperty) {
                        BasicProperty bp = (BasicProperty) property;
                        bp.setIsMany(true);
                        l = new DataObjectList(bp);
                    } else {
                        l = new ArrayList(2);
                    }

                    System.out.println("************ ejs WARNING auto ismany *************");
                    //getMap().remove(pname);
                    //getMap().put(pname, l);
                    //ejs 10/7/2005
                    getMap().remove(property);
                    getMap().put(property, l);
                    l.add(fld);
                    l.add(value);
                } 
            }

        } else {

            //must be a leaf or an attribute (which are all leaves)

            setValue(property, value);
        }
        //m_data_graph.setChanged(true);
    }


    //updating a leaf.  can't just put it in a map, have to wrap it
    //  incase attributes need to be stored for it
    private void setValue(Property property, Object value) {

        BasicDataObject datafld = null;

        //ejs 10/7/2005
        //String pname = property.getName();

        //if (!getMap().containsKey(pname)) {
        //ejs 10/7/2005
        if (!getMap().containsKey(property)) {

            datafld = new BasicDataObjectImpl(m_data_graph, property.getType(), this, property, null);
            //getMap().put(pname, datafld);
            //ejs 10/7/2005
            getMap().put(property, datafld);

        } else {

            //Object fld = getMap().get(pname);

            //ejs 10/7/2005
            Object fld = getMap().get(property);

            if (fld instanceof List) {

                datafld = new BasicDataObjectImpl(m_data_graph, property.getType(), this, property, null);
                ((List)fld).add(datafld);

            } else if (isValue(fld)) { //convert to isMany

                if (!m_auto_prop) {
                    throw new IllegalArgumentException("property is not defined as isMany and autoprop is off"); //i18n
                }

                if (property instanceof BasicProperty) {
                    ((BasicProperty) property).setIsMany(true);
                }
                List l = new DataObjectList((BasicProperty) property);
                //getMap().remove(pname);
                //getMap().put(pname, l);
                //ejs 10/7/2005
                System.out.println("************ ejs WARNING auto ismany 2 *************");
                getMap().remove(property);
                getMap().put(property, l);
                l.add(fld);
                datafld = new BasicDataObjectImpl(m_data_graph, property.getType(), this, property,null);
                l.add(datafld);

            } else if (!(fld instanceof BasicDataObject)) {

                throw new ClassCastException("field " + property.getName() + " not BasicDataObject"); //i18n

            } else {

                datafld = (BasicDataObject) fld;
            }
        }

        datafld.setValue(value);
    }


    private boolean isValue(Object obj) {

        return obj instanceof DataValue && ((DataValue) obj).hasValue();
    }


    /**
     * WARNING, does not protect against recursive refs... will get a 
     * StackOverflowError if any children are self
     * 
     * @return 
     */
    public String toString() {

        if (hasValue()) return getValue().toString();

        try {
            final java.io.StringWriter sw = new java.io.StringWriter();
            XmlCoder.encode(this, sw, false, false);
            String ret = sw.toString();

            return ret;
            /*
        Coder coder = new XmlCoder();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        coder.encodeSdo(this, bos);
        byte[] bytes = bos.toByteArray();
        return new String(bytes, "UTF8");
        */
        } catch (java.lang.OutOfMemoryError ooe) {
            System.err.println("BasicDataObjectImpl.toString ooe");
            ooe.printStackTrace();
            return null;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }


    //xpath helper methods

    public Iterator getChildren() {

        //List tmpl = new ArrayList(); //todo: speed this up or separate attrs from elems
        List tmpl = new AbstractDataObject.DataObjectList((BasicProperty) getContainmentProperty());
        Set keys = getMap().keySet();
        for (Iterator ki = keys.iterator(); ki.hasNext();) {
            //ejs 10/7/2005
            //String key = (String) ki.next();
            Property key = (Property) ki.next();
            if (key != null && key.getName().charAt(0) != '@') {
                Object o = getMap().get(key);
                if (o instanceof List) {
                    tmpl.addAll((List) o);
                } else {
                    tmpl.add(o);
                }
            }
        }

        //return getMap().values().iterator();
        return tmpl.iterator();
    }


    public Iterator getAttrs() {

        //List tmpl = new ArrayList(); //todo: speed this up or separate attrs from elems
        List tmpl = new AbstractDataObject.DataObjectList((BasicProperty) getContainmentProperty());
        Set keys = getMap().keySet();
        for (Iterator ki = keys.iterator(); ki.hasNext();) {
            //String key = (String) ki.next();
            //ejs 10/7/2005
            Property key = (Property) ki.next();
            if (key != null && key.getName().charAt(0) == '@') {
                Object o = getMap().get(key);
                if (o instanceof List) {
                    tmpl.addAll((List) o);
                } else {
                    tmpl.add(o);
                }
            }
        }

        return tmpl.iterator();
    }


    public final XPath getXPath(String xpath) throws JaxenException {

        return DataObjectXPathFactory.getXPath(xpath);
    }


    private boolean isPath(String path) {

        return DataUtil.isXPath(path);
    }


    ////////////////
    /// Clonable ///
    ////////////////

    /**
     * deep clone if all the flds are DataObject type or immutable or Clonable
     * 
     * object that clone() is called on always returns a root node with its own
     * graph.
     * 
     */
    public Object clone() {

        BasicDataObject ret = (BasicDataObject) DataFactory.createDataObject(m_type.getURI(), m_type.getName());

        return cloneContents(ret, null);
    }


    Object cloneContents(BasicDataObject ret, DataGraph graph) {

        if (ret != null && graph != null) {
            throw new java.lang.IllegalArgumentException("one or the other must be null");
        }
        if (ret == null && graph == null) {
            throw new java.lang.IllegalArgumentException("can't both be null");
        }

        Type rettype = null;

        if (ret != null) {
            rettype = ret.getType();
        } else {
            rettype = new BasicType(m_type.getName(), m_type.getURI(), m_type.getURIPrefix(), m_type.getInstanceClass());
            ret = new BasicDataObjectImpl(graph, rettype, null, m_containmentProperty, null);
        }

        if (isValue(this)) {
            ret.setValue(getValue());
        }

        //iterate thru properties, create new property obj for each field
        List l = getType().getProperties();
        for (Iterator iter = l.iterator(); iter.hasNext();) {

            Property prop = (Property) iter.next();
            boolean isset = isSet(prop);
            Type proptype = prop.getType();
            Type fldtype = new BasicType(proptype.getName(), proptype.getURI(), proptype.getURIPrefix(), proptype.getInstanceClass(), proptype.getProperties().size());
            Property newprop = new BasicProperty(prop.isMany(), prop.getName(), fldtype);
            rettype.getProperties().add(newprop);
            Object fld = null;

            if (isset) {
                fld = get(prop);
                if (fld instanceof BasicDataObjectImpl) {

                    BasicDataObjectImpl bdo = (BasicDataObjectImpl) fld;

                    BasicDataObject newbdo =  (BasicDataObject) bdo.cloneContents(null, ret.getDataGraph());
                    //correct containment so that containment is this Mini-me, not Dr. Evil
                    newbdo.setContainmentProperty(newprop);
                    //newbdo.setContainer(this);
                    newbdo.setContainer(ret);//3/23/06

                    fld = newbdo;
                }
                /* else {
                    throw new java.lang.IllegalArgumentException();
                }
                */
                ret.set(newprop, fld);
            }
        }

        return ret;
    }


    public boolean equals(Object obj) {

        if (obj == this) {

            return true;
        }

        //this is a deep test.  tests all field values
        //  for equality.
        // 
        // this is profoundly wrong.  todo: fix.  equals should not use hashCode impl below and
        // hashCode nor equals should call toString

        if (obj instanceof BasicDataObjectImpl) {
            BasicDataObjectImpl o = (BasicDataObjectImpl) obj;
            if (this.hashCode() == o.hashCode()) return true;
        }

        return false;
    }

    private int m_hc = -1;

    /**
     * hc is calculated by adding up the hc of each of the entries
     * and each of the entries' names
     * 
     * todo: TODO: needs full rewrite for speed
     *   take into account Lists and that Integer might be a String and a String might be an Integer.  Argh.
     * 
     * too bad this is so expensive
     */
    public int hashCode() {

	if (m_data_graph instanceof BasicDataGraph) {
	    BasicDataGraph bdg = (BasicDataGraph) m_data_graph;
	    if (!bdg.isHcChanged()) {
		return m_hc;
	    } else {
		bdg.setHcChanged(false); //don't calc it next time unless someone changed things
	    }
	}

        int hc = 0;

        if (hasValue()) {

            //hc += getValue().hashCode();
            hc += getValue().toString().hashCode(); //need the same hash for Integer(5) as String "5"
        }

        //add up keys
        final Iterator iter2 = getMap().keySet().iterator();
        //while (iter2.hasNext()) hc += iter2.next().hashCode();
        while (iter2.hasNext()) hc += iter2.next().toString().hashCode();

        final Iterator iter3 = getMap().values().iterator();
        //while (iter3.hasNext()) hc += iter3.next().hashCode();
        while (iter3.hasNext()) hc += iter3.next().toString().hashCode(); //should be slower but isn't much slower AND there have been some weird bugs with the non-toString'er

	m_hc = hc;

        return hc;
    }
}

