package org.xmlpull.v1.builder;

import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/**
 * Common abstraction shared between XmlElement, XmlDocument and XmlDoctype
 * to represent XML infoset item that can contain other infoet items
 * This is useful so getParent() operation will return this instead of Object ...
 */

public interface XmlContainer
{
    
    
    
    //public void serialize(XmlSerializer serializer) throws IOException;
}

