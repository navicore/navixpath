package org.xmlpull.v1.builder;

import java.util.Iterator;

/**
 * Use java.lang.Iterable instead when JDK 1.5 comes out ...
 *
 * @version $Revision: 1.1 $
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public interface Iterable
{
    public Iterator iterator();
}

