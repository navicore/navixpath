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

package org.navimatrix.commons.data.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class HashList implements java.util.List, java.io.Serializable {

    private final Set m_set; //for fast 'contains'

    private final List m_list; //for List order

    public HashList() {

        this(2);
    }

    public HashList(int size) {

        m_set = new HashSet(size);

        m_list = new ArrayList(size);
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation). <p>
     *
     * Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * @param o element to be appended to this list.
     * @return <tt>true</tt> (as per the general contract of the
     *            <tt>Collection.add</tt> method).
     *
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     * 		  supported by this list.
     * @throws ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws NullPointerException if the specified element is null and this
     *           list does not support null elements.
     * @throws IllegalArgumentException if some aspect of this element
     *            prevents it from being added to this list.
     */
    public boolean add(Object o) {

        m_set.add(o);

        return m_list.add(o);
    }
    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     *
     * @throws UnsupportedOperationException if the <tt>add</tt> method is not
     *		  supported by this list.
     * @throws    ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws    NullPointerException if the specified element is null and
     *            this list does not support null elements.
     * @throws    IllegalArgumentException if some aspect of the specified
     *		  element prevents it from being added to this list.
     * @throws    IndexOutOfBoundsException if the index is out of range
     *		  (index &lt; 0 || index &gt; size()).
     */
    public void add(int index, Object element) {

        m_set.add(element);

        m_list.add(index,element);
    }
    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is unspecified if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection whose elements are to be added to this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException if the <tt>addAll</tt> method is
     *         not supported by this list.
     * @throws ClassCastException if the class of an element in the specified
     * 	       collection prevents it from being added to this list.
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not support null
     *         elements, or if the specified collection is <tt>null</tt>.
     * @throws IllegalArgumentException if some aspect of an element in the
     *         specified collection prevents it from being added to this
     *         list.
     * @see #add(Object)
     */
    public boolean addAll(Collection c) {

        //throw new java.lang.UnsupportedOperationException();
        m_set.addAll(c);

        return m_list.addAll(c);
    }
    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * unspecified if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert first element from the specified
     *	            collection.
     * @param c elements to be inserted into this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException if the <tt>addAll</tt> method is
     *		  not supported by this list.
     * @throws ClassCastException if the class of one of elements of the
     * 		  specified collection prevents it from being added to this
     * 		  list.
     * @throws NullPointerException if the specified collection contains one
     *           or more null elements and this list does not support null
     *           elements, or if the specified collection is <tt>null</tt>.
     * @throws IllegalArgumentException if some aspect of one of elements of
     *		  the specified collection prevents it from being added to
     *		  this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *		  &lt; 0 || index &gt; size()).
     */
    public boolean addAll(int index, Collection c) {

        //throw new java.lang.UnsupportedOperationException();
        m_set.addAll(c);

        return m_list.addAll(index, c);
    }
    /**
     * Removes all of the elements from this list (optional operation).  This
     * list will be empty after this call returns (unless it throws an
     * exception).
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> method is
     * 		  not supported by this list.
     */
    public void clear() {

        m_set.clear();

        m_list.clear();
    }
    /**
     *
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested.
     * @return <tt>true</tt> if this list contains the specified element.
     * @throws ClassCastException if the type of the specified element
     * 	       is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this
     *         list does not support null elements (optional).
     */
    public boolean contains(Object o) {

        return m_set.contains(o);
    }
    /**
     *
     * Returns <tt>true</tt> if this list contains all of the elements of the
     * specified collection.
     *
     * @param  c collection to be checked for containment in this list.
     * @return <tt>true</tt> if this list contains all of the elements of the
     * 	       specified collection.
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         list (optional).
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not support null
     *         elements (optional).
     * @throws NullPointerException if the specified collection is
     *         <tt>null</tt>.
     * @see #contains(Object)
     */
    public boolean containsAll(Collection c) {

        return m_set.containsAll(c);
    }
    /**
     * Compares the specified object with this list for equality.  Returns
     * <tt>true</tt> if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements <tt>e1</tt> and
     * <tt>e2</tt> are <i>equal</i> if <tt>(e1==null ? e2==null :
     * e1.equals(e2))</tt>.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the <tt>List</tt> interface.
     *
     * @param o the object to be compared for equality with this list.
     * @return <tt>true</tt> if the specified object is equal to this list.
     */
    public boolean equals(Object o) {

        if (o == null) {

            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.equals(m_set) || o.equals(m_list)) {

            return true;
        }

        return false;
    }
    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException if the index is out of range (index
     * 		  &lt; 0 || index &gt;= size()).
     */
    public Object get(int index) {

        return m_list.get(index);
    }
    /**
     * Returns the hash code value for this list.  The hash code of a list
     * is defined to be the result of the following calculation:
     * <pre>
     *  hashCode = 1;
     *  Iterator i = list.iterator();
     *  while (i.hasNext()) {
     *      Object obj = i.next();
     *      hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
     *  }
     * </pre>
     * This ensures that <tt>list1.equals(list2)</tt> implies that
     * <tt>list1.hashCode()==list2.hashCode()</tt> for any two lists,
     * <tt>list1</tt> and <tt>list2</tt>, as required by the general
     * contract of <tt>Object.hashCode</tt>.
     *
     * @return the hash code value for this list.
     * @see Object#hashCode()
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    public int hashCode() {

        return 90 + m_list.hashCode();
    }
    /**
     * Returns the index in this list of the first occurrence of the specified
     * element, or -1 if this list does not contain this element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for.
     * @return the index in this list of the first occurrence of the specified
     * 	       element, or -1 if this list does not contain this element.
     * @throws ClassCastException if the type of the specified element
     * 	       is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this
     *         list does not support null elements (optional).
     */
    public int indexOf(Object o) {

        return m_list.indexOf(o);
    }
    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements.
     */
    public boolean isEmpty() {

        return m_set.isEmpty();
    }
    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * todo: bug, iterator is read-only
     *
     * @return an iterator over the elements in this list in proper sequence.
     */
    public Iterator iterator() {

        return m_list.iterator();
    }
    /**
     * Returns the index in this list of the last occurrence of the specified
     * element, or -1 if this list does not contain this element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for.
     * @return the index in this list of the last occurrence of the specified
     * 	       element, or -1 if this list does not contain this element.
     * @throws ClassCastException if the type of the specified element
     * 	       is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this
     *         list does not support null elements (optional).
     */
    public int lastIndexOf(Object o) {

        return m_list.lastIndexOf(o);
    }
    /**
     * Returns a list iterator of the elements in this list (in proper
     * sequence), starting at the specified position in this list.  The
     * specified index indicates the first element that would be returned by
     * an initial call to the <tt>next</tt> method.  An initial call to
     * the <tt>previous</tt> method would return the element with the
     * specified index minus one.
     *
     * @param index index of first element to be returned from the
     *		    list iterator (by a call to the <tt>next</tt> method).
     * @return a list iterator of the elements in this list (in proper
     * 	       sequence), starting at the specified position in this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *         &lt; 0 || index &gt; size()).
     */
    public ListIterator listIterator(int index) {

        return m_list.listIterator(index);
    }
    /**
     * Returns a list iterator of the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator of the elements in this list (in proper
     * 	       sequence).
     */
    public ListIterator listIterator() {

        return m_list.listIterator();
    }
    /**
     * Removes the first occurrence in this list of the specified element
     * (optional operation).  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index i
     * such that <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt> (if
     * such an element exists).
     *
     * @param o element to be removed from this list, if present.
     * @return <tt>true</tt> if this list contained the specified element.
     * @throws ClassCastException if the type of the specified element
     * 	          is incompatible with this list (optional).
     * @throws NullPointerException if the specified element is null and this
     *            list does not support null elements (optional).
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *		  not supported by this list.
     */
    public boolean remove(Object o) {

        m_set.remove(o);

        return m_list.remove(o);
    }
    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to removed.
     * @return the element previously at the specified position.
     *
     * @throws UnsupportedOperationException if the <tt>remove</tt> method is
     *		  not supported by this list.
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *            &lt; 0 || index &gt;= size()).
     */
    public Object remove(int index) {

        Object o = m_list.remove(index);
        m_set.remove(o);

        return o;
    }
    /**
     * Removes from this list all the elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection that defines which elements will be removed from
     *          this list.
     * @return <tt>true</tt> if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     * 		  is not supported by this list.
     * @throws ClassCastException if the types of one or more elements
     *            in this list are incompatible with the specified
     *            collection (optional).
     * @throws NullPointerException if this list contains one or more
     *            null elements and the specified collection does not support
     *            null elements (optional).
     * @throws NullPointerException if the specified collection is
     *            <tt>null</tt>.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection c) {

        //throw new java.lang.UnsupportedOperationException();
        m_set.removeAll(c);

        return m_list.removeAll(c);
    }
    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all the elements that are not contained in the specified
     * collection.
     *
     * @param c collection that defines which elements this set will retain.
     *
     * @return <tt>true</tt> if this list changed as a result of the call.
     *
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> method
     * 		  is not supported by this list.
     * @throws ClassCastException if the types of one or more elements
     *            in this list are incompatible with the specified
     *            collection (optional).
     * @throws NullPointerException if this list contains one or more
     *            null elements and the specified collection does not support
     *            null elements (optional).
     * @throws NullPointerException if the specified collection is
     *         <tt>null</tt>.
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(Collection c) {

        //throw new java.lang.UnsupportedOperationException();
        m_set.retainAll(c);

        return m_list.retainAll(c);
    }
    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     *
     * @throws UnsupportedOperationException if the <tt>set</tt> method is not
     *		  supported by this list.
     * @throws    ClassCastException if the class of the specified element
     * 		  prevents it from being added to this list.
     * @throws    NullPointerException if the specified element is null and
     *            this list does not support null elements.
     * @throws    IllegalArgumentException if some aspect of the specified
     *		  element prevents it from being added to this list.
     * @throws    IndexOutOfBoundsException if the index is out of range
     *		  (index &lt; 0 || index &gt;= size()).
     */
    public Object set(int index, Object element) {

        //throw new java.lang.UnsupportedOperationException();
        m_set.add(element);

        return m_list.set(index,element);
    }
    /**
     * Returns the number of elements in this list.  If this list contains
     * more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this list.
     */
    public int size() {

        return m_list.size();
    }
    /**
     * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     *
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).   Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>
     *	    list.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for <tt>indexOf</tt> and
     * <tt>lastIndexOf</tt>, and all of the algorithms in the
     * <tt>Collections</tt> class can be applied to a subList.<p>
     *
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList.
     * @param toIndex high endpoint (exclusive) of the subList.
     * @return a view of the specified range within this list.
     *
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *     (fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex).
     */
    public List subList(int fromIndex, int toIndex) {

        return m_list.subList(fromIndex,toIndex);
    }
    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence.  Obeys the general contract of the
     * <tt>Collection.toArray</tt> method.
     *
     * @return an array containing all of the elements in this list in proper
     *	       sequence.
     * @see Arrays#asList(Object[])
     */
    public Object[] toArray() {

        return m_list.toArray();
    }
    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence; the runtime type of the returned array is that of the
     * specified array.  Obeys the general contract of the
     * <tt>Collection.toArray(Object[])</tt> method.
     *
     * @param a the array into which the elements of this list are to
     *		be stored, if it is big enough; otherwise, a new array of the
     * 		same runtime type is allocated for this purpose.
     * @return  an array containing the elements of this list.
     *
     * @throws ArrayStoreException if the runtime type of the specified array
     * 		  is not a supertype of the runtime type of every element in
     * 		  this list.
     * @throws NullPointerException if the specified array is <tt>null</tt>.
     */
    public Object[] toArray(Object[] a) {

        return m_list.toArray(a);
    }
}

