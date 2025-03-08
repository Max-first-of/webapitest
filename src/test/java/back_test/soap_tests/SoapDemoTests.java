package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import io.qameta.allure.Step;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import jakarta.xml.bind.JAXBContext;
import model.SoapBodyRequestBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import utils.RequestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("SOAP тесты")
public class SoapDemoTests extends BaseTest {

    @DisplayName("Тест 1. Получаем настройки")
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @Test
    public void test1GetOrg(){
        step("Подготовка данных");
        SoapBodyRequestBuilder builder = new SoapBodyRequestBuilder("getSettings.xml");
        builder
                .addTagValue("es:Okato", "1000");
        addXmlAttach(builder.build());
        step("Выполняем запрос");
        Response response = given()
                .body(builder.build())
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().response();
        String s = response.asString();
        XmlPath xmlPath = new XmlPath(s);

        step("Проверяем ответ");
        String dateOfCalculationAge = xmlPath.get("DateOfCalculationAge").toString().substring(0,10);
        var maxCountWishPreschools = xmlPath.get("MaxCountWishPreschools");

        step("Проверяем данные", ()->{
            assertThat(dateOfCalculationAge)
                    .as("Дата равны")
                    .isEqualTo("2021-09-01");
        });
        System.out.println(s);
    }

    //Параметризированный тест
    @ParameterizedTest
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    public void test2ParamTest(){

    }
    //Параметризованный тест, данные берутся из файла
    @ParameterizedTest
    @Tags({@Tag("SOAP"), @Tag("Случайный тег")})
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    public void test3ParamTes(){

    }
}
