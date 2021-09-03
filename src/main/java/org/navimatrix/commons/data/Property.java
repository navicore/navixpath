/* 
 * partial implementation of ibm's and bea's draft sdo spec.  will implement the spec
 * once they release a finished *working* recomendation
 */

package org.navimatrix.commons.data;

public interface Property {

    public String getName();

    public Type getType();

    public boolean isMany();

    public Object getDefault();

    public boolean isContainer();
}
