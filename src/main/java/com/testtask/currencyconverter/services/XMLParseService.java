package com.testtask.currencyconverter.services;

import com.testtask.currencyconverter.entities.converter.Value;
import com.testtask.currencyconverter.entities.converter.Valute;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLParseService {

    public Document getXMLStructure(InputStream inputStream)
            throws ParserConfigurationException,
            IOException,
            SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        inputStream.close();
        return document;
    }

    /**
     * This method parses Document object into List of Valute objects
     * @param document parsed into Document object XML file
     * @param date date for which the file was requested
     * @return List of Valute objects
     */
    public List<Valute> getListOfValutes(Document document, Date date) {
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("Valute");
        List<Valute> valutes = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            valutes.add(getValuteObj(node, date));
        }

        return valutes;
    }

    /**
     * This method creates Valute object from parsed into Node object xml tag <Valute>
     * @param node Valute node to get info from
     * @param date Date for value to store
     * @return Valute object ready to store in database
     */
    public Valute getValuteObj(Node node, Date date) {
        String id = node.getAttributes().item(0).getNodeValue();
        // Method node.getChildNodes() doesn't work correctly, so i made this to get all child nodes of Valute node
        List<Node> childNodes = new ArrayList<>();
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            childNodes.add(child);
        }

        Valute valute = new Valute(
                id,
                Integer.parseInt(childNodes.get(0).getFirstChild().getTextContent()),
                childNodes.get(1).getFirstChild().getTextContent(),
                Integer.parseInt(childNodes.get(2).getFirstChild().getTextContent()),
                childNodes.get(3).getFirstChild().getTextContent()
        );
        Value value = new Value(
                valute,
                Double.parseDouble(childNodes.get(4)
                        .getFirstChild()
                        .getTextContent()
                        .replace(',','.')),
                date
        );

        return valute;
    }
}
