package back_test.base_tests;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

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
    public static RequestSpecification setRequestSpecification(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "GetSettings");
        RequestSpecification requestSpecification = builder.build();
        return requestSpecification;
    }
    @Attachment(value = "data", type = "application/xml", fileExtension = ".xml")
    public byte[] addXmlAttach(String attach){
        return attach.getBytes(StandardCharsets.UTF_8);
    }
}
