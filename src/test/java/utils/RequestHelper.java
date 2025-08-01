package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.SoapBodyRequestBuilder;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RequestHelper {
    public static String ConvertXmlToString(String fileName){

        String filePath = "src\\main\\resources\\" + fileName;
        String xml;
        try {
            xml = Files.lines(Paths.get(filePath)).collect(Collectors.joining("\n"));
        }catch (Exception ex){
            xml = null;
        }
        return xml;
    }

    @Step("Подготвка xml-документа")
    public static void setValue(String tag, String value){
        try {
            File xmlFile = new File("src" + File.separator + "main" + File.separator + "resources"+ File.separator+"getSettings.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName(tag);
            if(nodeList.getLength() > 0){
                nodeList.item(0).setTextContent(value);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    @Step("Выполняем запрос {}")
    public static Response soapRequest(String method, SoapBodyRequestBuilder body){
        Response response = given()
                .header("SOAPAction", method)//Название метода, который вызываем
                .body(body)
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }
}
