package domain.modules;

import domain.BaseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLElement<T extends BaseEntity<Long>> {

    public Element toXML(T t, Document doc)
    {
        return null;
    }

    public T fromXML(Element e)
    {
        return null;
    }

}
