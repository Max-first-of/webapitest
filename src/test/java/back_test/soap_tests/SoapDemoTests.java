package back_test.soap_tests;

import back_test.base_tests.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
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
    String someOutsideVariable = System.getenv("JENKINS_VAR");
    @DisplayName("Тест 1")
    @Tags({@Tag("SOAP"), @Tag("SMOKE")})
    @Test
    public void test1GetOrg(){
        step("Подготовка данных");
        SoapBodyRequestBuilder builder = new SoapBodyRequestBuilder("getSettings.xml");
        builder
                .addTagValue("es:Okato", "1000");

        addXmlAttach("Тело запроса", builder.build());

        step("Выполняем запрос");
        Response response = given()
                .header("SOAPAction", "GetSettings")//Название метода, который вызываем
                .body(builder.build())
                .when()
                .post("")
                .then()
                .statusCode(200)
                .extract().response();

        step("Работаем с ответом");
        String s = response.getBody().asString();
        addXmlAttach("Ответ", s);

        XmlPath xmlPath = new XmlPath(s);

        String dateOfCalculationAge = xmlPath.get("DateOfCalculationAge").toString().substring(0,10);
        String maxCountWishPreschools = xmlPath.get("MaxCountWishPreschools");

        step("Проверяем данные", ()->{
            assertThat(dateOfCalculationAge)
                    .as("Дата равны")
                    .isEqualTo("2021-09-01");
            assertThat(maxCountWishPreschools)
                    .as("Что-то написали")
                    .isBetween("9", "10");
        });
    }
    @DisplayName("Получаем данные из Jenkins")
    @Test
    public void getParamFromJenkinsTest(){
        assertThat(someOutsideVariable).isEqualTo("4");
    }
}
