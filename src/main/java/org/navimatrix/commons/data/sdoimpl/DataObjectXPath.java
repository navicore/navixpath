package org.navimatrix.commons.data.sdoimpl;

import org.navimatrix.jaxen.BaseXPath;
import org.navimatrix.jaxen.JaxenException;

public class DataObjectXPath extends BaseXPath {

    DataObjectXPath(java.lang.String xpath) throws JaxenException {

        super(xpath, DataObjectNavigator.getInstance());
    }
}
