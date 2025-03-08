package back_test.base_tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class BaseTest {
    public static String BASE_URI = "http://demoes.ir-tech.ru/Other/ES2_ODO/Web/Services/PreschoolInquiryWcfService.svc";

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.requestSpecification = setRequestSpecification();
    }
    @AfterAll
    public static void afterAll(){

    }
    //Устанавливаем спецификация по умолчанию
    public static RequestSpecification setRequestSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .addHeader("Content-Type", "text/xml; charset=utf-8");
                //.addHeader("SOAPAction", "GetSettings");
        RequestSpecification requestSpecification = builder.build();
        return requestSpecification;
    }
    @Step("Вложение")
    public void addXmlAttach(String name, String attach){
        Allure.addAttachment(name, "application/xml", attach);
    }
    public void AddXmlAttachFormat(String name, String attach){
        try {
            // Создаем источник для неформатированного XML
            StringReader stringReader = new StringReader(attach);
            StreamSource source = new StreamSource(stringReader);

            // Создаем StringWriter для отформатированного XML
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);

            // Создаем объект Transformer для форматирования
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Устанавливаем параметры для форматирования (отступы)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Включаем отступы
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Устанавливаем размер отступа (2 пробела)

            // Преобразуем XML с отступами
            transformer.transform(source, result);

            // Возвращаем отформатированную строку
           attach = stringWriter.toString();
        }catch (Exception e){
            return;
        }
        Allure.addAttachment(name,"application/xml", attach);
    }
}
