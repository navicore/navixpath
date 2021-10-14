package org.navimatrix.commons.data;

/**
 * Interim approach for implementing SDO mediators.
 * 
 * partially based on Domain Object Assembler pattern
 * but different enough due to the reason below that it can't be called
 * a Domain Object Assembler.
 * 
 * DataObjects are not *active* domain objects.  You must explicitly
 * store them and delete them by passing them to an implementation of
 * this interface.  AOP may help you construct them.  This limitation
 * is actually a feature to give flexibility and keep the complexity of
 * the implementations from approaching anything like hibernate, jdo,
 * etc...  not wanted here.
 * 
 * the uri of the root object is the db uri (last node of the jdbc uri)
 * 
 * the field(s) in the root must be partially constructed DataObjects and
 * the names of those fields must be the names of the tables they will
 * load from.
 * 
 */
public interface DataObjectAssembler {

    public DataObject read(DataObject query);

    public DataObject write(DataObject data);

    public void delete(DataObject data);

    /** 
     * if impl knows of the uri, it should set the type properties
     */
    public DataObject create(String uri, String name);
}
