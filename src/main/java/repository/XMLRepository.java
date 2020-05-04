package repository;

import domain.BaseEntity;
import domain.validators.ValidatorException;
import domain.xml.XMLElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class XMLRepository<T extends BaseEntity<Long>> extends InMemoryRepository<Long,T> {
    private String fileName;
    private XMLElement<T> xmlElement;

    private DocumentBuilderFactory factory;
    private DocumentBuilder docBuilder;
    private Document doc;

    public XMLRepository( XMLElement<T> xmlElement, String fileName) {

        this.fileName = fileName;
        this.xmlElement = xmlElement;

        try
        {
            factory = DocumentBuilderFactory.newInstance();
            docBuilder = factory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            loadData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> delete(Long aLong) {
        Optional<T> result = super.delete(aLong);

        if(result.isPresent())
            try
            {
                saveFile(); //if something was removed then save the changes
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        return result;
    }

    void saveFile() throws Exception
    {
        if(Files.exists(Paths.get(fileName)))
        {
            doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);

            //erase previous data
            doc.removeChild(doc.getDocumentElement());
            doc.appendChild(doc.createElement("root"));
        }
        else
        {
            doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();

            doc.appendChild(doc.createElement("root"));
        }

        Element root = doc.getDocumentElement();

        entities.keySet().forEach(key -> {
            root.appendChild(xmlElement.toXML(entities.get(key),doc));
        });


        Transformer transformer =
                TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//for indentation
        transformer.transform(new DOMSource(root),
                new StreamResult(new FileOutputStream(
                        fileName)));

    }
    @Override
    public Optional<T> save(T entity) {
        Optional<T> optional = super.save(entity);

        try {
            saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (optional.isPresent()) {
            return optional;
        }

        return Optional.empty();
    }

    void loadData() throws Exception
    {
      if (Files.exists(Paths.get(fileName))) {
            doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);

            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();

            for(int i = 0; i < nodes.getLength(); i++){

                if (nodes.item(i) instanceof Element) {
                    T elem = xmlElement.fromXML((Element)nodes.item(i));
                    entities.put(elem.getId(),elem);
                }
            }
        }
        else throw new ValidatorException("XML File does not exist!");
    }
}