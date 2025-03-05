package model;

import lombok.Builder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;

public class SoapBodyRequestBuilder {
    private SoapBodyRequestBuilder builder;
    private Document document;
    private String filePath;
    public SoapBodyRequestBuilder(String fileName){
        filePath = "src\\main\\resources\\" + fileName;
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
        }catch (Exception exception){

        }
    }
    public SoapBodyRequestBuilder addTagValue(String tag, String value){
        try {
            File xmlFile = new File("src\\main\\resources\\getSettings.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName(tag);
            if(nodeList.getLength() > 0){
                nodeList.item(0).setTextContent(value);
            }else {
                throw new NullPointerException("В документе отсутствует тег + " + tag);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        }catch (NullPointerException ex){
            throw new NullPointerException("В документе отсутствует тег + " + tag);
        } catch (Exception ex){
            System.out.println(ex);
        }
        return this;
    }
    public String build(){
        String body;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            body = writer.toString();
        }catch (Exception exception){
            body = "Что-то пошло не так: " + exception;
        }
        return body;
    }
}
