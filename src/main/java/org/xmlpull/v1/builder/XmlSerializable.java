package org.xmlpull.v1.builder;

/**
 * This interface is used during serialization by XmlPullBuilder
 * for children that are not in XML infoset.
 */

import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public interface XmlSerializable
{
    public void serialize(XmlSerializer ser) throws IOException;
}

