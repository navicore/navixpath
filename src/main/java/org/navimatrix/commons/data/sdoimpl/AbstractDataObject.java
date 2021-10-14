package org.navimatrix.commons.data.sdoimpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.navimatrix.commons.data.DataGraph;
import org.navimatrix.commons.data.DataObject;
import org.navimatrix.commons.data.DataUtil;
import org.navimatrix.commons.data.Property;
import org.navimatrix.commons.data.Type;
import org.navimatrix.commons.data.util.Base16;

public abstract class AbstractDataObject implements DataObject {


    abstract public Object clone();

    //
    // begin DataObject ifc
    //
    abstract public DataObject createDataObject(String propertyName);

    abstract public DataObject createDataObject(String propertyName, String namespaceURI, String prefix);



    public float getFloat(int propertyIndex) {
        return getFloat((Property) getType().getProperties().get(propertyIndex));
    }
    public int getInt(int propertyIndex) {
        //System.out.println("ejs index: " + propertyIndex + " p: " + getType().getProperties().get(propertyIndex));
        return getInt((Property) getType().getProperties().get(propertyIndex));
    }
    public long getLong(int propertyIndex) {
        return getLong((Property) getType().getProperties().get(propertyIndex));
    }
    public short getShort(int propertyIndex) {
        return getShort((Property) getType().getProperties().get(propertyIndex));
    }
    public byte[] getBytes(int propertyIndex) {
        return getBytes((Property) getType().getProperties().get(propertyIndex));
    }
    public BigDecimal getBigDecimal(int propertyIndex) {
        return getBigDecimal((Property) getType().getProperties().get(propertyIndex));
    }
    public BigInteger getBigInteger(int propertyIndex) {
        return getBigInteger((Property) getType().getProperties().get(propertyIndex));
    }
    public DataObject getDataObject(int propertyIndex) {
        return getDataObject((Property) getType().getProperties().get(propertyIndex));
    }
    public Date getDate(int propertyIndex) {
        return getDate((Property) getType().getProperties().get(propertyIndex));
    }
    public String getString(int propertyIndex) {
        return getString((Property) getType().getProperties().get(propertyIndex));
    }
    public boolean getBoolean(int propertyIndex) {
        return getBoolean((Property) getType().getProperties().get(propertyIndex));
    }
    public byte getByte(int propertyIndex) {
        return getByte((Property) getType().getProperties().get(propertyIndex));
    }
    public char getChar(int propertyIndex) {
        return getChar((Property) getType().getProperties().get(propertyIndex));
    }
    public double getDouble(int propertyIndex) {
        return getDouble((Property) getType().getProperties().get(propertyIndex));
    }
    public List getList(int propertyIndex) {
        return getList((Property) getType().getProperties().get(propertyIndex));
    }



    public void setFloat(int propertyIndex, float value) {
        setFloat((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setDataObject(int propertyIndex, DataObject value) {
        setDataObject((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setBoolean(int propertyIndex, boolean value) {
        setBoolean((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setByte(int propertyIndex, byte value) {
        setByte((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setChar(int propertyIndex, char value) {
        setChar((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setDouble(int propertyIndex, double value) {
        setDouble((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setInt(int propertyIndex, int value) {
        setInt((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setLong(int propertyIndex, long value) {
        setLong((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setShort(int propertyIndex, short value) {
        setShort((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setBytes(int propertyIndex, byte[] value) {
        setBytes((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setBigDecimal(int propertyIndex, BigDecimal value) {
        setBigDecimal((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setBigInteger(int propertyIndex, BigInteger value) {
        setBigInteger((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setDate(int propertyIndex, Date value) {
        setDate((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setString(int propertyIndex, String value) {
        setString((Property) getType().getProperties().get(propertyIndex), value);
    }
    public void setList(int propertyIndex, List value) {
        setList((Property) getType().getProperties().get(propertyIndex), value);
    }



    public boolean getBoolean(Property property) {
        return DataUtil.getBoolean(getValue(property)).booleanValue();
    }
    public byte getByte(Property property) {
        return DataUtil.getByte(getValue(property)).byteValue();
    }
    public char getChar(Property property) {
        return DataUtil.getCharacter(getValue(property)).charValue();
    }
    public double getDouble(Property property) {
        return DataUtil.getDouble(getValue(property)).doubleValue();
    }
    public float getFloat(Property property) {
        return DataUtil.getFloat(getValue(property)).floatValue();
    }
    public int getInt(Property property) {
        return DataUtil.getInteger(getValue(property)).intValue();
    }
    public long getLong(Property property) {
        return DataUtil.getLong(getValue(property)).longValue();
    }
    public short getShort(Property property) {
        return DataUtil.getShort(getValue(property)).shortValue();
    }
    public byte[] getBytes(Property property) {
        return DataUtil.getBytes(getValue(property));
    }
    public BigDecimal getBigDecimal(Property property) {
        return DataUtil.getBigDecimal(getValue(property));
    }
    public BigInteger getBigInteger(Property property) {
        return DataUtil.getBigInteger(getValue(property));
    }
    public abstract DataObject getDataObject(Property property);

    public Date getDate(Property property) {
        return DataUtil.getDate(getValue(property));
    }
    public String getString(Property property) {
        return DataUtil.getString(getValue(property));
    }

    public List getList(Property property) {

        Object ret = get(property);

        if (ret == null) {

            return null;
        }

        if (ret instanceof List) {

            return(List) ret;
        }

        List l = new DataObjectList((BasicProperty) property);

        l.add(ret);

        unset(property);

        setList(property, l);

        return l;
    }


    protected abstract Object getValue(Property property);


    public void setDate(Property property, Date value) {
        set(property, value);
    }
    public void setString(Property property, String value) {
        set(property, value);
    }
    public void setBoolean(Property property, boolean value) {
        //set(property, new Boolean(value));
        set(property, Boolean.valueOf(value));
    }
    public void setByte(Property property, byte value) {
        set(property, new Byte(value));
    }
    public void setChar(Property property, char value) {
        set(property, new Character(value));
    }
    public void setDouble(Property property, double value) {
        set(property, new Double(value));
    }
    public void setFloat(Property property, float value) {
        set(property, new Float(value));
    }
    public void setInt(Property property, int value) {
        set(property, new Integer(value));
    }
    public void setLong(Property property, long value) {
        set(property, new Long(value));
    }
    public void setShort(Property property, short value) {
        set(property, new Short(value));
    }
    public void setBytes(Property property, byte[] value) {
        //not optimized for bytes... todo: make this as lazy as possible.
        //  b64 is only here to make xml round trip coding work
        String sval = Base16.encode(value);
        set(property, sval);
    }
    public void setBigDecimal(Property property, BigDecimal value) {
        set(property, value);
    }
    public void setBigInteger(Property property, BigInteger value) {
        set(property, value);
    }
    public void setDataObject(Property property, DataObject value) {
        set(property, value);
    }
    public void setList(Property property, List value) {
        set(property, value);
    }


    abstract public Object get(String path);

    abstract public void set(String path, Object value);

    abstract public boolean isSet(String path);

    abstract public void unset(String path);


    public char getChar(String path) {
        return DataUtil.getCharacter(get(path)).charValue();
    }
    public boolean getBoolean(String path) {
        return DataUtil.getBoolean(get(path)).booleanValue();
    }
    public int getInt(String path) {
        return DataUtil.getInteger(get(path)).intValue();
    }
    public long getLong(String path) {
        return DataUtil.getLong(get(path)).longValue();
    }
    public double getDouble(String path) {
        return DataUtil.getDouble(get(path)).doubleValue();
    }
    public float getFloat(String path) {
        return DataUtil.getFloat(get(path)).floatValue();
    }
    public BigDecimal getBigDecimal(String path) {
        return DataUtil.getBigDecimal(get(path));
    }
    public BigInteger getBigInteger(String path) {
        return DataUtil.getBigInteger(get(path));
    }
    public short getShort(String path) {
        return DataUtil.getShort(get(path)).shortValue();
    }
    public byte[] getBytes(String path) {
        return DataUtil.getBytes(get(path));
    }
    public String getString(String path) {
        return DataUtil.getString(get(path));
    }
    public List getList(String path) {

        Object ret = get(path);

        if (ret == null) {
            return null;
        }

        if (ret instanceof List) {

            if (ret instanceof DataObjectList) {

                return(List) ret;

            } else {

                //this is probably a jaxen created ArrayList that needs
                // to be wrapped in something smart enough to handle
                // the remove method properly

                return new SDOBackedList((List) ret);
            }
        }

        List l;
        if (ret instanceof BasicDataObject) {
            BasicProperty p = (BasicProperty) ((DataObject) ret).getContainmentProperty();
            l = new DataObjectList(p); //keeps isMany correct
            /* ejs 10/9/2005
            unset(p);
            setList(p,l);
            */
        } else {
            l = new ArrayList(2); //Property isMany may become incorrect ... read only
        }

        l.add(ret);
        return l;
    }

    public abstract DataObject getDataObject(String path);

    public Date getDate(String path) {
        return DataUtil.getDate(get(path));
    }
    public byte getByte(String path) {
        return DataUtil.getByte(get(path)).byteValue();
    }



    public void setByte(String path, byte value) {
        set(path, new Byte(value));
    }
    public void setChar(String path, char value) {
        set(path, new Character(value));
    }
    public void setBoolean(String path, boolean value) {
        //set(path, new Boolean(value));
        set(path, Boolean.valueOf(value));
    }
    public void setInt(String path, int value) {
        set(path, new Integer(value));
    }
    public void setLong(String path, long value) {
        set(path, new Long(value));
    }
    public void setDouble(String path, double value) {
        set(path, new Double(value));
    }
    public void setFloat(String path, float value) {
        set(path, new Float(value));
    }
    public void setBigDecimal(String path, BigDecimal value) {
        set(path, value);
    }
    public void setShort(String path, short value) {
        set(path, new Short(value));
    }
    public void setBytes(String path, byte[] value) {
        //not optimized for bytes... todo: make this as lazy as possible.
        //  b64 is only here to make xml round trip coding work
        String sval = Base16.encode(value);
        set(path, sval);
    }
    public void setString(String path, String value) {
        set(path, value);
    }
    public void setDataObject(String path, DataObject value) {
        set(path, value);
    }
    public void setBigInteger(String path, BigInteger value) {
        set(path, value);
    }
    public void setDate(String path, Date value) {
        set(path, value);
    }
    public void setList(String path, List value) {
        set(path, value);
    }



    public Object get(int propertyIndex) {
        return getType().getProperties().get(propertyIndex);
    }
    public void set(int propertyIndex, Object value) {
        getType().getProperties().set(propertyIndex, value);
    }
    public boolean isSet(int propertyIndex) {
        Property p = (Property) getType().getProperties().get(propertyIndex);
        return isSet(p);
    }
    public void unset(int propertyIndex) {
        Property p = (Property) getType().getProperties().get(propertyIndex);
        unset(p);
    }



    public void delete() {
        List props = getType().getProperties();
        props.clear();

        DataObject ct = getContainer();
        BasicProperty pr = (BasicProperty) getContainmentProperty();
        if (ct != null && pr != null) {
            pr.setIsContainer(false);
            ct.unset(pr);
        }
    }



    abstract public DataObject getContainer();
    abstract public Property getContainmentProperty();
    abstract public DataGraph getDataGraph();
    abstract public Type getType();


    //return a data object even if it is a value
    abstract public Object get(Property property);

    abstract public void set(Property property, Object value);

    public boolean isSet(Property property) {
        throw new UnsupportedOperationException();
    }

    public void unset(Property property) {
        throw new UnsupportedOperationException();
    }

    /**
     * keep isMany correct if the collection is updated
     * via the List ifc
     */
    protected static class DataObjectList extends ArrayList {

        private BasicProperty property;

        protected DataObjectList() {
            this(null);
        }
        protected DataObjectList(BasicProperty property) {
            super();
            //System.out.println("********** new list: " + property);
            this.property = property;
        }
        public void setProperty(Property property) {
            this.property = (BasicProperty) property;
        }
        public Object remove(int index) {
            Object ret = super.remove(index);
            if (property != null) property.setIsMany(size() > 1);
            else if (ret instanceof BasicDataObject) {
                BasicDataObject bdo = (BasicDataObject) ret;
                bdo.getContainer().unset(bdo.getContainmentProperty());
            }
            return ret;
        }
        public boolean add(Object o) {
	    //so this is what java 5 is for?
	    if (o != null && !(o instanceof DataObject)) {
		throw new java.lang.IllegalArgumentException("can not add a " + o.getClass().getName() + " to a DataObjectList");
	    }
            //todo: bug for unset property
            boolean ret = super.add(o);
            if (property != null) property.setIsMany(size() > 1);
            return ret;
        }
        public void add(int index, Object element) {
	    if (element != null && !(element instanceof DataObject)) {
		throw new java.lang.IllegalArgumentException("can not add a " + element.getClass().getName() + " to a DataObjectList");
	    }
            //todo: bug for unset property
            super.add(index,element);
            if (property != null) property.setIsMany(size() > 1);
        }
        public boolean addAll(Collection c) {
            //todo: bug for unset property
            boolean ret = super.addAll(c);
            if (property != null) property.setIsMany(size() > 1);
            return ret;
        }
        public boolean addAll(int index, Collection c) {
            //todo: bug for unset property
            boolean ret = super.addAll(index,c);
            if (property != null) property.setIsMany(size() > 1);
            return ret;
        }
        public boolean remove(Object o) {
            boolean ret = super.remove(o);
            if (property != null) property.setIsMany(size() > 1);
            else if (o instanceof BasicDataObject) {
                BasicDataObject bdo = (BasicDataObject) o;
                bdo.getContainer().unset(bdo.getContainmentProperty());
            }

            return ret;
        }
        public boolean removeAll(Collection c) {
            //todo: bug for unset property
            boolean ret = super.removeAll(c);
            if (property != null) property.setIsMany(size() > 1);
            return ret;
        }
        //todo: bug for unset property
        // need Iterator impl for unset property usage
    }


    //
    // begin support for Lists that support remove on list items return from xpath queries
    //
    protected static class SDOBackedListIterator implements java.util.Iterator {

        private final Iterator m_iter;

        private Object m_current = null;

        public SDOBackedListIterator(Iterator iter) {

            m_iter = iter;
        }

        public synchronized boolean hasNext() {

            return m_iter.hasNext();
        }

        public synchronized Object next() {

            m_current = m_iter.next();

            if (m_current == null) {

                throw new java.lang.IllegalStateException();
            }

            return m_current;
        }

        public synchronized void remove() {

            m_iter.remove();

            removeListEntry(m_current);
        }
    }

    protected static void removeListEntry(Object item) {

        if (!(item instanceof BasicDataObject)) {

            throw new java.lang.IllegalArgumentException();
        }

        BasicDataObject data = (BasicDataObject) item;

        DataObject parent = data.getContainer();
        BasicProperty containmentproperty = (BasicProperty) data.getContainmentProperty();

        if (!containmentproperty.isMany()) {
            parent.unset(containmentproperty);
        } else {
            List l = parent.getList(containmentproperty);
            boolean ret = l.remove(item);
            if (l.size() <= 1) {
                containmentproperty.setIsMany(false);
                parent.unset(containmentproperty);
                parent.set(containmentproperty, item);
            }
        }
    }

    protected static class SDOBackedList implements java.util.List {

        private final List m_list;

        public SDOBackedList(List list) {

            m_list = list;
        }

        public Iterator iterator() {
            //use local impl
            return new SDOBackedListIterator(m_list.iterator());
        }
        public int size() {

            return m_list.size();
        }
        public boolean isEmpty() {

            return m_list.isEmpty();
        }
        public Object[] toArray(Object a[]) {

            return m_list.toArray(a);
        }
        public boolean contains(Object o) {

            return m_list.contains(o);
        }
        public boolean remove(Object o) {

            //use local impl

            boolean removed = m_list.remove(o);

            if (removed) {
                removeListEntry(o);
            }

            return removed;
        }
        public Object[] toArray() {

            return m_list.toArray();
        }
        public boolean addAll(Collection c) {

            throw new java.lang.UnsupportedOperationException();
        }
        public boolean add(Object o) {

            throw new java.lang.UnsupportedOperationException();
        }
        public boolean removeAll(Collection c) {

            throw new java.lang.UnsupportedOperationException();
        }
        public boolean containsAll(Collection c) {

            throw new java.lang.UnsupportedOperationException();
        }
        public void clear() {

            //use local impl
            for (Iterator iter = iterator(); iter.hasNext();) {
                iter.remove();
            }
        }
        public boolean addAll(int index, Collection c) {

            throw new java.lang.UnsupportedOperationException();
        }
        public int hashCode() {

            return 50+m_list.hashCode();
        }
        public boolean retainAll(Collection c) {

            throw new java.lang.UnsupportedOperationException();
        }
        public Object set(int index, Object element) {

            throw new java.lang.UnsupportedOperationException();
        }
        /*
        public boolean equals(Object o) {

            return super.equals(o);
        }
        */
        public Object remove(int index) {

            //use local impl
            Object o = get(index);
            remove(o);
            return o;
        }
        public Object get(int index) {

            return m_list.get(index);
        }
        public int lastIndexOf(Object o) {

            return m_list.indexOf(o);
        }
        public void add(int index, Object element) {

            throw new java.lang.UnsupportedOperationException();
        }
        public ListIterator listIterator(int index) {

            throw new java.lang.UnsupportedOperationException();
        }
        public int indexOf(Object o) {

            return m_list.indexOf(o);
        }
        public ListIterator listIterator() {

            throw new java.lang.UnsupportedOperationException();
        }
        public List subList(int fromIndex, int toIndex) {

            throw new java.lang.UnsupportedOperationException();
        }
    }
    //
    // end support for Lists that support remove on list items return from xpath queries
    //
}
