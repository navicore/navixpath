package org.navimatrix.commons.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.navimatrix.commons.data.sdoimpl.BasicDataObject;
import org.navimatrix.commons.data.sdoimpl.BasicDataObjectImpl;
import org.navimatrix.commons.data.util.Base16;

public class DataUtil {

    public static String resource2String(URL res) throws IOException {

        if (res == null) return null;

        InputStream is = res.openStream();
        InputStreamReader isr = new InputStreamReader(is, "8859_1");
        StringBuffer sb = new StringBuffer();

        while (isr.ready()) {
            char b = (char) isr.read();
            sb.append(b);
        }
        isr.close();

        String ret = sb.toString();
        return ret;
    }

    public static String resource2String(Class cl, String url, String file) throws IOException {
        if (file == null) return null;
        URL res;
        if (url != null)
            res = cl.getResource(url + file);
        else
            res = cl.getResource(file);

        return resource2String(res);
    }

    /**
     * get a bunch of resources turned to strings.  key for Data returned
     * is the files[i] content.
     * 
     * @param cl     the url+files[i] name is relative to this class's classloader source.
     * @param url    1st part of the filename or null.  when files[0] = mytable.sql,
     *               usually a dir name and/or a filename prefix, like resources/ for
     *               a full url of resources/mytable.sql.
     *               
     *               or if the url is resources/hsqldb_ the full url is
     *               resources/hsqldb_mytable.sql.
     *               
     *               "/" is NOT inserted.  so url isn't so accurate a name as prefix.
     * @param files
     * 
     * @return 
     * @exception IOException
     */
    public static Data resource2String(Class cl, String url, String[] files) throws IOException {
        if (files == null) return null;
        Data data = DataFactory.createData();
        for (int i = 0; i < files.length; i++) {
            data.put(files[i], resource2String(cl, url, files[i]));
        }
        return data;
    }

    public static String[] getStringFields(Object object, String prefix) {

        if (object == null) return null;

        Class cl = object.getClass();

        Field[] fields = cl.getFields();
        if (fields == null) return null;
        Set set = new HashSet();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().startsWith(prefix)) {
                set.add(fields[i]);
            }
        }

        String[] ret = new String[set.size()];

        Iterator iter = set.iterator();

        for (int i = 0; iter.hasNext(); i++) {
            Field f = (Field) iter.next();
            try {
                ret[i] = (String) f.get(object);
            } catch (Exception e) {
                throw new java.lang.UnsupportedOperationException("can not reflect"); //i18n
            }
        }

        return ret;
    }

    //
    // todo: test.  presumes that instanceof checks are faster than to/from String
    //  and constructing numbers from String arg
    //

    public static Integer getInteger(Object o) {
        if (o == null) return null;
        if (o instanceof Integer) {
            return(Integer) o;
        }
        if (o instanceof BasicDataObject) {
            return getInteger(((BasicDataObject)o).getValue());
        }

        try {
            return new Integer(o.toString());
        } catch (java.lang.NumberFormatException nfe) {
            if (o.toString().endsWith(".0")) {
                return new Integer(o.toString().substring(0, o.toString().length() - 2));
            }
            throw nfe;
        }
    }

    public static Long getLong(Object o) {
        if (o == null) return null;
        if (o instanceof Long) {
            return(Long) o;
        }
        if (o instanceof BasicDataObject) {
            return getLong(((BasicDataObject)o).getValue());
        }
        try {
            return new Long(o.toString());
        } catch (java.lang.NumberFormatException nfe) {
            if (o.toString().endsWith(".0")) {
                return new Long(o.toString().substring(0, o.toString().length() - 2));
            }
            throw nfe;
        }
    }

    public static Double getDouble(Object o) {
        if (o == null) return null;
        if (o instanceof Double) {
            return(Double) o;
        }
        if (o instanceof BasicDataObject) {
            return getDouble(((BasicDataObject)o).getValue());
        }
        return new Double(o.toString());
    }

    public static BigDecimal getBigDecimal(Object o) {
        if (o == null) return null;
        if (o instanceof BigDecimal) {
            return(BigDecimal) o;
        }
        if (o instanceof BasicDataObject) {
            return getBigDecimal(((BasicDataObject)o).getValue());
        }
        return new BigDecimal(o.toString());
    }

    public static BigInteger getBigInteger(Object o) {
        if (o == null) return null;
        if (o instanceof BigInteger) {
            return(BigInteger) o;
        }
        if (o instanceof BasicDataObject) {
            return getBigInteger(((BasicDataObject)o).getValue());
        }
        return new BigInteger(o.toString());
    }

    public static Boolean getBoolean(Object o) {
        if (o == null) return null;
        if (o instanceof Boolean) {
            return(Boolean) o;
        }
        if (o instanceof Byte) {
            if (((Byte) o).byteValue() == 0 ) {
                return new Boolean(false);
            } else {
                return new Boolean(true);
            }
        }
        if (o instanceof BasicDataObject) {
            return getBoolean(((BasicDataObject)o).getValue());
        }
        return Boolean.valueOf(o.toString());
    }

    public static Byte getByte(Object o) {
        if (o == null) return null;
        if (o instanceof Byte) {
            return(Byte) o;
        }
        if (o instanceof Boolean) {
            Boolean bool = (Boolean) o;
            if (bool.booleanValue()) {
                return new Byte((byte)1);
            } else {
                return new Byte((byte)0);
            }
        }
        if (o instanceof BasicDataObject) {
            return getByte(((BasicDataObject)o).getValue());
        }
        return new Byte(o.toString());
    }

    public static Float getFloat(Object o) {
        if (o == null) return null;
        if (o instanceof Float) {
            return(Float) o;
        }
        if (o instanceof BasicDataObject) {
            return getFloat(((BasicDataObject)o).getValue());
        }
        return new Float(o.toString());
    }

    public static Character getCharacter(Object o) {
        if (o == null) return null;
        if (o instanceof Character) {
            return(Character) o;
        }
        if (o instanceof BasicDataObject) {
            return getCharacter(((BasicDataObject)o).getValue());
        }
        //improbable
        return new Character(o.toString().charAt(0));
    }

    public static Short getShort(Object o) {
        if (o == null) return null;
        if (o instanceof Short) {
            return(Short) o;
        }
        if (o instanceof BasicDataObject) {
            return getShort(((BasicDataObject)o).getValue());
        }
        return new Short(o.toString());
    }

    public static Date getDate(Object o) {
        if (o == null) return null;
        if (o instanceof Date) {
            return(Date) o;
        }
        if (o instanceof BasicDataObject) {
            return getDate(((BasicDataObject)o).getValue());
        }
        return new Date(getLong(o).longValue());
    }

    public static byte[] getBytes(Object o) {
        if (o == null) return null;
        if (o instanceof byte[]) {
            return(byte[]) o;
        }
        if (o instanceof BasicDataObject) {
            return getBytes(((BasicDataObject)o).getValue());
        }
        if (o instanceof String) {
            //assume Base16
            return Base16.decode((String) o);
        }
        if (o instanceof char[]) {
            return(byte[]) o;
        }
        return o.toString().getBytes(); //i18n?? need a lookup of default charset?
    }

    public static String getString(Object o) {
        if (o == null) return null;
        if (o instanceof BasicDataObject) {
            //return getString(((BasicDataObject)o).getValue());
            Object newo = ((BasicDataObject)o).getValue();
            if (newo instanceof BasicDataObject) {
                BasicDataObject newdo = (BasicDataObject) newo;
                throw new java.lang.IllegalArgumentException("error.  value is a BasicDataObject,  should be primitive.  recursive error warning.  type: " + newdo.getType().getName());
            } else {
                return getString(newo);
            }
        }
        if (o instanceof byte[]) {
            return new String((byte[]) o); //i18n
        }
        //todo: what else?
        return o.toString();
    }

    //
    // begin DataUtil utils
    //

    public static boolean isAttribute(Object data) {
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;

            String type_name = dataobj.getType().getName();

            if (type_name == null || type_name.length() == 0) {
                return false;
            }

            boolean attr = dataobj.getType().getName().charAt(0) == '@';

            return attr;
        }
        return false;
    }

    public static boolean hasProperty(Object data, String propertyName) {
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;
            return dataobj.isSet(propertyName);
        }
        return false;
    }

    public static DataObject getRoot(Object data) {
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;
            return dataobj.getDataGraph().getRootObject();
        }
        return null;
    }
    public static DataObject getParent(Object data) {
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;
            return dataobj.getContainer();
        }
        return null;
    }
    public static String getAttributeName(Object data) {
        String name = getName(data);

        if (name != null && (name.length() != 0) && name.charAt(0) == '@') {
            return name.substring(1, name.length());
        }
        return name;
    }
    public static String getName(Object data) {
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;
            return dataobj.getType().getName();
        }
        return null;
    }
    public static String getURI(Object data) { 
        if (data instanceof DataObject) {
            DataObject dataobj = (DataObject) data;
            return dataobj.getType().getURI();
        }
        return null;
    }

    //single object iterator
    public static Iterator getIterator(final Object object) {

        if (object instanceof List) {
            return((List) object).iterator();
        }

        if (object instanceof Collection) {
            return((Collection) object).iterator();
        }

        return new Iterator() {
            boolean hasNext = true;
            public boolean hasNext() {
                if (object == null) {
                    return false;
                }
                return hasNext;
            }
            public Object next() {
                if (object == null || !hasNext) {
                    throw new java.util.NoSuchElementException();
                }
                hasNext = false;
                return object;
            }
            public void remove() {
                //noop
            }
        };
    }

    //
    // sort
    //
    public static List sortByNumbers(List list, String path) {

        Comparator comparator = new DataObjectComparator(path, true);

        return sort(list,comparator);
    }

    public static List sortByStrings(List list, String path) {

        Comparator comparator = new DataObjectComparator(path, false);

        return sort(list,comparator);
    }

    private static List sort(List list, Comparator comparator) {

        //Object[] items = list.toArray(); //does this always work?

        Object[] items = new Object[list.size()];

        for (int i = 0; i < items.length; i++) {

            items[i] = list.get(i);
        }

        Arrays.sort(items, comparator);

        List ret = new ArrayList();

        for (int i = 0; i < items.length; i++) {

            ret.add(items[i]);
        }

        return ret;
    }

    private static class DataObjectComparator implements Comparator {

        private final String m_path;

        private final boolean m_numeric;

        private Comparator m_string_comparator;

        public DataObjectComparator(String path, boolean numeric) { 

            m_path = path;

            m_numeric = numeric;

            if (!numeric) {
                m_string_comparator = java.text.Collator.getInstance();
            }
        }

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         *
         * The implementor must ensure that <tt>sgn(compare(x, y)) ==
         * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
         * implies that <tt>compare(x, y)</tt> must throw an exception if and only
         * if <tt>compare(y, x)</tt> throws an exception.)<p>
         *
         * The implementor must also ensure that the relation is transitive:
         * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
         * <tt>compare(x, z)&gt;0</tt>.<p>
         *
         * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
         * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
         * <tt>z</tt>.<p>
         *
         * It is generally the case, but <i>not</i> strictly required that
         * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * 	       first argument is less than, equal to, or greater than the
         *	       second.
         * @throws ClassCastException if the arguments' types prevent them from
         * 	       being compared by this Comparator.
         */
        public int compare(Object o1, Object o2) {

            if (!(o1 instanceof DataObject) || !(o2 instanceof DataObject))
                throw new java.lang.IllegalArgumentException("sort array must be all DataObjects");

            DataObject data1 = (DataObject) o1;
            DataObject data2 = (DataObject) o2;

            String s1 = data1.getString(m_path);
            String s2 = data2.getString(m_path);

            //put nulls at the end
            if (s1 == null && s2 == null) return 0;
            if (s1 == null) return +1;
            if (s2 == null) return +1;

            if (m_numeric) {

                if (s1 == null && s2 == null) return 0;
                if (s1 == null) return +1;
                if (s2 == null) return +1;

                double n1 = 0;
                double n2 = 0;
                try {
                    n1 = Double.valueOf(s1).doubleValue();
                } catch (Exception e) {
                }
                try {
                    n2 = Double.valueOf(s2).doubleValue();
                } catch (Exception e) {
                }

                if (n1 == n2) return 0;
                if (n1 < n2) return -1;
                return 1;

            } else {

                return m_string_comparator.compare(s1, s2);
            }
        }

        /**
         *
         * Indicates whether some other object is &quot;equal to&quot; this
         * Comparator.  This method must obey the general contract of
         * <tt>Object.equals(Object)</tt>.  Additionally, this method can return
         * <tt>true</tt> <i>only</i> if the specified Object is also a comparator
         * and it imposes the same ordering as this comparator.  Thus,
         * <code>comp1.equals(comp2)</code> implies that <tt>sgn(comp1.compare(o1,
         * o2))==sgn(comp2.compare(o1, o2))</tt> for every object reference
         * <tt>o1</tt> and <tt>o2</tt>.<p>
         *
         * Note that it is <i>always</i> safe <i>not</i> to override
         * <tt>Object.equals(Object)</tt>.  However, overriding this method may,
         * in some cases, improve performance by allowing programs to determine
         * that two distinct Comparators impose the same order.
         *
         * @param   obj   the reference object with which to compare.
         * @return  <code>true</code> only if the specified object is also
         *		a comparator and it imposes the same ordering as this
         *		comparator.
         * @see     java.lang.Object#equals(java.lang.Object)
         * @see java.lang.Object#hashCode()
         */
        public boolean equals(Object obj) {

            return super.equals(obj);  //trick?
        }
    }


    //use this to copy fields from one data object to another
    public static void copyDataObject(DataObject source, DataObject target) {

        for (Iterator iter = source.getType().getProperties().iterator(); iter.hasNext();) {

            Property property = (Property) iter.next();

            target.set("/" + property.toString(), source.getDataObject(property).clone()); //update / overwrite
        }
    }


    public static boolean isXPath(String path) {

        if (path == null) return false;
        if (path.charAt(0) == '/') return true;
        if (path.charAt(0) == '.') return true;
        //if (path.charAt(0) == '@') return true; //THIS CAUSES STACK OVERFLOW!
        int last = path.length() - 1;
        if (path.charAt(last) == ')') return true;
        if (path.charAt(last) == ']') return true;
        return false;

    }
}
