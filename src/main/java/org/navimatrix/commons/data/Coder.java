package org.navimatrix.commons.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


public interface Coder {


    /**
     * not optional
     * 
     * @param input
     * 
     * @return 
     * @exception IOException
     */
    public DataObject decodeSdo(InputStream input) throws IOException;


    /**
     * not optional
     * 
     * @param data
     * @param output
     * 
     * @exception IOException
     */
    public void encodeSdo(DataObject data, OutputStream output) throws IOException;
}

