package org.xmlpull.v1.builder;

/**
 * Represents otrdered colelction of Character
 * <a href="http://www.w3.org/TR/xml-infoset/#infoitem.attribute">Attribute Information Item</a>
 * where character code properties are put together into Java String.
 */
public interface XmlCharacters //extends XmlContainer
{
    public String getText();
    public Boolean isWhitespaceContent();
    public XmlElement getParent();

}

